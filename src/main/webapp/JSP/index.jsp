<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    // ===============================
    // ADMIN SESSION CHECK
    // ===============================
    String adminUser = (String) session.getAttribute("adminName");

    if (adminUser == null)
        adminUser = (String) session.getAttribute("fnameadmin");

    // ===============================
    // CUSTOMER SESSION CHECK
    // ===============================
    String customerUser = (String) session.getAttribute("customerName");

    if (customerUser == null)
        customerUser = (String) session.getAttribute("fname");

    // ===============================
    // ANY USER LOGGED IN?
    // ===============================
    boolean isLoggedIn = (adminUser != null || customerUser != null);

    // ===============================
    // DASHBOARD PATH
    // ===============================
    String dashboardLink = "";
    if (adminUser != null) {
        dashboardLink = "ADMIN/adminPanel.jsp";
    } else if (customerUser != null) {
        dashboardLink = "user-dashboard.jsp";
    }
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Health Basket | Online Medicine Store</title>
<link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
<!-- Icons -->
<link rel="stylesheet"
 href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap"
 rel="stylesheet">

<style>
/* GLOBAL */
body {
    margin: 0;
    padding: 0;
    font-family: 'Poppins', sans-serif;
    background: #f4f7fb;
}

/* NAVBAR (UNCHANGED) */
.navbar {
    background-color: #0d6efd;
    padding: 12px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
    box-shadow: 0 2px 6px rgba(0,0,0,0.2);
}

.navbar .logo {
    font-size: 20px;
    font-weight: bold;
}

.navbar .nav-links a {
    color: white;
    text-decoration: none;
    margin-left: 20px;
    font-size: 15px;
    padding: 6px 12px;
    border-radius: 5px;
}
.navbar .nav-links a:hover {
    background: white;
    color: #0d6efd;
}

/* SLIDER */
/* SLIDER */
.slider {
    width: 100%;
    height: 380px;   /* FIXED HEIGHT */
    position: relative;
    overflow: hidden;
}

.slides {
    display: flex;
    width: 400%;
    height: 100%;
    animation: slideAnim 16s infinite;
}

.slides img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: top center; /* Fixes top cutting */
}




@keyframes slideAnim {
    0% { margin-left: 0%; }
    20% { margin-left: 0%; }

    25% { margin-left: -100%; }
    45% { margin-left: -100%; }

    50% { margin-left: -200%; }
    70% { margin-left: -200%; }

    75% { margin-left: -300%; }
    95% { margin-left: -300%; }

    100% { margin-left: 0%; }
}

/* CATEGORY SECTION */
.section-title {
    text-align: center;
    font-size: 26px;
    margin-top: 40px;
    color: #4a00e0;
}

.category-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
    gap: 30px;
    padding: 20px 40px;
}

.category-card {
    background: white;
    padding: 20px;
    border-radius: 16px;
    box-shadow: 0px 4px 10px rgba(0,0,0,0.1);
    text-align: center;
    font-weight: 600;
    color: #333;
    transition: 0.3s;
}
.category-card:hover {
    transform: translateY(-6px);
    box-shadow: 0px 6px 14px rgba(74,0,224,0.4);
}

/* PRODUCT CAROUSEL — AMAZON STYLE */
.carousel-container {
    padding: 25px 40px;
}

.carousel-title {
    font-size: 22px;
    color: #4a00e0;
    margin-bottom: 10px;
}

.carousel {
    display: flex;
    overflow-x: auto;
    gap: 20px;
    scroll-behavior: smooth;
    padding-bottom: 10px;
}

.carousel::-webkit-scrollbar {
    height: 6px;
}
.carousel::-webkit-scrollbar-thumb {
    background: #4a00e0;
    border-radius: 10px;
}

.product-card {
    min-width: 180px;
    background: white;
    padding: 12px;
    border-radius: 12px;
    box-shadow: 0px 4px 10px rgba(0,0,0,0.15);
    text-align: center;
}

.product-card img {
    width: 100%;
    border-radius: 10px;
    height: 140px;
    object-fit: contain;
}

.product-name {
    margin-top: 8px;
    font-weight: bold;
}

.product-price {
    color: green;
    font-weight: bold;
    margin-top: 4px;
}

.add-btn {
    margin-top: 8px;
    padding: 6px 14px;
    background: #4a00e0;
    color: white;
    border-radius: 6px;
    border: none;
}

/* BEST SELLERS GRID */
.best-grid {
    padding: 20px 40px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
    gap: 25px;
}

/* FOOTER (YOUR EXACT FOOTER) */
.footer {
    background-color: #4a007d;
    color: white;
    padding: 40px 20px;
    margin-top: 40px;
}

.footer-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 30px;
    padding: 0 40px;
}

.footer-section h3,
.footer-section h4 {
    margin-bottom: 10px;
}

.footer-section a {
    color: #e3d4ff;
    text-decoration: none;
    display: block;
    margin: 5px 0;
}

.footer-section a:hover {
    color: white;
}

.social-icons i {
    margin-right: 10px;
    cursor: pointer;
}

.footer-bottom {
    text-align: center;
    padding-top: 20px;
    font-size: 14px;
}

</style>

</head>

<body>

