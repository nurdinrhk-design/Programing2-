package com.rentcar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseHelper {
    // MySQL database URL, username, and password for XAMPP phpMyAdmin
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rentcar_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            // Force load the MySQL JDBC Driver (essential for Tomcat/Jakarta containers)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void initializeDatabase() {
        String createCarsTable = "CREATE TABLE IF NOT EXISTS cars (" +
                "car_id INT PRIMARY KEY AUTO_INCREMENT," +
                "plate_number VARCHAR(20) UNIQUE NOT NULL," +
                "brand VARCHAR(50) NOT NULL," +
                "model VARCHAR(50) NOT NULL," +
                "year INT NOT NULL," +
                "price_per_day DOUBLE NOT NULL," +
                "status VARCHAR(20) DEFAULT 'Available'" +
                ");";

        String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                "customer_id INT PRIMARY KEY AUTO_INCREMENT," +
                "nik VARCHAR(20) UNIQUE NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "phone VARCHAR(20) NOT NULL," +
                "address TEXT NOT NULL," +
                "license_number VARCHAR(50) NOT NULL" +
                ");";

        String createRentalsTable = "CREATE TABLE IF NOT EXISTS rentals (" +
                "rental_id INT PRIMARY KEY AUTO_INCREMENT," +
                "car_id INT NOT NULL," +
                "customer_id INT NOT NULL," +
                "rent_date DATE NOT NULL," +
                "due_date DATE NOT NULL," +
                "return_date DATE," +
                "price_per_day DOUBLE NOT NULL," +
                "total_price DOUBLE," +
                "fine DOUBLE DEFAULT 0," +
                "status VARCHAR(20) DEFAULT 'Rented'," +
                "FOREIGN KEY (car_id) REFERENCES cars(car_id) ON DELETE CASCADE," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Execute table creations
            stmt.execute(createCarsTable);
            stmt.execute(createCustomersTable);
            stmt.execute(createRentalsTable);
            
            System.out.println("MySQL database tables verified/created successfully.");
            
            // Seed initial data if tables are empty
            seedInitialData(conn);
            
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            System.err.println("Make sure MySQL is running in XAMPP and the 'rentcar_db' database is created in phpMyAdmin.");
            e.printStackTrace();
        }
    }

    private static void seedInitialData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Check if cars are empty
            ResultSet rsCar = stmt.executeQuery("SELECT COUNT(*) FROM cars");
            if (rsCar.next() && rsCar.getInt(1) == 0) {
                stmt.execute("INSERT INTO cars (plate_number, brand, model, year, price_per_day, status) VALUES " +
                        "('B 1234 ABC', 'Toyota', 'Avanza', 2022, 350000.0, 'Available')," +
                        "('B 5678 DEF', 'Honda', 'Civic', 2021, 600000.0, 'Available')," +
                        "('B 9012 GHI', 'Mitsubishi', 'Xpander', 2023, 400000.0, 'Available')," +
                        "('D 2468 JKL', 'Suzuki', 'Ertiga', 2020, 300000.0, 'Available')," +
                        "('D 1357 MNO', 'Hyundai', 'Ioniq 5', 2023, 850000.0, 'Maintenance')");
                System.out.println("Seeded default cars data.");
            }

            // Check if customers are empty
            ResultSet rsCust = stmt.executeQuery("SELECT COUNT(*) FROM customers");
            if (rsCust.next() && rsCust.getInt(1) == 0) {
                stmt.execute("INSERT INTO customers (nik, name, phone, address, license_number) VALUES " +
                        "('3273012345678901', 'Fahrizal Tech', '08123456789', 'Jl. Sukajadi No. 12, Bandung', 'SIM-A-12345')," +
                        "('3171098765432109', 'Aulia Rahma', '08771234567', 'Jl. Sudirman No. 45, Jakarta', 'SIM-A-98765')," +
                        "('3204081234560002', 'Budi Santoso', '08529876543', 'Jl. Merdeka No. 99, Tangerang', 'SIM-A-67890')");
                System.out.println("Seeded default customers data.");
            }
        }
    }
}
