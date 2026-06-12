<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Title -->
<div class="page-header">
    <div>
        <h1 class="page-title">Rentals & Returns Transactions</h1>
        <p class="page-subtitle">Track rent-outs, process car returns, calculate fines, and export reports.</p>
    </div>
    <div class="d-flex gap-2">
        <a href="${pageContext.request.contextPath}/report/pdf" class="btn btn-secondary-custom" target="_blank">
            <i class="fa-solid fa-file-pdf me-1 text-danger"></i> PDF Report
        </a>
        <a href="${pageContext.request.contextPath}/rentals?action=rent" class="btn btn-gradient">
            <i class="fa-solid fa-key me-1"></i> Rent Out Car
        </a>
    </div>
</div>

<!-- Alert notifications -->
<c:if test="${not empty param.msg}">
    <c:choose>
        <c:when test="${param.msg == 'success_rent'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Rental lease successfully recorded. Vehicle marked as rented.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'success_return'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Vehicle return recorded. Fine and total charges settled. Car is available again.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'car_unavailable'}">
            <div class="alert-custom alert-custom-danger">
                <i class="fa-solid fa-circle-exclamation"></i>
                <span>Operation failed. Selected vehicle is already rented out or in maintenance.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'error_rent' || param.msg == 'error_return'}">
            <div class="alert-custom alert-custom-danger">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <span>An error occurred while writing transaction data to the database.</span>
            </div>
        </c:when>
    </c:choose>
</c:if>

<!-- Rentals Table Card -->
<div class="card-premium">
    <div class="table-responsive-custom">
        <table class="table-custom">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Vehicle Details</th>
                    <th>Customer</th>
                    <th>Rent Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Price/Day</th>
                    <th>Fine</th>
                    <th>Total Cost</th>
                    <th>Status</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty listRentals}">
                        <tr>
                            <td colspan="11" class="text-center py-4 text-secondary">No rental transactions registered yet.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="rental" items="${listRentals}" varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.index + 1}"/></td>
                                <td>
                                    <div class="fw-bold"><c:out value="${rental.car.brand} ${rental.car.model}"/></div>
                                    <small class="text-gradient fw-medium"><c:out value="${rental.car.plateNumber}"/></small>
                                </td>
                                <td>
                                    <div class="fw-bold"><c:out value="${rental.customer.name}"/></div>
                                    <small class="text-muted"><c:out value="${rental.customer.phone}"/></small>
                                </td>
                                <td><c:out value="${rental.rentDate}"/></td>
                                <td><c:out value="${rental.dueDate}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty rental.returnDate}">
                                            <c:out value="${rental.returnDate}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted italic">Still Rented</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:setLocale value="id_ID"/>
                                    <fmt:formatNumber value="${rental.pricePerDay}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${rental.fine}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${rental.status == 'Returned'}">
                                            <span class="text-success fw-bold">
                                                <fmt:formatNumber value="${rental.totalPrice}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="badge-status ${rental.status == 'Returned' ? 'available' : 'rented'}">
                                        <c:out value="${rental.status}"/>
                                    </span>
                                </td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${rental.status == 'Rented'}">
                                            <a href="${pageContext.request.contextPath}/rentals?action=return&id=${rental.id}" class="btn-action btn-return" title="Return Car">
                                                <i class="fa-solid fa-arrow-rotate-left"></i> Return Car
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-secondary" style="font-size: 0.8rem;"><i class="fa-solid fa-circle-check text-success"></i> Settled</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="footer.jsp" />
