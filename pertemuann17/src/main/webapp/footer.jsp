        </main> <!-- End .app-content-view -->
        
        <footer class="app-footer">
            <div class="container-fluid">
                <div class="row align-items-center">
                    <div class="col-md-6 text-center text-md-start">
                        <span class="footer-text">&copy; 2026 <strong>NurdinCar</strong>. All rights reserved.</span>
                    </div>
                    <div class="col-md-6 text-center text-md-end">
                        <span class="footer-text">Built with <i class="fa-solid fa-heart text-danger"></i> & Java EE 10</span>
                    </div>
                </div>
            </div>
        </footer>
    </div> <!-- End .app-main-layout -->

    <!-- Bootstrap 5 Bundle with Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom Live Clock & UI Interactions -->
    <script>
        function updateClock() {
            const now = new Date();
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');
            
            const clockEl = document.getElementById('live-clock');
            if (clockEl) {
                clockEl.textContent = `${hours}:${minutes}:${seconds}`;
            }
        }
        setInterval(updateClock, 1000);
        updateClock();
    </script>
</body>
</html>
