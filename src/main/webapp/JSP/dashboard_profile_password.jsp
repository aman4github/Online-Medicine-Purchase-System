
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Update Credentials Dashboard</title>
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../CSS/dashboard_profile_password.css" />
    <link rel="stylesheet" href="../CSS/spinner.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <script src="../JAVASCRIPT/dashboard_profile_password.js"></script>
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
                    <a href="dashboard_profile_address.jsp">
                        <i class="fas fa-location-arrow"></i>
                        <span class="nav-item">Address Update</span>
                    </a>
                </li>
                
                <li>
                    <a href="dashboard_profile.jsp">
                        <i class="fas fa-info-circle"></i>
                        <span class="nav-item">Details</span>
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
                        
<!--        <div class="spinner_outer" id="spinner_outer">
		<div class="spinner"></div>
	</div>-->

        <section class="main">
            <div class="main-top">
                <h1>Change Details Dashboard</h1>
                <!-- <i class="fas fa-user-cog"></i> -->
            </div>
            <div class="main-body main-body1" id="main-bodyId1">
                <div class="cardOut cardOut1">
                    <form action="../UserNameChangeDashboard" method="post" class="passUpdateForm">
                        <div class="card">
                            <label for="" class="details-label">New Username</label>
                            <div class="details">
                                <input type="text" name="username" class="passUpdateInput" id="newUser" oninput="validate()" required>
                            </div>
                            <div id="userError" class="error-message" style="color: red;"></div>
                        </div>
                        <input type="text" name="fusername" value="<%=vusername%>" style="display: none;">
                        <input type="text" name="femail" value="<%=vemail%>" style="display: none;">
                        <input type="text" name="fname" value="<%=vname%>" style="display: none;">
                        <input type="text" name="fmsg" value="USERNAME" style="display: none;">
                        <div class="card">
                            <button type="submit" onclick="hideshow()" class="passUpdateForm" id="formBtnUser" disabled>Update Username</button>
                        </div>
                    </form>
                        
                    <form action="../PassChangeDashboard" method="post" class="passUpdateForm">
                        <div class="card">
                            <label for="" class="details-label">New Password</label>
                            <div class="details">
                                <input type="password" name="fpass" class="passUpdateInput" id="newPass" oninput="passValidate()" required>
                            </div>
                            <div id="passwordError" class="error-message" style="color: red;"></div>
                        </div>
                        <div class="card">
                            <label for="" class="details-label">Confirm Password</label>
                            <div class="details">
                                <input type="password" class="passUpdateInput" id="confirmPass" oninput="passCheck()" required>
                            </div>
                            <div id="passwordError2" class="error-message" style="color: red;"></div>
                        </div>
                        
                        <input type="text" name="fUsername" value="<%=vusername%>" style="display: none;">
                        <input type="text" name="femail" value="<%=vemail%>" style="display: none;">
                        <input type="text" name="fname" value="<%=vname%>" style="display: none;">
                        <input type="text" name="fmsg" value="PASSWORD" style="display: none;">
                        <div class="card">
                            <button type="submit" class="passUpdateForm" id="formBtnPass" disabled>Update Password</button>
                        </div>
                    </form>
                </div>
                
                
                
            </div>   
        </section>
    </div>
         
    <script>
		var showSpinner = document.getElementById("spinner_outer");

		function hideshow() {
			showSpinner.style.display = "flex";
		}
    </script>
</body>

</html>
