

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
<%!
            String vname, vacc, vusername, vphone, vemail, msg, pathLink, title, navtitle, modalImgPath;
            %>
        <%
            HttpSession sess = request.getSession(false);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
           try
{
            if(sess!=null) {
                vname = sess.getAttribute("fname").toString();
                
                msg = request.getAttribute("data").toString();
                pathLink = request.getAttribute("pathLink").toString();
                title = request.getAttribute("title").toString();
                navtitle = request.getAttribute("navtitle").toString();
                modalImgPath = request.getAttribute("modalImgPath").toString();
            }
}
catch(Exception ex)
{
                %>
                <script>
//                    alert("Session was not created!!!");
//                    alert("Redirecting for logging==>>");
                    location.href="/online-medicine-purchase-system/HTML/ALERT/session_error.html";
                </script>
                <%  
}
            %> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=title%></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="shortcut icon" href="/online-medicine-purchase-system/IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="/online-medicine-purchase-system/CSS/navbar.css">
    <link rel="stylesheet" href="/online-medicine-purchase-system/CSS/login.css">
</head>

<body>
    
    <!-- Navbar starts here -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary nav-color bg-secondary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="/online-medicine-purchase-system/IMAGE/Logo/main_logo.png" alt="" height="0" width="98" class="img-fluid">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse pre-nav" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/online-medicine-purchase-system/HTML/index.html">Home</a>
                    </li>
                    <li class="nav-item active"  aria-current="page" >
                        <a class="nav-link" href="#"><%=navtitle%></a>
                    </li>
                </ul>

                

               
                <!-- Modal ends here  -->
                <a href="/online-medicine-purchase-system/Logout">
                    <button type="button" class="btn btn-help" data-bs-dismiss="modal">Sign Out</button>
                </a>

            </div>
        </div>
    </nav>
    
    <!-- Navbar ends here -->
    
    <button type="submit" data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="modalOpen" style="display: none">
                LOGIN
         </button>
          <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <img src="<%=modalImgPath%>" class="modal-img img-fluid" alt="">
                </div>
                <div class="modal-body">
                    <p><%=msg%></p>
                </div>
                <div class="modal-close">
                    <a href="<%=pathLink%>">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Thank You</button>
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



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>

</html>


