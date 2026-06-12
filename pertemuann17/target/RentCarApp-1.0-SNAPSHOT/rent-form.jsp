<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<!-- Page Header -->
<div class="page-header">
    <div>
        <h1 class="page-title">Rent out a Car</h1>
        <p class="page-subtitle">Register a new car lease transaction for an available vehicle.</p>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/rentals" class="btn btn-secondary-custom">
            <i class="fa-solid fa-arrow-left me-1"></i> Back to Transactions
        </a>
    </div>
</div>

<!-- Form Container Card -->
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card-premium">
            <h3 class="h5 text-white mb-4">
                <i class="fa-solid fa-file-signature text-gradient me-2"></i> Create Rental Lease
            </h3>
            
            <form action="${pageContext.request.contextPath}/rentals?action=add" method="POST" id="rentForm">
                <div class="row g-4">
                    <!-- Select Car -->
                    <div class="col-md-12">
                        <label class="form-label">Select Car (Only Available Cars listed)</label>
                        <select name="carId" id="carSelect" class="form-control-custom" required>
                            <option value="" data-price="0">-- Select Car --</option>
                            <c:forEach var="car" items="${availableCars}">
                                <option value="${car.id}" data-price="${car.pricePerDay}">
                                    <c:out value="${car.brand} ${car.model} (${car.plateNumber}) - " />
                                    <fmt:setLocale value="id_ID"/>
                                    <fmt:formatNumber value="${car.pricePerDay}" type="currency" currencySymbol="Rp " maxFractionDigits="0"/> / Day
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Select Customer -->
                    <div class="col-md-12">
                        <label class="form-label">Select Customer</label>
                        <select name="customerId" class="form-control-custom" required>
                            <option value="">-- Select Customer --</option>
                            <c:forEach var="cust" items="${customers}">
                                <option value="${cust.id}">
                                    <c:out value="${cust.name} (${cust.nik}) - ${cust.phone}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Rent Date -->
                    <div class="col-md-6">
                        <label class="form-label">Rent Date</label>
                        <input type="date" name="rentDate" id="rentDate" class="form-control-custom" required>
                    </div>

                    <!-- Due Date -->
                    <div class="col-md-6">
                        <label class="form-label">Due Date (Expected Return)</label>
                        <input type="date" name="dueDate" id="dueDate" class="form-control-custom" required>
                    </div>

                    <!-- Read-only Summary fields calculated via JS -->
                    <div class="col-md-6">
                        <label class="form-label">Price per Day</label>
                        <div class="input-group">
                            <span class="input-group-text bg-dark border-light text-secondary" style="border-radius: 10px 0 0 10px; border-right: none;">Rp</span>
                            <input type="text" id="priceDisplay" class="form-control-custom" style="border-radius: 0 10px 10px 0;" value="0" disabled>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Estimated Total Price</label>
                        <div class="input-group">
                            <span class="input-group-text bg-dark border-light text-secondary" style="border-radius: 10px 0 0 10px; border-right: none;">Rp</span>
                            <input type="text" id="totalDisplay" class="form-control-custom" style="border-radius: 0 10px 10px 0; font-weight: 700; color: var(--accent-blue);" value="0" disabled>
                        </div>
                    </div>
                </div>
                
                <div class="d-flex justify-content-end gap-3 mt-5 pt-3 border-top border-light">
                    <a href="${pageContext.request.contextPath}/rentals" class="btn btn-secondary-custom">Cancel</a>
                    <button type="submit" class="btn btn-gradient">Confirm Rental</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Dynamic pricing calculations JavaScript -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const carSelect = document.getElementById("carSelect");
        const rentDateInput = document.getElementById("rentDate");
        const dueDateInput = document.getElementById("dueDate");
        const priceDisplay = document.getElementById("priceDisplay");
        const totalDisplay = document.getElementById("totalDisplay");

        // Set default dates: Rent Date = today, Due Date = tomorrow
        const today = new Date();
        const tomorrow = new Date();
        tomorrow.setDate(today.getDate() + 1);

        const formatDate = (date) => {
            const yyyy = date.getFullYear();
            const mm = String(date.getMonth() + 1).padStart(2, '0');
            const dd = String(date.getDate()).padStart(2, '0');
            return `${yyyy}-${mm}-${dd}`;
        };

        rentDateInput.value = formatDate(today);
        dueDateInput.value = formatDate(tomorrow);
        
        // Prevent picking past dates
        rentDateInput.min = formatDate(today);
        dueDateInput.min = formatDate(today);

        function formatIDR(num) {
            return new Intl.NumberFormat('id-ID', { minimumFractionDigits: 0 }).format(num);
        }

        function calculateEstimates() {
            const selectedOption = carSelect.options[carSelect.selectedIndex];
            const pricePerDay = parseFloat(selectedOption.getAttribute("data-price")) || 0;
            
            priceDisplay.value = formatIDR(pricePerDay);

            const rentDate = new Date(rentDateInput.value);
            const dueDate = new Date(dueDateInput.value);

            if (rentDate && dueDate && !isNaN(rentDate) && !isNaN(dueDate) && pricePerDay > 0) {
                const diffTime = dueDate - rentDate;
                let diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
                
                if (diffDays <= 0) {
                    diffDays = 1; // Minimum 1 day rental
                }
                
                const totalPrice = diffDays * pricePerDay;
                totalDisplay.value = formatIDR(totalPrice);
            } else {
                totalDisplay.value = "0";
            }
        }

        carSelect.addEventListener("change", calculateEstimates);
        rentDateInput.addEventListener("change", function() {
            // Ensure due date is not before rent date
            dueDateInput.min = rentDateInput.value;
            calculateEstimates();
        });
        dueDateInput.addEventListener("change", calculateEstimates);
        
        // Initial call
        calculateEstimates();
    });
</script>

<jsp:include page="footer.jsp" />