<!-- NAVBAR (UNCHANGED) -->
<div class="navbar">
    <div style="display: flex; align-item: center;">
	<img src="../IMAGE/Logo/main_logo.png" alt="" height="" width="98" style="margin-right: 15px; border-radius: 5px;">
    <div class="logo" style="display: flex; align-items: center;">Online Medicine Store</div>
</div>

    <div class="nav-links">
        <% if (!isLoggedIn) { %>

            <a href="about.jsp">About Us</a>
            <a href="../HTML/user_registration_form.html">Reg Form</a>
            <a href="../HTML/adminLogin.html">Admin Login</a>
            <a href="../HTML/loginCustomer.html">Customer Login</a>

        <% } else { %>

            <% if (customerUser != null) { %>
                <span>Welcome, <%= customerUser %></span>
            <% } else if (adminUser != null) { %>
                <span>Admin: <%= adminUser %></span>
            <% } %>
            <a href="about.jsp">About Us</a>

            <a href="<%= dashboardLink %>">Dashboard</a>
            <a href="../Logout">Logout</a>

        <% } %>
    </div>
</div>

<!-- ================= SLIDER ================= -->
<div class="slider">
    <div class="slides">
        <img src="../IMAGE/IndexImages/banner7.jpg">
        <img src="../IMAGE/IndexImages/banner3.jpg">
        <img src="../IMAGE/IndexImages/banner4.jpg">
        <img src="../IMAGE/IndexImages/banner5.jpg">
    </div>
</div>

<!-- CATEGORY SECTION -->
<h2 class="section-title">Shop by Category</h2>
<div class="category-grid">
    <div class="category-card">Vitamins</div>
    <div class="category-card">Pain Relief</div>
    <div class="category-card">Skin Care</div>
    <div class="category-card">Baby Products</div>
    <div class="category-card">Cold & Cough</div>
    <div class="category-card">Personal Care</div>
</div>

<!-- PRODUCT CAROUSEL -->
<div class="carousel-container">
    <h2 class="carousel-title">Trending Medicines</h2>

    <div class="carousel">
        <div class="product-card">
            <img src="../IMAGE/IndexImages/med1.jpg">
            <div class="product-name">Paracetamol</div>
            <div class="product-price">₹30</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>

        <div class="product-card">
            <img src="../IMAGE/IndexImages/c.jpg">
            <div class="product-name">Vitamin C</div>
            <div class="product-price">₹80</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>

        <div class="product-card">
            <img src="../IMAGE/IndexImages/med8.jpg">
            <div class="product-name">Pain Relief Spray</div>
            <div class="product-price">₹150</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>

        <div class="product-card">
            <img src="../IMAGE/IndexImages/med4.jpg">
            <div class="product-name">Vitamin D</div>
            <div class="product-price">₹220</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>
        
        <div class="product-card">
            <img src="../IMAGE/IndexImages/med3.jpg">
            <div class="product-name">Cough Syrup</div>
            <div class="product-price">₹150</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>
        
        <div class="product-card">
            <img src="../IMAGE/IndexImages/med5.jpg">
            <div class="product-name">Insulin</div>
            <div class="product-price">₹150</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>
        
        <div class="product-card">
            <img src="../IMAGE/IndexImages/med6.jpg">
            <div class="product-name">Dibetic</div>
            <div class="product-price">₹150</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>
        <div class="product-card">
            <img src="../IMAGE/IndexImages/med7.jpg">
            <div class="product-name">Injection</div>
            <div class="product-price">₹150</div>
            <button class="add-btn" style="background: green;">Available</button>
        </div>
    </div>
</div>

<!-- BEST SELLERS -->
<h2 class="section-title">Best Sellers</h2>
<div class="best-grid">
    <div class="product-card">
        <img src="../IMAGE/IndexImages/med3.jpg">
        <div class="product-name">Cough Syrup</div>
        <div class="product-price">₹120</div>
    </div>

    <div class="product-card">
        <img src="../IMAGE/IndexImages/med1.jpg">
        <div class="product-name">Multivitamin Tablets</div>
        <div class="product-price">₹299</div>
    </div>

    <div class="product-card">
        <img src="../IMAGE/IndexImages/med2.jpg">
        <div class="product-name">Disprin Tablets</div>
        <div class="product-price">₹50</div>
    </div>

    <div class="product-card">
        <img src="../IMAGE/IndexImages/med10.jpg">
        <div class="product-name">Energy Drink</div>
        <div class="product-price">₹99</div>
    </div>
</div>

<!-- 🌟 FOOTER -->
<footer class="footer">
    <div class="footer-container">

        <div class="footer-section">
            <h3>Medicine Store</h3>
            <p>Your trusted partner for online medicine delivery.</p>
        </div>

        <div class="footer-section">
            <h4>Quick Links</h4>
            <a href="index.jsp">Home</a>
            <a href="about.jsp">About Us</a>
            <a href="<%= dashboardLink %>">Dashboard</a>
        </div>

        <div class="footer-section">
            <h4>Support</h4>
            <p>Email: support@healthbasket.com</p>
            <p>Phone: +91 12345 67890</p>
        </div>

        <div class="footer-section">
            <h4>Follow Us</h4>
            <div class="social-icons">
                <i class="fab fa-facebook"></i>
                <i class="fab fa-instagram"></i>
                <i class="fab fa-twitter"></i>
            </div>
        </div>

    </div>

    <div class="footer-bottom">
        © 2025 Medicine Store | All Rights Reserved
    </div>
</footer>

</body>
</html>