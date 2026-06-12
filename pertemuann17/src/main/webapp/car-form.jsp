<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Header -->
<div class="page-header">
    <div>
        <h1 class="page-title">Edit Vehicle Details</h1>
        <p class="page-subtitle">Update registration details, pricing, or status for this vehicle.</p>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/cars" class="btn btn-secondary-custom">
            <i class="fa-solid fa-arrow-left me-1"></i> Back to Fleet List
        </a>
    </div>
</div>

<!-- Form Container Card -->
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card-premium">
            <h3 class="h5 text-white mb-4">
                <i class="fa-solid fa-pen-to-square text-gradient me-2"></i> Update Car Details
            </h3>
            
            <form action="${pageContext.request.contextPath}/cars?action=update" method="POST">
                <!-- Hidden Input for ID -->
                <input type="hidden" name="id" value="${car.id}">
                
                <div class="row g-4">
                    <div class="col-md-6">
                        <label class="form-label">Plate Number</label>
                        <input type="text" name="plateNumber" class="form-control-custom" value="<c:out value="${car.plateNumber}"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Year of Manufacture</label>
                        <input type="number" name="year" class="form-control-custom" min="1990" max="2030" value="<c:out value="${car.year}"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Brand</label>
                        <input type="text" name="brand" class="form-control-custom" value="<c:out value="${car.brand}"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Model</label>
                        <input type="text" name="model" class="form-control-custom" value="<c:out value="${car.model}"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Rental Price (per Day)</label>
                        <input type="number" name="pricePerDay" class="form-control-custom" min="1" value="<fmt:formatNumber value="${car.pricePerDay}" pattern="#####"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Status</label>
                        <select name="status" class="form-control-custom">
                            <option value="Available" ${car.status == 'Available' ? 'selected' : ''}>Available</option>
                            <option value="Rented" ${car.status == 'Rented' ? 'selected' : ''}>Rented</option>
                            <option value="Maintenance" ${car.status == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                        </select>
                    </div>
                </div>
                
                <div class="d-flex justify-content-end gap-3 mt-5 pt-3 border-top border-light">
                    <a href="${pageContext.request.contextPath}/cars" class="btn btn-secondary-custom">Cancel</a>
                    <button type="submit" class="btn btn-gradient">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
