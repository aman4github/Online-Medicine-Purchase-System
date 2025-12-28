

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adding Stock</title>
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
<body style="background-color: #B77466;">
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
        <form action="../../addStock" class="sameBank" method="post">
            <span>Enter Medicine Name:</span>
            <input type="text" name="accName" id="accNum" placeholder="Enter medicine Name" required ">
            <span>Enter Medicine Description:</span>
            <input type="text" name="accDesc" id="accNum" placeholder="Enter medicine Description" required ">
            <span>Enter Medicine Price:</span>
            <input type="number" name="accPrice" id="accNum" placeholder="Enter medicine Price" required ">
            <span>Initial Quantity:</span>
            <input type="number" name="accQty" id="accNum" placeholder="Enter medicine Quantity" value="0" required ">
            <span>Choose Medicine Image:</span>
            <input type="file" name="accImg" style="border: white;">
            
            
            <div class="buttons">
                <button type="submit" id="transferBtn">ADD</button>
                <a href="add_remove_stock.jsp"><button type="button">back</button></a>
            </div>
        </form>
    </div>
    <script>
        

    </script>
</body>
</html>
