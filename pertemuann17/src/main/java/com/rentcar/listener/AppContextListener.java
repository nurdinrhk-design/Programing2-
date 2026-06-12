package com.rentcar.listener;

import com.rentcar.dao.DatabaseHelper;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("RentCarApp Web Application starting up...");
        DatabaseHelper.initializeDatabase();
        System.out.println("RentCarApp Database successfully initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("RentCarApp Web Application shutting down...");
    }
}
