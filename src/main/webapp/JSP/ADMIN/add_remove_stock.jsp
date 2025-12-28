<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Add/Remove Stock</title>
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../../CSS/admin_panel.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
    <style>
        .card {
            margin-left: 75px;
            margin-top: 75px !important;
        }
        .main-skills {
            align-content: center;
        }
        nav a:hover {
            background-color: #A0E9FF;
        }
    </style>
</head>

<body style="background-color : #A0E9FF">
    <%!
            String vname, vacc, vaccType;
            %>
        <%
            HttpSession sess = request.getSession(false);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
           try
{
            if(sess!=null){
                vname = sess.getAttribute("fnameadmin").toString();
                
            }         
}
catch(Exception ex)
{
                %>
                <script>
//                    alert("Session was not created!!!");
//                    alert("Redirecting for logging==>>");
                    location.href="../../HTML/ALERT/session_error_admin.html";
                </script>
                <%  
}
            %> 
    <div class="container">
        <nav>
            <ul>
                <li>
                    <a href="#" class="logo logoImg navA">
                        <img src="../../IMAGE/Logo/logo.png" alt="">
                        
                    </a>
                </li>
                <li>
                    <a href="#" class="logo logoText navA">
                        <span class="nav-item nav-item1">
                            Admin<br><%=vname%>
                        </span>
                    </a>
                </li>
                <li><a href="stock_management.jsp" class="navA">
                        <i class="fas fa-backward"></i>
                        <span class="nav-item">Stock Panel</span>
                    </a>
                </li>
<!--                <li><a href="">
                        <i class="fas fa-cog"></i>
                        <span class="nav-item">Settings</span>
                    </a>
                </li>
                <li><a href="">
                        <i class="fas fa-question-circle"></i>
                        <span class="nav-item">Help</span>
                    </a>
                </li>-->
                <li><a href="../../Logout" class="logout navA">
                        <i class="fas fa-sign-out-alt"></i>
                        <span class="nav-item">Log out</span>
                    </a>
                </li>
            </ul>
        </nav>

        <section class="main">
            <div class="main-top">
                <h1>Add/Remove</h1>
                
            </div>
            <div class="main-skills">
                <div class="card">
                    <i class="fas fa-plus-square"></i>
                    <h3>Add Medicine</h3>
                    <p>Addition Section</p>
                    <a href="addStock.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-minus-square"></i>
                    <h3>Remove Medicine</h3>
                    <p>Removal Section</p>
                    <a href="removeStock.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
                
            </div>
        </section>
    </div>
</body>

</html>