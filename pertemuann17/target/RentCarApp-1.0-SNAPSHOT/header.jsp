<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NurdinCar | Premium Rent Car Management</title>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700;800&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/style.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom Premium Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Top Horizontal Navigation Bar -->
    <header class="app-navbar">
        <div class="navbar-container container-fluid">
            <!-- Left Side: Brand Logo -->
            <div class="navbar-brand-wrapper">
                <div class="brand-logo">
                    <i class="fa-solid fa-car-rear text-gradient"></i>
                </div>
                <span class="brand-name">Nurdin<span class="text-gradient">Car</span></span>
            </div>
            
            <!-- Center: Horizontal Navigation Menu -->
            <nav class="navbar-menu-horizontal">
                <a href="${pageContext.request.contextPath}/" class="nav-horizontal-item ${pageContext.request.requestURI.endsWith('index.jsp') || pageContext.request.requestURI == pageContext.request.contextPath ? 'active' : ''}">
                    <i class="fa-solid fa-chart-pie me-1"></i> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/cars" class="nav-horizontal-item ${pageContext.request.requestURI.contains('cars.jsp') || pageContext.request.requestURI.contains('car-form.jsp') ? 'active' : ''}">
                    <i class="fa-solid fa-car me-1"></i> Cars Fleet
                </a>
                <a href="${pageContext.request.contextPath}/customers" class="nav-horizontal-item ${pageContext.request.requestURI.contains('customers.jsp') || pageContext.request.requestURI.contains('customer-form.jsp') ? 'active' : ''}">
                    <i class="fa-solid fa-users me-1"></i> Customers
                </a>
                <a href="${pageContext.request.contextPath}/rentals" class="nav-horizontal-item ${pageContext.request.requestURI.contains('rentals.jsp') || pageContext.request.requestURI.contains('rent-form.jsp') || pageContext.request.requestURI.contains('return-form.jsp') ? 'active' : ''}">
                    <i class="fa-solid fa-handshake me-1"></i> Transactions
                </a>
            </nav>
            
            <!-- Right Side: Profile & Clock & Quick Action -->
            <div class="navbar-actions-wrapper">
                <span class="system-time me-3 d-none d-md-inline-block">
                    <i class="fa-regular fa-clock me-1 text-gradient"></i>
                    <span id="live-clock" style="font-weight: 600; color: var(--text-primary);">--:--:--</span>
                </span>
                
                <a href="${pageContext.request.contextPath}/rentals?action=rent" class="btn btn-gradient btn-sm me-3">
                    <i class="fa-solid fa-plus me-1"></i> Rent out
                </a>
                
                <div class="user-profile-badge">
                    <div class="user-avatar-small">
                        <i class="fa-solid fa-user-tie"></i>
                    </div>
                    <div class="user-info-small d-none d-lg-block">
                        <span class="user-name-small">Admin</span>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <div class="app-main-layout container-fluid">
        <!-- Main Content Area -->
        <main class="app-content-view">
            <!-- Page contents begin -->
