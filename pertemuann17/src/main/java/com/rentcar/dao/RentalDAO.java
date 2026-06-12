package com.rentcar.dao;

import com.rentcar.model.Car;
import com.rentcar.model.Customer;
import com.rentcar.model.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalDAO {

    public List<Rental> getAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT r.*, c.plate_number, c.brand, c.model, c.year, c.price_per_day AS car_price, c.status AS car_status, " +
                     "cust.nik, cust.name, cust.phone, cust.address, cust.license_number " +
                     "FROM rentals r " +
                     "JOIN cars c ON r.car_id = c.car_id " +
                     "JOIN customers cust ON r.customer_id = cust.customer_id " +
                     "ORDER BY r.rental_id DESC";
                     
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public Rental getRentalById(int id) {
        String sql = "SELECT r.*, c.plate_number, c.brand, c.model, c.year, c.price_per_day AS car_price, c.status AS car_status, " +
                     "cust.nik, cust.name, cust.phone, cust.address, cust.license_number " +
                     "FROM rentals r " +
                     "JOIN cars c ON r.car_id = c.car_id " +
                     "JOIN customers cust ON r.customer_id = cust.customer_id " +
                     "WHERE r.rental_id = ?";
                     
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRental(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertRental(Rental rental) {
        String sqlInsertRental = "INSERT INTO rentals (car_id, customer_id, rent_date, due_date, price_per_day, status) VALUES (?, ?, ?, ?, ?, 'Rented')";
        String sqlUpdateCar = "UPDATE cars SET status = 'Rented' WHERE car_id = ?";
        
        Connection conn = null;
        PreparedStatement psRental = null;
        PreparedStatement psCar = null;
        
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false); // Begin Transaction
            
            psRental = conn.prepareStatement(sqlInsertRental);
            psRental.setInt(1, rental.getCarId());
            psRental.setInt(2, rental.getCustomerId());
            psRental.setString(3, rental.getRentDate());
            psRental.setString(4, rental.getDueDate());
            psRental.setDouble(5, rental.getPricePerDay());
            psRental.executeUpdate();
            
            psCar = conn.prepareStatement(sqlUpdateCar);
            psCar.setInt(1, rental.getCarId());
            psCar.executeUpdate();
            
            conn.commit(); // Commit Transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(psRental);
            closeQuietly(psCar);
            closeQuietly(conn);
        }
    }

    public boolean returnCar(int rentalId, int carId, String returnDate, double fine, double totalPrice) {
        String sqlUpdateRental = "UPDATE rentals SET return_date = ?, fine = ?, total_price = ?, status = 'Returned' WHERE rental_id = ?";
        String sqlUpdateCar = "UPDATE cars SET status = 'Available' WHERE car_id = ?";
        
        Connection conn = null;
        PreparedStatement psRental = null;
        PreparedStatement psCar = null;
        
        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false); // Begin Transaction
            
            psRental = conn.prepareStatement(sqlUpdateRental);
            psRental.setString(1, returnDate);
            psRental.setDouble(2, fine);
            psRental.setDouble(3, totalPrice);
            psRental.setInt(4, rentalId);
            psRental.executeUpdate();
            
            psCar = conn.prepareStatement(sqlUpdateCar);
            psCar.setInt(1, carId);
            psCar.executeUpdate();
            
            conn.commit(); // Commit Transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(psRental);
            closeQuietly(psCar);
            closeQuietly(conn);
        }
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        String sqlTotalCars = "SELECT COUNT(*) FROM cars";
        String sqlAvailableCars = "SELECT COUNT(*) FROM cars WHERE status = 'Available'";
        String sqlTotalCust = "SELECT COUNT(*) FROM customers";
        String sqlActiveRentals = "SELECT COUNT(*) FROM rentals WHERE status = 'Rented'";
        String sqlRevenue = "SELECT SUM(total_price) FROM rentals WHERE status = 'Returned'";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            
            try (ResultSet rs = stmt.executeQuery(sqlTotalCars)) {
                stats.put("totalCars", rs.next() ? rs.getInt(1) : 0);
            }
            try (ResultSet rs = stmt.executeQuery(sqlAvailableCars)) {
                stats.put("availableCars", rs.next() ? rs.getInt(1) : 0);
            }
            try (ResultSet rs = stmt.executeQuery(sqlTotalCust)) {
                stats.put("totalCustomers", rs.next() ? rs.getInt(1) : 0);
            }
            try (ResultSet rs = stmt.executeQuery(sqlActiveRentals)) {
                stats.put("activeRentals", rs.next() ? rs.getInt(1) : 0);
            }
            try (ResultSet rs = stmt.executeQuery(sqlRevenue)) {
                stats.put("totalRevenue", rs.next() ? rs.getDouble(1) : 0.0);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            stats.put("totalCars", 0);
            stats.put("availableCars", 0);
            stats.put("totalCustomers", 0);
            stats.put("activeRentals", 0);
            stats.put("totalRevenue", 0.0);
        }
        return stats;
    }

    private Rental mapResultSetToRental(ResultSet rs) throws SQLException {
        Rental rental = new Rental(
            rs.getInt("rental_id"),
            rs.getInt("car_id"),
            rs.getInt("customer_id"),
            rs.getString("rent_date"),
            rs.getString("due_date"),
            rs.getString("return_date"),
            rs.getDouble("price_per_day"),
            rs.getDouble("total_price"),
            rs.getDouble("fine"),
            rs.getString("status")
        );
        
        // Map nested objects
        Car car = new Car(
            rs.getInt("car_id"),
            rs.getString("plate_number"),
            rs.getString("brand"),
            rs.getString("model"),
            rs.getInt("year"),
            rs.getDouble("car_price"),
            rs.getString("car_status")
        );
        rental.setCar(car);
        
        Customer customer = new Customer(
            rs.getInt("customer_id"),
            rs.getString("nik"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getString("license_number")
        );
        rental.setCustomer(customer);
        
        return rental;
    }

    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}
