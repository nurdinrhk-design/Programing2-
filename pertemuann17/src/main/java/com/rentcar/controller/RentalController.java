package com.rentcar.controller;

import com.rentcar.dao.CarDAO;
import com.rentcar.dao.CustomerDAO;
import com.rentcar.dao.RentalDAO;
import com.rentcar.model.Car;
import com.rentcar.model.Customer;
import com.rentcar.model.Rental;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet("/rentals")
public class RentalController extends HttpServlet {
    private RentalDAO rentalDAO;
    private CarDAO carDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        rentalDAO = new RentalDAO();
        carDAO = new CarDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "rent":
                showRentForm(request, response);
                break;
            case "return":
                showReturnForm(request, response);
                break;
            case "list":
            default:
                listRentals(request, response);
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
                    processRental(request, response);
                    break;
                case "processReturn":
                    processReturn(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/rentals");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            listRentals(request, response);
        }
    }

    private void listRentals(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Rental> listRentals = rentalDAO.getAllRentals();
        request.setAttribute("listRentals", listRentals);
        request.getRequestDispatcher("/rentals.jsp").forward(request, response);
    }

    private void showRentForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Car> availableCars = carDAO.getAvailableCars();
        List<Customer> customers = customerDAO.getAllCustomers();
        
        request.setAttribute("availableCars", availableCars);
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/rent-form.jsp").forward(request, response);
    }

    private void showReturnForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Rental rental = rentalDAO.getRentalById(id);
        
        if (rental != null) {
            request.setAttribute("rental", rental);
            request.setAttribute("today", LocalDate.now().toString());
            request.getRequestDispatcher("/return-form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=not_found");
        }
    }

    private void processRental(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String rentDate = request.getParameter("rentDate");
        String dueDate = request.getParameter("dueDate");
        
        Car car = carDAO.getCarById(carId);
        if (car == null || !"Available".equals(car.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=car_unavailable");
            return;
        }

        Rental rental = new Rental();
        rental.setCarId(carId);
        rental.setCustomerId(customerId);
        rental.setRentDate(rentDate);
        rental.setDueDate(dueDate);
        rental.setPricePerDay(car.getPricePerDay());

        boolean success = rentalDAO.insertRental(rental);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=success_rent");
        } else {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=error_rent");
        }
    }

    private void processReturn(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int rentalId = Integer.parseInt(request.getParameter("rentalId"));
        int carId = Integer.parseInt(request.getParameter("carId"));
        String returnDateStr = request.getParameter("returnDate");
        
        Rental rental = rentalDAO.getRentalById(rentalId);
        if (rental == null) {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=rental_not_found");
            return;
        }

        LocalDate dueDate = LocalDate.parse(rental.getDueDate());
        LocalDate rentDate = LocalDate.parse(rental.getRentDate());
        LocalDate returnDate = LocalDate.parse(returnDateStr);

        // Calculate total days rented (at least 1 day)
        long daysRented = ChronoUnit.DAYS.between(rentDate, returnDate);
        if (daysRented <= 0) {
            daysRented = 1;
        }

        // Calculate late days
        long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        double fine = 0.0;
        if (lateDays > 0) {
            fine = lateDays * 75000.0; // Fine is IDR 75,000 per late day
        }

        double totalPrice = (daysRented * rental.getPricePerDay()) + fine;

        boolean success = rentalDAO.returnCar(rentalId, carId, returnDateStr, fine, totalPrice);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=success_return");
        } else {
            response.sendRedirect(request.getContextPath() + "/rentals?msg=error_return");
        }
    }
}
