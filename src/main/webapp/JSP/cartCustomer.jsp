<%@page import="java.sql.DriverManager"%>
<%@page import="oracle.jdbc.OraclePreparedStatement"%>
<%@page import="oracle.jdbc.OracleResultSetMetaData"%>
<%@page import="oracle.jdbc.OracleResultSet"%>
<%@page import="oracle.jdbc.OracleConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    String shortText(String s) {
        if (s == null) return "";
        int limit = 15; // show first 15 chars
        if (s.length() <= limit) return s;
        return s.substring(0, limit) + "...";
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <title>Your Cart</title>
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <link rel="stylesheet" href="../CSS/shopCustomer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

    
</head>

<body style="background-color: #ABE0F0">

    <%!
        String vname, vusername, vphone, vemail, vpassword, vaddress, vcity,vid, vgender, vpin, vdistrict, vstate;
    %>
    <%
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        try {
            if (sess != null) {
                vname = sess.getAttribute("fname").toString();
                vusername = sess.getAttribute("fusername").toString();
                vphone = sess.getAttribute("fphone").toString();
                vemail = sess.getAttribute("femail").toString();
                vpassword = sess.getAttribute("fpassword").toString();
                vaddress = sess.getAttribute("faddress").toString();
                vcity = sess.getAttribute("fcity").toString();
                vgender = sess.getAttribute("fgender").toString();
                vpin = sess.getAttribute("fpin").toString();
                vdistrict = sess.getAttribute("fdistrict").toString();
                vstate = sess.getAttribute("fstate").toString();
                vid = sess.getAttribute("fid").toString();
            }
        } catch (Exception ex) {
    %>
    <script>
        location.href = "../HTML/ALERT/session_error.html";
    </script>
    <%
        }
    %>

    <%!
        OracleConnection oconn;
        OraclePreparedStatement ops, ops2;
        OracleResultSet ors, ors2;
        OracleResultSetMetaData orsmd;
        int counter, reccounter, colcounter;
        String vstateacc, vstockId, vqty;
    %>
    <%
        // DB connect
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        oconn = (OracleConnection) DriverManager.getConnection(
                "jdbc:oracle:thin:@LOCALHOST:1521:XE", "HEALTH_BASKET", "DATABASE");

        // Query        
        
        
        ops = (OraclePreparedStatement) oconn.prepareCall("SELECT s.STOCK_ID, s.NAME, s.DESCRIPTION, s.PRICE, s.IMAGE_URL, s.QUANTITY AS stock_qty, c.QUANTITY AS cart_qty FROM STOCK s JOIN CART c ON s.STOCK_ID = c.STOCK_ID WHERE c.CUSTOMER_ID = ? AND s.QUANTITY >= 1 ORDER BY c.CREATED_AT DESC");
        ops.setString(1, vid);
        ops.executeUpdate();
        ors = (OracleResultSet) ops.executeQuery();
        orsmd = (OracleResultSetMetaData) ors.getMetaData();
    %>

    <section class="main">
        <div class="main-top">
            <h1>Your Cart</h1>
            <a href="user-dashboard.jsp">
                <i class="fas fa-backward"></i>
            </a>
        </div>

        <div class="main-skills">
            <div class="cardTable">

                <div class="product-grid">
                    <%
                    int allTotal = 0;
                    boolean hasItems = false;
                        while (ors.next() == true) {

                            // 🔁 CHANGE column names here if needed
                            String stock_Id = null;
                            String name = null;
                            String desc = null;
                            String price = null;
                            String imageUrl = null;
                            String quantity = null;
                            int totalPrice = 0;
                            hasItems = true;

                            try { stock_Id = ors.getString("STOCK_ID"); } catch (Exception e) {}
                            try { name = ors.getString("NAME"); } catch (Exception e) {}
                            try { desc = ors.getString("DESCRIPTION"); } catch (Exception e) {}
                            try { price = ors.getString("PRICE"); } catch (Exception e) {}
                            try { imageUrl = ors.getString("IMAGE_URL"); } catch (Exception e) {}
                            try { quantity = ors.getString("cart_qty"); } catch (Exception e) {}
                            
                            totalPrice = Integer.parseInt(price) * Integer.parseInt(quantity);
                            
                            allTotal += totalPrice;
                            sess.setAttribute("totalPrice", allTotal);
                            
                         // after: imageUrl = ors.getString("IMAGE_URL");
                            String imgRelPath;

                            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                                // if DB has only the file name (e.g. 'main_logo.png'),
                                // prepend your images folder
                                if (imageUrl.contains("/")) {
                                    // already has a folder in DB
                                    imgRelPath = imageUrl;                       // e.g. "IMAGE/MediShop/main_logo.png"
                                } else {
                                    imgRelPath = "IMAGE/MediShop/" + imageUrl;   // <-- CHANGE folder if needed
                                }
                            } else {
                                // fallback image
                                imgRelPath = "IMAGE/MediShop/c.jpg";
                            }

                            // final URL for the browser
                            String imgPath = request.getContextPath() + "/" + imgRelPath;


                            if (stock_Id == null) stock_Id = "";
                            if (name == null) name = "";
                            if (desc == null) desc = "";
                            if (price == null) price = "0";

                            // safe name for JS (remove single quotes)
                            String safeName = name.replace("'", "");
                    %>

                    <div class="product-card">
                       <img src="<%= imgPath %>" alt="<%= name %>" class="product-image" />


                        <div class="product-content">
                            <h2 class="product-name"><%= shortText(name) %></h2>

                            <p class="product-desc">
                                Quantity - <%= quantity %><br>
                                Price/Unit - <%= price %>
                            </p>

                            <div class="product-footer">
                                <span class="product-price">₹ <%= totalPrice %></span>

                                <!--  <button type="button"
                                        class="add-to-cart-btn"
                                        onclick="addToCart('<%= stock_Id %>', '<%= safeName %>', '<%= price %>');">
                                    Add to Cart
                                </button>-->
                                <div style="display: flex">
                                <form action="../addToCart" method="post" style="margin-right: 15px">
                                        <input type="text" name="fstockid" value="<%=ors.getString("STOCK_ID")%>" style="display: none;">
                                        <input type="text" name="fcustomerid" value="<%=vid%>" style="display: none;">
                                        <input type="text" name="fdest" value="cart" style="display: none;">
                                        <button type="submit" class="actionkyc" style="background: green;">
                                        <i class="fas fa-plus"></i>
                                        </button>
                                        
                                    </form>
                                    <form action="../removeFromCart" method="post">
                                        <input type="text" name="fstockid" value="<%=ors.getString("STOCK_ID")%>" style="display: none;">
                                        <input type="text" name="fcustomerid" value="<%=vid%>" style="display: none;">
                                        <input type="text" name="fdest" value="cart" style="display: none;">
                                        <button type="submit" class="actionkyc" style="background: red">
                                        <i class="fas fa-minus"></i>
                                        </button>
                                    </form>
                                    </div>
                            </div>
                        </div>
                    </div>

                    <%
                        } // end while
                    %>
                </div>
                <div class="cartCheckOut" style="display: flex; justify-content: center; margin-bottom: 10px;">
                		<button class="actionkyc" disabled style="width: 20%; background: green;"><b>Total Price - ₹ <%= allTotal %></b></button>
                	</div>
                <form action="../stockQTYChecking" method="post">
    <div class="cartCheckOut" style="display: flex; justify-content: center;">

        <button class="actionkyc"
                style="width: 20%;"
                <%= hasItems ? "" : "disabled" %>>
            <b>Proceed To Checkout</b>
        </button>

    </div>
</form>


                <%
                    // Close DB resources
                    ors.close();
                    ops.close();
                    oconn.close();
                %>

            </div>
        </div>
    </section>
	
	
	
    <script>
        function addToCart(id, name, price) {
            alert(name + " (₹" + price + ") added to cart!");
            // later you can send this to a servlet:
            // location.href = '../AddToCart?medId=' + encodeURIComponent(id);
        }
    </script>

</body>

</html>
