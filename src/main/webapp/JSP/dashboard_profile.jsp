

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Dashboard Profile</title>
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../CSS/dashboard_profile_edit.css" />
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
                <li>
                    <a href="user-dashboard.jsp">
                        <i class="fas fa-users-cog"></i>
                        <span class="nav-item">Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="dashboard_profile_password.jsp">
                        <i class="fas fa-lock"></i>
                        <span class="nav-item">Update</span>
                    </a>
                </li>
                
                <li>
                    <a href="../Logout" class="logout">
                        <i class="fas fa-sign-out-alt"></i>
                        <span class="nav-item">Log out</span>
                    </a>
                </li>
            </ul>
        </nav>

        <section class="main">
            <div class="main-top">
                <h1>Know Your Profile</h1>
                <!-- <i class="fas fa-user-cog"></i> -->
            </div>
            <div class="main-body main-body1" id="main-bodyId1">
                <div class="cardOut cardOut1">
                    <div class="card">
                        <label for="" class="details-label">Name</label>
                        <div class="details"><%=vname%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">Username</label>
                        <div class="details"><%=vusername%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">Phone Number</label>
                        <div class="details"><%=vphone%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">Email</label>
                        <div class="details"><%=vemail%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">Gender</label>
                        <div class="details"><%=vgender%></div>
                    </div>
                </div>
                <div class="cardOut">
                    <div class="card">
                        <label for="" class="details-label">Address</label>
                        <div class="details"><%=vaddress%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">City</label>
                        <div class="details"><%=vcity%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">Pin Code</label>
                        <div class="details"><%=vpin%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">District</label>
                        <div class="details"><%=vdistrict%></div>
                    </div>
                    <div class="card">
                        <label for="" class="details-label">State</label>
                        <div class="details"><%=vstate%></div>
                    </div>
                </div>
            </div>   
        </section>
    </div>
</body>

</html>