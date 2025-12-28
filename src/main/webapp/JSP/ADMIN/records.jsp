<%@page import="java.sql.DriverManager"%>
<%@page import="oracle.jdbc.OracleResultSet"%>
<%@page import="oracle.jdbc.OracleResultSetMetaData"%>
<%@page import="oracle.jdbc.OraclePreparedStatement"%>
<%@page import="oracle.jdbc.OracleConnection"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Records Panel</title>
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
        background-color: #A7AAE1;
    }

    .card input {
        border: 1.5px solid #F875AA;
        border-radius: 5px;
        height: 31px;
        width: 200px;
        padding: 3px 3px 3px 5px;

    }

    .balance {
        display: flex;
        justify-content: space-around;
    }

    .balanceView {
        width: 85px;
        border: 1.5px solid #F875AA;
        padding: 2px;
        border-radius: 5px;
        /* display: none; */
    }

    .card button {
        margin-top: 10px !important;
    }

    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>
</head>

<body style="background-color : #A7AAE1">
    <%!
            String vname, vacc, vaccType, vbalance;
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
                <h1>Records Panel</h1>
                
            </div>
            <div class="main-skills">
                <form action="allRecordsById.jsp" method="post">
                    <div class="card">
                        <i class="fas fa-table"></i>
                        <h3>Records By Customer Id</h3>
                        <div class="balance">

                            <input type="number" name="medName" id="accNum" placeholder="Enter Medicine id..." required oninput="validate()">


                            
                        </div>

                        
                        <button type="submit" disabled id="transferBtn">Check</button>
                        
                    </div>
                </form>
                <div class="card">
                    <i class="fas fa-database"></i>
                    <h3>All Records</h3>
                    <p>Record List</p>
                    <a href="allRecords.jsp" class="cardBtnAdmin">
                        <button>Check</button>
                    </a>
                </div>
                
            </div>
        </section>
                        
    </div>
    <script>
        function validate(){
            const accNum = document.getElementById("accNum");
            console.log(accNum.value.length)
            if(accNum.value.length==6)
            {
                document.getElementById("transferBtn").disabled=false;
            }
            else{
                document.getElementById("transferBtn").disabled=true;
            }
        }

    </script>
</body>

</html>