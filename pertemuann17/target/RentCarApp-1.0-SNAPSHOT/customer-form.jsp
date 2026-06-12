<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Header -->
<div class="page-header">
    <div>
        <h1 class="page-title">Edit Customer Profile</h1>
        <p class="page-subtitle">Update contact details, identity number, or residential address.</p>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/customers" class="btn btn-secondary-custom">
            <i class="fa-solid fa-arrow-left me-1"></i> Back to Clients List
        </a>
    </div>
</div>

<!-- Form Container Card -->
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card-premium">
            <h3 class="h5 text-white mb-4">
                <i class="fa-solid fa-user-pen text-gradient me-2"></i> Update Customer Profile
            </h3>
            
            <form action="${pageContext.request.contextPath}/customers?action=update" method="POST">
                <!-- Hidden Input for ID -->
                <input type="hidden" name="id" value="${customer.id}">
                
                <div class="row g-4">
                    <div class="col-md-6">
                        <label class="form-label">NIK (National ID Number)</label>
                        <input type="text" name="nik" class="form-control-custom" value="<c:out value="${customer.nik}"/>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Driving License Number (SIM)</label>
                        <input type="text" name="licenseNumber" class="form-control-custom" value="<c:out value="${customer.licenseNumber}"/>" required>
                    </div>
                    <div class="col-md-12">
                        <label class="form-label">Customer Full Name</label>
                        <input type="text" name="name" class="form-control-custom" value="<c:out value="${customer.name}"/>" required>
                    </div>
                    <div class="col-md-12">
                        <label class="form-label">Phone Number</label>
                        <input type="text" name="phone" class="form-control-custom" value="<c:out value="${customer.phone}"/>" required>
                    </div>
                    <div class="col-md-12">
                        <label class="form-label">Residential Address</label>
                        <textarea name="address" class="form-control-custom" rows="3" required><c:out value="${customer.address}"/></textarea>
                    </div>
                </div>
                
                <div class="d-flex justify-content-end gap-3 mt-5 pt-3 border-top border-light">
                    <a href="${pageContext.request.contextPath}/customers" class="btn btn-secondary-custom">Cancel</a>
                    <button type="submit" class="btn btn-gradient">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
