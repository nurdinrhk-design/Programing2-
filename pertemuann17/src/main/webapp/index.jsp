<%@ page import="com.rentcar.dao.RentalDAO" %>
<%@ page import="com.rentcar.model.Rental" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Fetch stats and recent activities on load
    RentalDAO rentalDAO = new RentalDAO();
    Map<String, Object> stats = rentalDAO.getDashboardStats();
    request.setAttribute("stats", stats);

    List<Rental> rentals = rentalDAO.getAllRentals();
    List<Rental> recentRentals = rentals;
    if (rentals.size() > 5) {
        recentRentals = rentals.subList(0, 5);
    }
    request.setAttribute("recentRentals", recentRentals);
%>

<jsp:include page="header.jsp" />

<!-- Dashboard Header -->
<div class="page-header">
    <div>
        <h1 class="page-title">Welcome Back, Admin</h1>
        <p class="page-subtitle">Here is the real-time operational overview of NurdinCar rental services.</p>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/report/pdf" class="btn btn-secondary-custom" target="_blank">
            <i class="fa-solid fa-file-pdf me-2 text-danger"></i> Export Transaction PDF
        </a>
    </div>
</div>

<!-- Status Cards Row -->
<div class="row g-4 mb-5">
    <!-- Total Cars Card -->
    <div class="col-xl-3 col-md-6">
        <div class="card-premium">
            <div class="stat-card">
                <div class="stat-info">
                    <span class="stat-label">Total Fleet</span>
                    <span class="stat-value"><c:out value="${stats.totalCars}" /></span>
                    <span class="text-secondary mt-1" style="font-size: 0.75rem;">
                        <span class="text-success font-weight-bold"><c:out value="${stats.availableCars}" /></span> Available
                    </span>
                </div>
                <div class="stat-icon blue">
                    <i class="fa-solid fa-car"></i>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Active Rentals Card -->
    <div class="col-xl-3 col-md-6">
        <div class="card-premium">
            <div class="stat-card">
                <div class="stat-info">
                    <span class="stat-label">Active Rentals</span>
                    <span class="stat-value"><c:out value="${stats.activeRentals}" /></span>
                    <span class="text-secondary mt-1" style="font-size: 0.75rem;">Vehicles on road</span>
                </div>
                <div class="stat-icon red">
                    <i class="fa-solid fa-key"></i>
                </div>
            </div>
        </div>
    </div>

    <!-- Total Customers Card -->
    <div class="col-xl-3 col-md-6">
        <div class="card-premium">
            <div class="stat-card">
                <div class="stat-info">
                    <span class="stat-label">Total Clients</span>
                    <span class="stat-value"><c:out value="${stats.totalCustomers}" /></span>
                    <span class="text-secondary mt-1" style="font-size: 0.75rem;">Registered members</span>
                </div>
                <div class="stat-icon purple">
                    <i class="fa-solid fa-users"></i>
                </div>
            </div>
        </div>
    </div>

    <!-- Total Revenue Card -->
    <div class="col-xl-3 col-md-6">
        <div class="card-premium">
            <div class="stat-card">
                <div class="stat-info">
                    <span class="stat-label">Total Revenue</span>
                    <span class="stat-value" style="font-size: 1.4rem; padding-top: 6px;">
                        <fmt:setLocale value="id_ID"/>
                        <fmt:formatNumber value="${stats.totalRevenue}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                    </span>
                    <span class="text-secondary mt-1" style="font-size: 0.75rem;">From settled rentals</span>
                </div>
                <div class="stat-icon green">
                    <i class="fa-solid fa-coins"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Recent Transactions and Quick Actions Grid -->
<div class="row g-4">
    <!-- Recent Transactions Table -->
    <div class="col-lg-8">
        <div class="card-premium h-100">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="card-title h5 mb-0">Recent Transactions</h3>
                <a href="${pageContext.request.contextPath}/rentals" class="text-gradient text-decoration-none font-weight-bold" style="font-size: 0.85rem;">
                    View All <i class="fa-solid fa-arrow-right ms-1"></i>
                </a>
            </div>
            
            <div class="table-responsive-custom">
                <table class="table-custom">
                    <thead>
                        <tr>
                            <th>Car</th>
                            <th>Customer</th>
                            <th>Rent Date</th>
                            <th>Due Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty recentRentals}">
                                <tr>
                                    <td colspan="5" class="text-center py-4 text-secondary">No recent transactions recorded.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="rental" items="${recentRentals}">
                                    <tr>
                                        <td>
                                            <div class="fw-bold"><c:out value="${rental.car.brand} ${rental.car.model}"/></div>
                                            <small class="text-secondary"><c:out value="${rental.car.plateNumber}"/></small>
                                        </td>
                                        <td>
                                            <div><c:out value="${rental.customer.name}"/></div>
                                            <small class="text-secondary"><c:out value="${rental.customer.phone}"/></small>
                                        </td>
                                        <td><c:out value="${rental.rentDate}"/></td>
                                        <td><c:out value="${rental.dueDate}"/></td>
                                        <td>
                                            <span class="badge-status ${rental.status == 'Returned' ? 'available' : 'rented'}">
                                                <c:out value="${rental.status}"/>
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Quick Management & Tools Card -->
    <div class="col-lg-4">
        <div class="card-premium h-100 d-flex flex-column justify-content-between">
            <div>
                <h3 class="card-title h5 mb-4">Quick Actions</h3>
                <div class="d-grid gap-3">
                    <a href="${pageContext.request.contextPath}/rentals?action=rent" class="btn btn-gradient text-start p-3 d-flex align-items-center">
                        <i class="fa-solid fa-key fa-lg me-3"></i>
                        <div>
                            <div class="fw-bold">New Rent Out</div>
                            <small class="text-dark opacity-75">Register a car rental lease</small>
                        </div>
                    </a>
                    
                    <a href="${pageContext.request.contextPath}/cars" class="btn btn-secondary-custom text-start p-3 d-flex align-items-center">
                        <i class="fa-solid fa-car-rear fa-lg me-3 text-gradient"></i>
                        <div>
                            <div class="fw-bold text-white">Add New Car</div>
                            <small class="text-secondary">Expand the rental vehicle fleet</small>
                        </div>
                    </a>

                    <a href="${pageContext.request.contextPath}/customers" class="btn btn-secondary-custom text-start p-3 d-flex align-items-center">
                        <i class="fa-solid fa-user-plus fa-lg me-3 text-gradient"></i>
                        <div>
                            <div class="fw-bold text-white">Register Customer</div>
                            <small class="text-secondary">Create a new client account profile</small>
                        </div>
                    </a>
                </div>
            </div>
            
            <div class="mt-4 p-3 rounded bg-dark border-light text-center">
                <i class="fa-solid fa-shield-halved text-gradient mb-2" style="font-size: 1.5rem;"></i>
                <div class="fw-bold" style="font-size: 0.85rem;">System Status: Secure</div>
                <small class="text-secondary" style="font-size: 0.75rem;">Connected to H2/SQLite DB engine</small>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
