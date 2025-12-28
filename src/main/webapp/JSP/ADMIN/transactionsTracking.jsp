
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
    <title>Transactions Tracking</title>
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../../CSS/allUsers.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
</head>

<body style="background-color: #DAEFB3">
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
  
<%!
        // STEP 3: DECLARING OBJECTS AND VARIABLES
            OracleConnection oconn;
            OraclePreparedStatement ops, ops2;
            OracleResultSet ors, ors2;
            OracleResultSetMetaData orsmd, orsmd2;
            int counter, reccounter, colcounter;
            String vuserid;
        %>
                
        <%
        
//            vstateacc = request.getParameter("facc");
            // STEP 4: REGISTRATION OF ORACLE DRIVER
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            
            // STEP 5: INSTANTIATING TH             E CONNECTION
            oconn = (OracleConnection) DriverManager.getConnection ("jdbc:oracle:thin:@LOCALHOST:1521:XE","HEALTH_BASKET","DATABASE");
            
            // STEP 6: INSTANTIATING THE STATEMENT OBJECT
            
            ops = (OraclePreparedStatement)oconn.prepareCall("SELECT * FROM TRANSACTIONS ORDER BY TRANSACTION_TIME DESC");
            
            // STEP 7: FILLING UP THE DATABASE RECORDS IN A TEMPORARY CONTAINER
            ors = (OracleResultSet)ops.executeQuery();
            
            
            // STEP 8: GETTING THE COLUMNS INFORMATION(METADATA)
            orsmd = (OracleResultSetMetaData)ors.getMetaData();
            
            %>
            
                    <%
double totalDebit = 0;
double totalCredit = 0;

ops2 = (OraclePreparedStatement) oconn.prepareCall(
    "SELECT NVL(SUM(DEBIT),0) AS TOTAL_DEBIT, NVL(SUM(CREDIT),0) AS TOTAL_CREDIT FROM TRANSACTIONS"
);

ors2 = (OracleResultSet) ops2.executeQuery();

if (ors2.next()) {
    totalDebit = ors2.getDouble("TOTAL_DEBIT");
    totalCredit = ors2.getDouble("TOTAL_CREDIT");
}
%>
            
        <section class="main">
            <div class="main-top">
                <h1>Transactions Tracking</h1>
                <a href="adminPanel.jsp">
                    <i class="fas fa-backward"></i>
                </a>
                
            </div>
            <div class="main-skills" style="flex-direction: column; width: fit-content;">
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
                                
                            </tr>
                <%
                    }
                    %>
                        </tbody>
                        
                    </table>
                    
                    
                    
                    
                    
                </div>
                <div class="cardTable" style="width: fit-content;">
                	<table style="margin-top: 25px;">
    <thead>
        <tr>
            <th><center>TOTAL_DEBIT</center></th>
            <th><center>TOTAL_CREDIT</center></th>
            
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><center><p><%=totalDebit %></p></center></td>
            <td><center><p><%=totalCredit %></p></center></td>
            
        </tr>
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


