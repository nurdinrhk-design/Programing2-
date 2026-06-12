<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Title -->
<div class="page-header">
    <div>
        <h1 class="page-title">Manage Customer Records</h1>
        <p class="page-subtitle">Register new customers, edit contact profiles, or review registration details.</p>
    </div>
    <div>
        <button type="button" class="btn btn-gradient" data-bs-toggle="modal" data-bs-target="#addCustomerModal">
            <i class="fa-solid fa-user-plus me-1"></i> Register Customer
        </button>
    </div>
</div>

<!-- Alert notifications -->
<c:if test="${not empty param.msg}">
    <c:choose>
        <c:when test="${param.msg == 'success_add'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Customer profile successfully registered.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'success_update'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Customer details updated successfully.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'success_delete'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Customer profile removed successfully.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'error_add' || param.msg == 'error_update' || param.msg == 'error_delete'}">
            <div class="alert-custom alert-custom-danger">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <span>Operation failed. NIK (National ID Card Number) must be unique.</span>
            </div>
        </c:when>
    </c:choose>
</c:if>

<!-- Customers Table Card -->
<div class="card-premium">
    <div class="table-responsive-custom">
        <table class="table-custom">
            <thead>
                <tr>
                    <th>#</th>
                    <th>National ID (NIK)</th>
                    <th>Customer Name</th>
                    <th>Phone Number</th>
                    <th>Driving License (SIM)</th>
                    <th>Address</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty listCustomers}">
                        <tr>
                            <td colspan="7" class="text-center py-4 text-secondary">No customer records found. Add a new customer above!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="cust" items="${listCustomers}" varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.index + 1}"/></td>
                                <td><span class="font-weight-bold text-white"><c:out value="${cust.nik}"/></span></td>
                                <td>
                                    <div class="fw-bold"><c:out value="${cust.name}"/></div>
                                    <small class="text-muted">ID: CUST-<c:out value="${cust.id}"/></small>
                                </td>
                                <td><c:out value="${cust.phone}"/></td>
                                <td><span class="badge bg-secondary p-2"><c:out value="${cust.licenseNumber}"/></span></td>
                                <td><c:out value="${cust.address}"/></td>
                                <td class="text-center">
                                    <div class="d-flex justify-content-center gap-2">
                                        <a href="${pageContext.request.contextPath}/customers?action=edit&id=${cust.id}" class="btn-action btn-edit" title="Edit Customer">
                                            <i class="fa-solid fa-user-pen"></i>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/customers?action=delete" method="POST" onsubmit="return confirm('Are you sure you want to delete this customer?');" style="display:inline;">
                                            <input type="hidden" name="id" value="${cust.id}">
                                            <button type="submit" class="btn-action btn-delete" title="Delete Customer">
                                                <i class="fa-solid fa-user-minus"></i>
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Add Customer Modal -->
<div class="modal fade" id="addCustomerModal" tabindex="-1" aria-labelledby="addCustomerModalLabel" aria-hidden="true" style="background-color: rgba(11, 15, 25, 0.65);">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-light bg-dark" style="border-radius: 16px;">
            <div class="modal-header border-bottom border-light p-4">
                <h5 class="modal-title text-white h5 font-family-outfit" id="addCustomerModalLabel">
                    <i class="fa-solid fa-user-plus text-gradient me-2"></i> Register New Customer
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/customers?action=add" method="POST">
                <div class="modal-body p-4">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">NIK (National ID Number)</label>
                            <input type="text" name="nik" class="form-control-custom" placeholder="e.g. 3273012345678901" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Driving License Number (SIM)</label>
                            <input type="text" name="licenseNumber" class="form-control-custom" placeholder="e.g. SIM-A-12345" required>
                        </div>
                        <div class="col-md-12">
                            <label class="form-label">Customer Full Name</label>
                            <input type="text" name="name" class="form-control-custom" placeholder="e.g. Fahrizal Tech" required>
                        </div>
                        <div class="col-md-12">
                            <label class="form-label">Phone Number</label>
                            <input type="text" name="phone" class="form-control-custom" placeholder="e.g. 08123456789" required>
                        </div>
                        <div class="col-md-12">
                            <label class="form-label">Residential Address</label>
                            <textarea name="address" class="form-control-custom" rows="3" placeholder="e.g. Jl. Sukajadi No. 12, Bandung" required></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-top border-light p-4">
                    <button type="button" class="btn btn-secondary-custom" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-gradient">Register Client</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
