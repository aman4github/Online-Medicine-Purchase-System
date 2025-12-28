
<%@page import="java.sql.DriverManager"%>
<%@page import="oracle.jdbc.OraclePreparedStatement"%>
<%@page import="oracle.jdbc.OracleResultSetMetaData"%>
<%@page import="oracle.jdbc.OracleResultSet"%>
<%@page import="oracle.jdbc.OracleConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    String shortText(String s) {
        if (s == null) return "";
        int limit = 15; // show first 20 chars
        if (s.length() <= limit) return s;
        return s.substring(0, limit) + "...";
    }
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Delivery</title>
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../../CSS/allUsers.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
</head>

<body style="background-color: #F5F8DE">
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
    
<!--    <div class="container">
        <nav>
            <ul>
                <li>
                    <a href="#" class="logo logoImg">
                        <img src="../img/Logo/icon-logo.png" alt="">
                        
                    </a>
                </li>
                <li>
                    <a href="#" class="logo logoText">
                        <span class="nav-item nav-item1">
                            Welcome<br> 
                        </span>
                    </a>
                </li>
                <li><a href="#">
                        <i class="fas fa-home"></i>
                        <span class="nav-item">Home</span>
                    </a>
                </li>
                <li><a href="dashboard_profile.jsp">
                    <i class="fas fa-user-cog"></i>
                        <span class="nav-item">Settings / Info</span>
                    </a>
                </li>
                <li><a href="">
                        <i class="fas fa-question-circle"></i>
                        <span class="nav-item">Help</span>
                    </a>
                </li>
                <li><a href="../Logout" class="logout">
                        <i class="fas fa-sign-out-alt"></i>
                        <span class="nav-item">Log out</span>
                    </a>
                </li>
            </ul>
        </nav>-->
<%!
        // STEP 3: DECLARING OBJECTS AND VARIABLES
            OracleConnection oconn;
            OraclePreparedStatement ops;
            OracleResultSet ors;
            OracleResultSetMetaData orsmd;
            int counter, reccounter, colcounter;
            String vstateacc;
        %>
        <%
	       String vCusID = request.getParameter("cusid");
//            vstateacc = request.getParameter("facc");
            // STEP 4: REGISTRATION OF ORACLE DRIVER
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            
            // STEP 5: INSTANTIATING TH             E CONNECTION
            oconn = (OracleConnection) DriverManager.getConnection ("jdbc:oracle:thin:@LOCALHOST:1521:XE","HEALTH_BASKET","DATABASE");
            
            // STEP 6: INSTANTIATING THE STATEMENT OBJECT
            //ops = (OraclePreparedStatement)oconn.prepareCall("SELECT * FROM STOCK_RECORD ORDER BY RECORD_DATE");
            ops = (OraclePreparedStatement)oconn.prepareCall("SELECT ORDER_TIME, ORDER_ID, CUSTOMER_ID, STOCK_NAME AS MEDICINE_NAME, QUANTITY, TOTAL_PRICE, PAYMENT_TYPE, DELIVERY_STATUS FROM ORDERS WHERE CUSTOMER_ID = ? AND DELIVERY_STATUS = 'PENDING' ORDER BY ORDER_TIME DESC");
            		
            ops.setString(1, vCusID);
            

//            ops.setString(1, vstateacc);
            // STEP 7: FILLING UP THE DATABASE RECORDS IN A TEMPORARY CONTAINER
            ors = (OracleResultSet)ops.executeQuery();
            
            // STEP 8: GETTING THE COLUMNS INFORMATION(METADATA)
            orsmd = (OracleResultSetMetaData)ors.getMetaData();
            %>
        <section class="main">
            <div class="main-top">
                <h1>Delivery Status</h1>
                <a href="deliveryStatusAdmin.jsp">
                    <i class="fas fa-backward"></i>
                </a>
                
            </div>
            <div class="main-skills">
                <div class="cardTable">
                    
                    
                    <table>
                        <thead>
                            <tr>
                    <%
                        // STEP 9: BRINGING THE TABLE HEADINGS
                        for(counter = 1; counter <= orsmd.getColumnCount(); counter ++)
                        {
                        %>
                        <th><center><%=orsmd.getColumnName(counter)%></center></th>
                        <%
                            }
                        %>
                                <th>
                                    <center>ACTIONS</center>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                <%
                    // STEP 10: BRINGING ALL THE RECORDS AND DISPLAYING AS TABLE ROWS
                    while(ors.next() == true)
                    {
                    %>
                            <tr>
                    <%
                       for(counter = 1; counter <= orsmd.getColumnCount(); counter ++)
                        {
                        %>
                        <td><center><p><%= shortText(ors.getString(counter)) %></p></center></td>
                        
                        <%
                            }
                        %>
                             <td>
                                    <form action="../../deliveryConfirmationMail" method="post">
                                        <input type="text" name="fcusid" value="<%=ors.getString("CUSTOMER_ID")%>" style="display: none;">
                                        <input type="text" name="fordid" value="<%=ors.getString("ORDER_ID")%>" style="display: none;">
                                        <input type="text" name="fmsg" value="DELIVERED" style="display: none;">
                                        <input type="text" name="floc" value="useridloc" style="display: none;">
                                        <button type="submit" class="actionkyc" style="margin-bottom: 5px; background: green;">DELIVERED</button><br>
                                    </form>
                                    <form action="../../deliveryConfirmationMail" method="post">
                                        <input type="text" name="fcusid" value="<%=ors.getString("CUSTOMER_ID")%>" style="display: none;">
                                        <input type="text" name="fordid" value="<%=ors.getString("ORDER_ID")%>" style="display: none;">
                                        <input type="text" name="floc" value="useridloc" style="display: none;">
                                        <input type="text" name="fmsg" value="DECLINED" style="display: none;">
                                        <button type="submit" class="actionkyc" style="background: red;">DECLINED</button><br>
                                    </form>
                                </td>  
                            </tr>
                <%
                    }
                    %>
                        </tbody>
                        
                    </table>
                    <%
                        // STEP 11: CLOSING THE CONNECTIONS
                        ors.close();
                        ops.close();
                        oconn.close();
                        %>
                </div>
                
            </div>
        </section>
    
</body>

</html>


