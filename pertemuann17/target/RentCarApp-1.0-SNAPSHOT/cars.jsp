<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Title -->
<div class="page-header">
    <div>
        <h1 class="page-title">Manage Vehicle Fleet</h1>
        <p class="page-subtitle">Add, edit, or remove rental cars and update their status.</p>
    </div>
    <div>
        <button type="button" class="btn btn-gradient" data-bs-toggle="modal" data-bs-target="#addCarModal">
            <i class="fa-solid fa-plus me-1"></i> Add New Car
        </button>
    </div>
</div>

<!-- Alert notifications -->
<c:if test="${not empty param.msg}">
    <c:choose>
        <c:when test="${param.msg == 'success_add'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Car successfully added to the database.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'success_update'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Car details updated successfully.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'success_delete'}">
            <div class="alert-custom alert-custom-success">
                <i class="fa-solid fa-circle-check"></i>
                <span>Car successfully removed from the fleet.</span>
            </div>
        </c:when>
        <c:when test="${param.msg == 'error_add' || param.msg == 'error_update' || param.msg == 'error_delete'}">
            <div class="alert-custom alert-custom-danger">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <span>An error occurred while processing the database operation. Check for duplicate plate numbers.</span>
            </div>
        </c:when>
    </c:choose>
</c:if>

<!-- Cars List Card -->
<div class="card-premium">
    <div class="table-responsive-custom">
        <table class="table-custom">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Car Identity</th>
                    <th>Details</th>
                    <th>Price / Day</th>
                    <th>Status</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty listCars}">
                        <tr>
                            <td colspan="6" class="text-center py-4 text-secondary">No cars available. Add a new car above!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="car" items="${listCars}" varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.index + 1}"/></td>
                                <td>
                                    <span class="font-weight-bold text-white"><c:out value="${car.plateNumber}"/></span>
                                    <div class="text-muted" style="font-size: 0.75rem;">ID: CAR-<c:out value="${car.id}"/></div>
                                </td>
                                <td>
                                    <div class="fw-bold"><c:out value="${car.brand} ${car.model}"/></div>
                                    <small class="text-secondary">Year: <c:out value="${car.year}"/></small>
                                </td>
                                <td>
                                    <fmt:setLocale value="id_ID"/>
                                    <span class="text-white fw-medium">
                                        <fmt:formatNumber value="${car.pricePerDay}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                                    </span>
                                </td>
                                <td>
                                    <span class="badge-status <c:out value="${car.status.toLowerCase()}"/>">
                                        <c:out value="${car.status}"/>
                                    </span>
                                </td>
                                <td class="text-center">
                                    <div class="d-flex justify-content-center gap-2">
                                        <a href="${pageContext.request.contextPath}/cars?action=edit&id=${car.id}" class="btn-action btn-edit" title="Edit Car">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/cars?action=delete" method="POST" onsubmit="return confirm('Are you sure you want to delete this car?');" style="display:inline;">
                                            <input type="hidden" name="id" value="${car.id}">
                                            <button type="submit" class="btn-action btn-delete" title="Delete Car">
                                                <i class="fa-solid fa-trash"></i>
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

<!-- Add Car Modal (Popup bootstrap modal mapped to add action) -->
<div class="modal fade" id="addCarModal" tabindex="-1" aria-labelledby="addCarModalLabel" aria-hidden="true" style="background-color: rgba(11, 15, 25, 0.65);">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-light bg-dark" style="border-radius: 16px;">
            <div class="modal-header border-bottom border-light p-4">
                <h5 class="modal-title text-white h5 font-family-outfit" id="addCarModalLabel">
                    <i class="fa-solid fa-car-rear text-gradient me-2"></i> Register New Car
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/cars?action=add" method="POST">
                <div class="modal-body p-4">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Plate Number</label>
                            <input type="text" name="plateNumber" class="form-control-custom" placeholder="e.g. B 1234 ABC" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Year of Manufacture</label>
                            <input type="number" name="year" class="form-control-custom" min="1990" max="2030" placeholder="e.g. 2022" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Brand</label>
                            <input type="text" name="brand" class="form-control-custom" placeholder="e.g. Toyota" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Model</label>
                            <input type="text" name="model" class="form-control-custom" placeholder="e.g. Avanza" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Rental Price (per Day)</label>
                            <input type="number" name="pricePerDay" class="form-control-custom" min="1" placeholder="e.g. 350000" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Initial Status</label>
                            <select name="status" class="form-control-custom">
                                <option value="Available">Available</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-top border-light p-4">
                    <button type="button" class="btn btn-secondary-custom" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-gradient">Save Vehicle</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
