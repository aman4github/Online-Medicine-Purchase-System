

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock In</title>
    <link rel="stylesheet" href="../../CSS/availability_check.css">
    <link rel="shortcut icon" href="../../IMAGE/Logo/favicon.png" type="image/x-icon">
    <style>
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
        }
    </style>
</head>
<body style="background-color: #9CC6DB;">
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
        <form action="../../stockOut" class="sameBank" method="post">
            <span>Enter Medicine Id:</span>
            <input type="number" name="accNum" id="accNum" placeholder="Enter medicine id" required oninput="validate()">
            <span>Enter the Quantity:</span>
            <input type="number" name="accQty" id="accQty" value="0" placeholder="Enter quantity" required>
            
            <div class="buttons">
                <button type="submit" disabled id="transferBtn">REMOVE</button>
                <a href="stock_management.jsp"><button type="button">back</button></a>
            </div>
        </form>
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
