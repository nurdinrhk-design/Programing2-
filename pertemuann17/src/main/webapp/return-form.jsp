<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Header -->
<div class="page-header">
    <div>
        <h1 class="page-title">Process Car Return</h1>
        <p class="page-subtitle">Settle a car rental lease, calculate late fines, and return vehicle to the fleet.</p>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/rentals" class="btn btn-secondary-custom">
            <i class="fa-solid fa-arrow-left me-1"></i> Back to Transactions
        </a>
    </div>
</div>

<!-- Form Container Card -->
<div class="row justify-content-center">
    <!-- Rental Details Overview (Left) -->
    <div class="col-lg-5 mb-4">
        <div class="card-premium h-100">
            <h3 class="h5 text-white mb-4">
                <i class="fa-solid fa-receipt text-gradient me-2"></i> Lease Details
            </h3>
            
            <div class="d-flex flex-column gap-3">
                <div class="p-3 rounded bg-dark border-light">
                    <span class="text-secondary d-block" style="font-size: 0.75rem; text-transform: uppercase; letter-spacing: 0.5px;">Vehicle</span>
                    <strong class="text-white h6"><c:out value="${rental.car.brand} ${rental.car.model}"/></strong>
                    <span class="text-gradient d-block fw-medium font-family-outfit"><c:out value="${rental.car.plateNumber}"/></span>
                </div>
                
                <div class="p-3 rounded bg-dark border-light">
                    <span class="text-secondary d-block" style="font-size: 0.75rem; text-transform: uppercase; letter-spacing: 0.5px;">Customer</span>
                    <strong class="text-white h6"><c:out value="${rental.customer.name}"/></strong>
                    <span class="text-secondary d-block" style="font-size: 0.85rem;"><c:out value="${rental.customer.phone}"/></span>
                    <small class="text-muted text-xs">NIK: <c:out value="${rental.customer.nik}"/></small>
                </div>
                
                <div class="row g-2">
                    <div class="col-6">
                        <div class="p-3 rounded bg-dark border-light">
                            <span class="text-secondary d-block" style="font-size: 0.75rem;">Rent Date</span>
                            <span class="text-white fw-bold"><c:out value="${rental.rentDate}"/></span>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="p-3 rounded bg-dark border-light">
                            <span class="text-secondary d-block" style="font-size: 0.75rem;">Due Date</span>
                            <span class="text-white fw-bold text-gradient"><c:out value="${rental.dueDate}"/></span>
                        </div>
                    </div>
                </div>
                
                <div class="p-3 rounded bg-dark border-light d-flex justify-content-between align-items-center">
                    <span class="text-secondary" style="font-size: 0.85rem;">Daily Lease Rate:</span>
                    <strong class="text-white">
                        <fmt:setLocale value="id_ID"/>
                        <fmt:formatNumber value="${rental.pricePerDay}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/>
                    </strong>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Settle Return Form (Right) -->
    <div class="col-lg-7 mb-4">
        <div class="card-premium h-100">
            <h3 class="h5 text-white mb-4">
                <i class="fa-solid fa-clipboard-check text-gradient me-2"></i> Return Settlements
            </h3>
            
            <form action="${pageContext.request.contextPath}/rentals?action=processReturn" method="POST" id="returnForm">
                <!-- Hidden inputs -->
                <input type="hidden" name="rentalId" value="${rental.id}">
                <input type="hidden" name="carId" value="${rental.carId}">
                
                <div class="row g-3">
                    <!-- Return Date Selector -->
                    <div class="col-md-12">
                        <label class="form-label">Return Date</label>
                        <input type="date" name="returnDate" id="returnDate" class="form-control-custom" value="${today}" required>
                    </div>

                    <!-- Calculated Duration details -->
                    <div class="col-md-6">
                        <label class="form-label">Duration Rented</label>
                        <input type="text" id="durationDisplay" class="form-control-custom" value="1 Day" disabled>
                    </div>
                    
                    <div class="col-md-6">
                        <label class="form-label">Late Return Overdue</label>
                        <input type="text" id="lateDisplay" class="form-control-custom text-danger font-weight-bold" value="0 Days" disabled>
                    </div>

                    <!-- Financial Settlements -->
                    <div class="col-md-12">
                        <hr class="border-light my-3">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Late Return Fine (Rp 75.000 / Day)</label>
                        <div class="input-group">
                            <span class="input-group-text bg-dark border-light text-secondary" style="border-radius: 10px 0 0 10px; border-right: none;">Rp</span>
                            <input type="text" id="fineDisplay" class="form-control-custom" style="border-radius: 0 10px 10px 0;" value="0" disabled>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Final Settlement Price</label>
                        <div class="input-group">
                            <span class="input-group-text bg-dark border-light text-secondary" style="border-radius: 10px 0 0 10px; border-right: none;">Rp</span>
                            <input type="text" id="totalDisplay" class="form-control-custom" style="border-radius: 0 10px 10px 0; font-weight: 700; color: var(--accent-green);" value="0" disabled>
                        </div>
                    </div>
                </div>
                
                <div class="d-flex justify-content-end gap-3 mt-5 pt-3 border-top border-light">
                    <a href="${pageContext.request.contextPath}/rentals" class="btn btn-secondary-custom">Cancel</a>
                    <button type="submit" class="btn btn-gradient" style="background: var(--primary-gradient); box-shadow: 0 4px 15px rgba(0, 242, 254, 0.25);">Settle Return</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Dynamic return calculation engine JavaScript -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const returnDateInput = document.getElementById("returnDate");
        const durationDisplay = document.getElementById("durationDisplay");
        const lateDisplay = document.getElementById("lateDisplay");
        const fineDisplay = document.getElementById("fineDisplay");
        const totalDisplay = document.getElementById("totalDisplay");

        // Parse JSP constants
        const rentDate = new Date("${rental.rentDate}");
        const dueDate = new Date("${rental.dueDate}");
        const pricePerDay = parseFloat("${rental.pricePerDay}");

        // Prevent choosing return date earlier than rent date
        const formatDate = (date) => {
            const yyyy = date.getFullYear();
            const mm = String(date.getMonth() + 1).padStart(2, '0');
            const dd = String(date.getDate()).padStart(2, '0');
            return `${yyyy}-${mm}-${dd}`;
        };
        returnDateInput.min = formatDate(rentDate);

        function formatIDR(num) {
            return new Intl.NumberFormat('id-ID', { minimumFractionDigits: 0 }).format(num);
        }

        function calculateReturnCosts() {
            const returnDate = new Date(returnDateInput.value);
            if (!returnDate || isNaN(returnDate)) return;

            // 1. Calculate duration (at least 1 day)
            const diffRent = returnDate - rentDate;
            let daysRented = Math.ceil(diffRent / (1000 * 60 * 60 * 24));
            if (daysRented <= 0) {
                daysRented = 1;
            }
            durationDisplay.value = `${daysRented} Day${daysRented > 1 ? 's' : ''}`;

            // 2. Calculate late days
            const diffDue = returnDate - dueDate;
            let lateDays = Math.ceil(diffDue / (1000 * 60 * 60 * 24));
            if (lateDays < 0) {
                lateDays = 0;
            }
            
            lateDisplay.value = `${lateDays} Day${lateDays > 1 ? 's' : ''}`;
            if (lateDays > 0) {
                lateDisplay.classList.add("text-danger");
                lateDisplay.classList.remove("text-success");
            } else {
                lateDisplay.classList.remove("text-danger");
                lateDisplay.classList.add("text-success");
            }

            // 3. Fines (Rp 75,000 per late day)
            const fine = lateDays * 75000;
            fineDisplay.value = formatIDR(fine);

            // 4. Total Cost
            const total = (daysRented * pricePerDay) + fine;
            totalDisplay.value = formatIDR(total);
        }

        returnDateInput.addEventListener("change", calculateReturnCosts);
        
        // Initial run
        calculateReturnCosts();
    });
</script>

<jsp:include page="footer.jsp" />
