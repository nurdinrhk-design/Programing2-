package com.rentcar.controller;

import com.rentcar.dao.CarDAO;
import com.rentcar.model.Car;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class CarController extends HttpServlet {
    private CarDAO carDAO;

    @Override
    public void init() throws ServletException {
        carDAO = new CarDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listCars(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "add":
                    addCar(request, response);
                    break;
                case "update":
                    updateCar(request, response);
                    break;
                case "delete":
                    deleteCar(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cars");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            listCars(request, response);
        }
    }

    private void listCars(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Car> listCars = carDAO.getAllCars();
        request.setAttribute("listCars", listCars);
        request.getRequestDispatcher("/cars.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Car existingCar = carDAO.getCarById(id);
        request.setAttribute("car", existingCar);
        request.getRequestDispatcher("/car-form.jsp").forward(request, response);
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String plateNumber = request.getParameter("plateNumber");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        String status = request.getParameter("status");

        Car newCar = new Car(0, plateNumber, brand, model, year, pricePerDay, status);
        boolean success = carDAO.insertCar(newCar);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/cars?msg=success_add");
        } else {
            response.sendRedirect(request.getContextPath() + "/cars?msg=error_add");
        }
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String plateNumber = request.getParameter("plateNumber");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
        String status = request.getParameter("status");

        Car car = new Car(id, plateNumber, brand, model, year, pricePerDay, status);
        boolean success = carDAO.updateCar(car);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/cars?msg=success_update");
        } else {
            response.sendRedirect(request.getContextPath() + "/cars?msg=error_update");
        }
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = carDAO.deleteCar(id);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/cars?msg=success_delete");
        } else {
            response.sendRedirect(request.getContextPath() + "/cars?msg=error_delete");
        }
    }
}
