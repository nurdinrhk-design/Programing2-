package com.rentcar.dao;

import com.rentcar.model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY car_id DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE status = 'Available' ORDER BY brand, model";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car getCarById(int id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCar(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertCar(Car car) {
        String sql = "INSERT INTO cars (plate_number, brand, model, year, price_per_day, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, car.getPlateNumber());
            pstmt.setString(2, car.getBrand());
            pstmt.setString(3, car.getModel());
            pstmt.setInt(4, car.getYear());
            pstmt.setDouble(5, car.getPricePerDay());
            pstmt.setString(6, car.getStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCar(Car car) {
        String sql = "UPDATE cars SET plate_number = ?, brand = ?, model = ?, year = ?, price_per_day = ?, status = ? WHERE car_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, car.getPlateNumber());
            pstmt.setString(2, car.getBrand());
            pstmt.setString(3, car.getModel());
            pstmt.setInt(4, car.getYear());
            pstmt.setDouble(5, car.getPricePerDay());
            pstmt.setString(6, car.getStatus());
            pstmt.setInt(7, car.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCarStatus(int id, String status) {
        String sql = "UPDATE cars SET status = ? WHERE car_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCar(int id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Car mapResultSetToCar(ResultSet rs) throws SQLException {
        return new Car(
            rs.getInt("car_id"),
            rs.getString("plate_number"),
            rs.getString("brand"),
            rs.getString("model"),
            rs.getInt("year"),
            rs.getDouble("price_per_day"),
            rs.getString("status")
        );
    }
}
