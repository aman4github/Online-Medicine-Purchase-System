<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    // ===============================
    // ADMIN SESSION CHECK
    // ===============================
    String adminUser = (String) session.getAttribute("adminName");
    if (adminUser == null) adminUser = (String) session.getAttribute("fnameadmin");

    // ===============================
    // CUSTOMER SESSION CHECK
    // ===============================
    String customerUser = (String) session.getAttribute("customerName");
    if (customerUser == null) customerUser = (String) session.getAttribute("fname");

    // ===============================
    // LOGIN CHECK
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
<html lang="en">

<head>
<meta charset="UTF-8">
<title>About Us - Medicine Store</title>
<link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
<!-- Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

<style>
/* ==================== GLOBAL ==================== */
body {
    margin: 0;
    font-family: 'Poppins', sans-serif;
    background: #faf7fb;
}

/* ==================== NAVBAR ==================== */
.navbar {
    background: linear-gradient(90deg, #6a00ff, #a64cff);
    padding: 14px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
    box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}

.navbar .logo {
    font-size: 22px;
    font-weight: bold;
}

.navbar .nav-links {
    display: flex;
    align-items: center;
}

.navbar .nav-links a {
    color: white;
    text-decoration: none;
    margin-left: 22px;
    font-size: 15px;
    padding: 8px 14px;
    border-radius: 5px;
    transition: 0.3s;
}

.navbar .nav-links a:hover {
    background: white;
    color: #6a00ff;
}

.navbar .welcome-text {
    margin-right: 18px;
    font-weight: bold;
}

/* ==================== HERO SECTION ==================== */
.hero {
    background: linear-gradient(135deg, #6a00ff, #a64cff);
    padding: 80px 40px;
    text-align: center;
    color: white;
}

.hero h1 {
    font-size: 40px;
    font-weight: 600;
}

.hero p {
    font-size: 18px;
    margin-top: 10px;
}

/* ==================== ABOUT SECTION ==================== */
.about-section {
    padding: 50px 40px;
    display: flex;
    gap: 40px;
    align-items: center;
    justify-content: center;
}

.about-section img {
    width: 400px;
    border-radius: 18px;
    box-shadow: 0 6px 18px rgba(0,0,0,0.15);
}

.about-text {
    max-width: 600px;
}

.about-text h2 {
    color: #6a00ff;
    font-size: 28px;
    margin-bottom: 10px;
}

.about-text p {
    font-size: 16px;
    color: #555;
    line-height: 1.7;
}

/* ==================== MISSION SECTION ==================== */
.mission-box {
    background: white;
    margin: 40px;
    padding: 40px;
    border-radius: 18px;
    box-shadow: 0 3px 15px rgba(0,0,0,0.1);
}

.mission-box h2 {
    color: #6a00ff;
    font-size: 28px;
}

.mission-box p {
    color: #444;
    line-height: 1.7;
    font-size: 16px;
    margin-top: 10px;
}

/* ==================== WHY CHOOSE US ==================== */
.features {
    padding: 40px;
}

.features h2 {
    text-align: center;
    font-size: 28px;
    color: #6a00ff;
}

.feature-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    gap: 25px;
    margin-top: 30px;
}

.feature-card {
    background: white;
    padding: 25px;
    border-radius: 16px;
    text-align: center;
    box-shadow: 0 2px 12px rgba(0,0,0,0.12);
    transition: 0.3s;
}

.feature-card:hover {
    transform: translateY(-6px);
}

.feature-card i {
    font-size: 35px;
    color: #7d2cff;
    margin-bottom: 15px;
}

.feature-card h3 {
    color: #4a007d;
    margin-bottom: 10px;
}

/* ==================== FOOTER ==================== */
.footer {
    background: #4a007d;
    color: white;
    padding: 40px 20px 10px;
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
    display: block;
    text-decoration: none;
    color: #e3d4ff;
    margin: 5px 0;
}
.footer-section a:hover {
    color: white;
}

.social-icons i {
    font-size: 20px;
    margin-right: 10px;
    cursor: pointer;
}

.footer-bottom {
    text-align: center;
    padding-top: 20px;
    font-size: 14px;
    opacity: 0.8;
}

</style>
</head>

<body>

<!-- 🌟 NAVBAR -->
<div class="navbar">
    <div class="logo">Medicine Store</div>

    <div class="nav-links">

        <% if (!isLoggedIn) { %>
            <a href="index.jsp">Home</a>
            <a href="../HTML/adminLogin.html">Admin Login</a>
            <a href="../HTML/loginCustomer.html">Customer Login</a>

        <% } else { %>

            <% if (customerUser != null) { %>
                <span class="welcome-text">Welcome, <%= customerUser %></span>
            <% } else if (adminUser != null) { %>
                <span class="welcome-text">Admin: <%= adminUser %></span>
            <% } %>

            <a href="index.jsp">Home</a>
            <a href="<%= dashboardLink %>">Dashboard</a>
            <a href="../Logout">Logout</a>

        <% } %>

    </div>
</div>


<!-- 🌟 HERO SECTION -->
<div class="hero">
    <h1>About Online Medicine Store</h1>
    <p>Your trusted partner for safe & fast medicine delivery.</p>
</div>


<!-- 🌟 ABOUT SECTION -->
<div class="about-section">
    <!-- <img src="https://i.imgur.com/Zu9j3p0.png" /> -->
	<img src="../IMAGE/About/about.jpg" />
    <div class="about-text">
        <h2>Who We Are</h2>
        <p>
            We are a modern online pharmacy platform offering verified medicines, healthcare essentials,
            and wellness products delivered right to your doorstep.
            <br><br>
            Our goal is to make healthcare accessible, affordable, and convenient for everyone.
        </p>
    </div>
</div>


<!-- 🌟 MISSION / VISION -->
<div class="mission-box">
    <h2>Our Mission</h2>
    <p>
        To provide fast, reliable, and affordable access to genuine medicines
        through a seamless online experience.
    </p>

    <br>
    <h2>Our Vision</h2>
    <p>
        To become India’s most trusted online pharmacy by offering quality products,
        transparent services, and exceptional customer support.
    </p>
</div>


<!-- 🌟 WHY CHOOSE US -->
<div class="features">
    <h2>Why Choose Us?</h2>

    <div class="feature-grid">

        <div class="feature-card">
            <i class="fas fa-truck-fast"></i>
            <h3>Fast Delivery</h3>
            <p>Super quick doorstep delivery across all major cities.</p>
        </div>

        <div class="feature-card">
            <i class="fas fa-certificate"></i>
            <h3>100% Genuine Medicines</h3>
            <p>All products are verified & approved for safety and quality.</p>
        </div>

        <div class="feature-card">
            <i class="fas fa-headset"></i>
            <h3>24/7 Support</h3>
            <p>Our team is always available to help you with anything.</p>
        </div>

        <div class="feature-card">
            <i class="fas fa-wallet"></i>
            <h3>Affordable Prices</h3>
            <p>Exclusive discounts and offers on regular orders.</p>
        </div>

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
