
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password OTP Verification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../CSS/navbar.css">
    <link rel="stylesheet" href="../CSS/login.css">
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
                        <a class="nav-link" href="#">Customer OTP Verification Form</a>
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
            String vname, votp, vemail;
            %>
        <%
            HttpSession sess = request.getSession(false);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            try
{
            if(sess!=null) 
            {
                vname = sess.getAttribute("fname").toString();
                votp = sess.getAttribute("otp").toString();
                vemail = sess.getAttribute("email").toString();
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
            if(request.getParameter("bVerify")!=null)
            {
                if(request.getParameter("tbOTP").equals(votp))
                {
                %>
                <button type="submit" data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="modalOpen" style="display: none">
                LOGIN
         </button>
          <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <img src="../IMAGE/Modal/checked.png" class="modal-img img-fluid" alt="">
                </div>
                <div class="modal-body">
                    <p>OTP verification successful.</p>
                </div>
                <div class="modal-close">
                    <a href="newForgotPassCustomer.jsp">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Next</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
     <script>
            window.onload = function(){
            document.getElementById('modalOpen').click();
            }
      </script>
                
                <%
                }
                else
                {
                       %> 
                       <button type="submit" data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="modalOpen" style="display: none">
                LOGIN
         </button>
         <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <img src="../IMAGE/Modal/cross.png" class="modal-img img-fluid" alt="">
                </div>
                <div class="modal-body">
                    <p>Wrong OTP. Please try again!!!</p>
                </div>
                <div class="modal-close">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <script>
            window.onload = function(){
            document.getElementById('modalOpen').click();
            }
      </script>
                       <!--<h3 style="color:red">Wrong OTP. Please try again!!!</h3>-->                 
                        <%
                }
            }
            else
            {
                   %>
                   <button type="submit" data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="modalOpen" style="display: none">
                LOGIN
         </button>
         <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <img src="../IMAGE/Modal/email.png" class="modal-img img-fluid" alt="">
                </div>
                <div class="modal-body">
                    <p>Welcome <%=vname%> .<br>
                        Mail has been sent to your registered email - <%=vemail%> .</p>
                </div>
                <div class="modal-close">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Verify</button>
                </div>
            </div>
        </div>
    </div>
    <script>
            window.onload = function(){
            document.getElementById('modalOpen').click();
            }
      </script>
                   
                    <%
            }   
            %> 

    <div class="wrapper">
        <form action="#" method="post">
            <h2 style="color: white;">OTP VERIFICATION</h2>
            <div class="input-field">
                <input type="text" name="tbOTP" required>
                <label>OTP</label>
            </div>
            <button type="submit" name="bVerify">
                VERIFY
            </button>

            <!-- Modal -->

        </form>
    </div>
    
    
    
      

      
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>

</html>