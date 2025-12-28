<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Admin Panel</title>
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../../CSS/admin_panel.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
   
</head>

<body>
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
                <li><a href="../index.jsp" class="navA">
                        <i class="fas fa-home"></i>
                        <span class="nav-item">Home</span>
                    </a>
                </li>
                <li><a href="allAccounts.jsp">
                        <i class="fas fa-user-circle"></i>
                        <span class="nav-item">All Accounts</span>
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
                <h1>Actions</h1>
                
            </div>
            <div class="main-skills">
                <div class="card">
                    <i class="fas fa-user-alt"></i>
                    <h3>Stock Management</h3>
                    <p>Manage Inventory</p>
                    <a href="stock_management.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-money-bill-wave"></i>
                    <h3>Delivery</h3>
                    <p>Delivery Approvals</p>
                    <a href="deliveryStatusAdmin.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-money-check-alt"></i>
                    <h3>Transactions</h3>
                    <p>Check All Transactions</p>
                    <a href="transactionsTracking.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
                <div class="card">
                    <i class="fas fa-user-alt-slash"></i>
                    <h3>Close Account</h3>
                    <p>Account Closer Section</p>
                    <a href="accountClosure.jsp" class="cardBtnAdmin">
                        <button>Get Started</button>
                    </a>
                </div>
            </div>
        </section>
    </div>
</body>

</html>