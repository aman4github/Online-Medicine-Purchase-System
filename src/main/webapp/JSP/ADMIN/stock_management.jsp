<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Stock Management</title>
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../../CSS/admin_panel.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
   
</head>

<style>
	nav a:hover {
        background-color: #F38BA0;
    }
</style>

<body style="background-color : #F38BA0">
    <%!
            String vname;
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
                <li><a href="adminPanel.jsp" class="navA">
                        <i class="fas fa-users-cog"></i>
                        <span class="nav-item">Admin Panel</span>
                    </a>
                </li>
                <li><a href="allMedicines.jsp">
                        <i class="fas fa-tablets"></i>
                        <span class="nav-item">All Medicines</span>
                    </a>
                </li>
                <li><a href="records.jsp">
                        <i class="fas fa-database"></i>
                        <span class="nav-item">All Records</span>
                    </a>
                </li>
                
                <li><a href="../../Logout" class="logout navA">
                        <i class="fas fa-sign-out-alt"></i>
                        <span class="nav-item">Log out</span>
                    </a>
                </li>
            </ul>
        </nav>

        <section class="main">
            <div class="main-top">
                <h1>Stock Management</h1>
                
            </div>
            <div class="main-skills">
                <div class="card">
                    <i class="fas fa-clinic-medical"></i>
                    <h3>Stock Availability</h3>
                    <p>Manage Inventory</p>
                    <a href="availability_check.jsp" class="cardBtnAdmin">
                        <button>Check</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-question-circle"></i>
                    <h3>Add / Remove Stock</h3>
                    <p>Manage Medicine</p>
                    <a href="add_remove_stock.jsp" class="cardBtnAdmin">
                        <button>ADD / REMOVE</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-plus-circle"></i>
                    <h3>Stock In</h3>
                    <p>Add Medicine</p>
                    <a href="stockIn.jsp" class="cardBtnAdmin">
                        <button>ADD</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-minus-circle"></i>
                    <h3>Stock Out</h3>
                    <p>Remove Medicine</p>
                    <a href="stockOut.jsp" class="cardBtnAdmin">
                        <button>Remove</button>
                    </a>
                </div>
            </div>
        </section>
    </div>
</body>

</html>