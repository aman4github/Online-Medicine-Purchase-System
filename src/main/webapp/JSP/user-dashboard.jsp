
<%@page import="java.sql.DriverManager"%>
<%@page import="oracle.jdbc.OracleResultSetMetaData"%>
<%@page import="oracle.jdbc.OracleResultSet"%>
<%@page import="oracle.jdbc.OraclePreparedStatement"%>
<%@page import="oracle.jdbc.OracleConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    String shortText(String s) {
        if (s == null) return "";
        int limit = 22; // show first 20 chars
        if (s.length() <= limit) return s;
        return s.substring(0, limit) + "...";
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>User Dashboard</title>
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../CSS/user_dashboard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
</head>

<body>
    <%!
            String vname, vusername, vphone, vemail, vpassword, vaddress, vcity, vgender, vpin, vdistrict, vstate;
            %>
        <%
            HttpSession sess = request.getSession(false);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
           try
{
            if(sess!=null) {
            	vname = sess.getAttribute("fname").toString();
            	vusername = sess.getAttribute("fusername").toString();
            	vphone = sess.getAttribute("fphone").toString();
            	vemail = sess.getAttribute("femail").toString();
            	vpassword = sess.getAttribute("fpassword").toString();
            	vaddress = sess.getAttribute("faddress").toString();
            	vcity = sess.getAttribute("fcity").toString();
            	vgender = sess.getAttribute("fgender").toString();
            	vpin = sess.getAttribute("fpin").toString();
            	vdistrict = sess.getAttribute("fdistrict").toString();
            	vstate = sess.getAttribute("fstate").toString();
            }      
}
catch(Exception ex)
{
                %>
                <script>
//                    alert("Session was not created!!!");
//                    alert("Redirecting for logging==>>");
                    location.href="../HTML/ALERT/session_error.html";
                </script>
                <%  
}
            %> 
            <%!
        // STEP 3: DECLARING OBJECTS AND VARIABLES
            OracleConnection oconn;
            OraclePreparedStatement ops;
            OracleResultSet ors;
        %>
        <%
            // STEP 4: REGISTRATION OF ORACLE DRIVER
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            
            // STEP 5: INSTANTIATING TH             E CONNECTION
            oconn = (OracleConnection) DriverManager.getConnection ("jdbc:oracle:thin:@LOCALHOST:1521:XE","HEALTH_BASKET","DATABASE");
            
            // STEP 6: INSTANTIATING THE STATEMENT OBJECT
            ops = (OraclePreparedStatement)oconn.prepareCall("SELECT * FROM CUSTOMER WHERE USERNAME = ?");
            ops.setString(1, vusername);
            ops.executeUpdate();
            ors = (OracleResultSet)ops.executeQuery();
            
            if(ors.next()) {
            	vname = ors.getString("NAME");
            }
            
            
        %>
        
        
    <div class="container">
        <nav>
            <ul>
                <li>
                    <a href="#" class="logo logoImg">
                        <img src="../IMAGE/Logo/logo.png" alt="">
                        
                    </a>
                </li>
                <li>
                    <a href="#" class="logo logoText">
                        <span class="nav-item nav-item1">
                            Welcome<br> <%=vname%>
                        </span>
                    </a>
                </li>
                <li><a href="index.jsp">
                        <i class="fas fa-home"></i>
                        <span class="nav-item">Home</span>
                    </a>
                </li>
                <li><a href="dashboard_profile.jsp">
                    <i class="fas fa-user-cog"></i>
                        <span class="nav-item">Settings / Info</span>
                    </a>
                </li>
                
                <li><a href="../Logout" class="logout">
                        <i class="fas fa-sign-out-alt"></i>
                        <span class="nav-item">Log out</span>
                    </a>
                </li>
            </ul>
        </nav>

        <section class="main">
            <div class="main-top">
                <h1>Dashboard</h1>
                
            </div>
            <div class="main-skills">
                <div class="card">
                    <i class="fas fa-user-alt"></i>
                    <h3>Account Details</h3>
                    <p>Name : <%=vname%><br>Phone : <%=vphone%><br>Email : <%= shortText(vemail) %></p>
                </div>
                <div class="card">
                    <i class="fas fa-shopping-bag"></i>
                    <h3>Medicine Shop</h3>
                    <p>Purchase Your Medicine</p>
                    <form action="shopCustomer.jsp" method="post">
                        <button type="submit">Buy</button>
                    </form>
                </div>
                <div class="card">
                    <i class="fas fa-shopping-cart"></i>
                    <h3>Cart</h3>
                    <p>Your Cart List</p>
                    <form action="cartCustomer.jsp" method="post">
                        <input type="text" name="facc" value="" style="display: none;">
                        <button type="submit">Check</button>
                    </form>
                </div>
                <div class="card">
                    <i class="fas fa-history"></i>
                    <h3>Orders</h3>
                    <p>Check Your Orders</p>
                    <form action="ordersCustomer.jsp" method="post">
                        
                        <button type="submit">Check</button>
                    </form>
                </div>
            </div>
        </section>
    </div>
</body>

</html>

