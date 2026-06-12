package com.rentcar.controller;

import com.rentcar.dao.CustomerDAO;
import com.rentcar.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerController extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
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
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listCustomers(request, response);
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
                    addCustomer(request, response);
                    break;
                case "update":
                    updateCustomer(request, response);
                    break;
                case "delete":
                    deleteCustomer(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/customers");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            listCustomers(request, response);
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Customer> listCustomers = customerDAO.getAllCustomers();
        request.setAttribute("listCustomers", listCustomers);
        request.getRequestDispatcher("/customers.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer existingCustomer = customerDAO.getCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String nik = request.getParameter("nik");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String licenseNumber = request.getParameter("licenseNumber");

        Customer newCustomer = new Customer(0, nik, name, phone, address, licenseNumber);
        boolean success = customerDAO.insertCustomer(newCustomer);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/customers?msg=success_add");
        } else {
            response.sendRedirect(request.getContextPath() + "/customers?msg=error_add");
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nik = request.getParameter("nik");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String licenseNumber = request.getParameter("licenseNumber");

        Customer customer = new Customer(id, nik, name, phone, address, licenseNumber);
        boolean success = customerDAO.updateCustomer(customer);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/customers?msg=success_update");
        } else {
            response.sendRedirect(request.getContextPath() + "/customers?msg=error_update");
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = customerDAO.deleteCustomer(id);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/customers?msg=success_delete");
        } else {
            response.sendRedirect(request.getContextPath() + "/customers?msg=error_delete");
        }
    }
}
