

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Setup New Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="../CSS/navbar.css">
    <link rel="stylesheet" href="../CSS/login.css">
    <script src="../JAVASCRIPT/dashboard_profile_password.js"></script>
</head>

<body>

    <!-- Navbar starts here -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary nav-color bg-secondary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="../IMAGE/Logo/main_logo.png" alt="" height="0" width="98" class="img-fluid">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse pre-nav" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="../Logout">Home</a>
                    </li>
                    <li class="nav-item active"  aria-current="page" >
                        <a class="nav-link" href="#">Setup New Password</a>
                    </li>
                </ul>


            <!-- Modal ends here  -->
            <a href="../Logout">
                <button type="button" class="btn btn-help" data-bs-dismiss="modal">Sign Out</button>
            </a>

            </div>
        </div>
    </nav>
<!-- Navbar ends here -->
<%!
            String vname, vusername;
            %>
        <%
            HttpSession sess = request.getSession(false);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
           try
{
            if(sess!=null){
                vname = sess.getAttribute("fname").toString();
                vusername = sess.getAttribute("fusername").toString();
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

    <div class="wrapper">
        <form action="../NewPassUpdate" method="post">
            <h2 style="color: white;">Forgot Password</h2>
            <div class="input-field">
                <input type="password"  id="newPass" name="fpass" oninput="passValidate()" required>
                <label>Enter Your New Password</label>
            </div>
            <div id="passwordError" class="error-message" style="color: red;"></div>
            <div class="input-field">
                <input type="password" id="confirmPass" oninput="passCheck()" required>
                <label>Confirm Your New Password</label>
            </div>
            <div id="passwordError2" class="error-message" style="color: red; padding-bottom: 10px;"></div>
            <input type="text" name="fUsername" value="<%=vusername%>" style="display: none;">
            <button type="submit" id="formBtnPass" disabled>Submit</button>
            
        </form>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>

</html>
