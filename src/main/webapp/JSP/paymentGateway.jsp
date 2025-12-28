<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../IMAGE/Logo/favicon.png" type="image/x-icon">
    <title>Payment Gateway</title>
    <link rel="stylesheet" href="../CSS/paymentGateway.css">
</head>

<body>

<%!
        String vname, vusername, vphone, vemail, vpassword, vaddress,vtotalPrice, vcity,vid, vgender, vpin, vdistrict, vstate;
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
                vtotalPrice = sess.getAttribute("totalPrice").toString();
            }
        } catch (Exception ex) {
    %>
    <script>
        location.href = "../HTML/ALERT/session_error.html";
    </script>
    <%
        }
    %>

    <div class="container">
        <form action="../paymentVerification" method="post">
            <div class="row">
                <div class="column">
                    <h3 class="title">Billing Address</h3>
                    <div class="input-box">
                        <span>Full Name :</span>
                        <input type="text" placeholder="Sk Aman" value="<%= vname %>" disabled>
                    </div>
                    <div class="input-box">
                        <span>Email :</span>
                        <input type="email" placeholder="example@example.com" name="pemail" value="<%= vemail %>" disabled>
                    </div>
                    <div class="input-box">
                        <span>Address :</span>
                        <input type="text" placeholder="Room - Street - Locality" value="<%= vaddress %>" disabled>
                    </div>
                    <div class="input-box">
                        <span>City :</span>
                        <input type="text" placeholder="Kolkata" value="<%= vcity %>" disabled>
                    </div>

                    <div class="flex">
                        <div class="input-box">
                            <span>State :</span>
                            <input type="text" placeholder="West Bengal" value="<%= vstate %>" disabled>
                        </div>
                        <div class="input-box">
                            <span>Pin Code :</span>
                            <input type="number" placeholder="123 456" value="<%= vpin %>" disabled>
                        </div>
                    </div>
                </div>

                <div class="column">
                    <h3 class="title">Payment</h3>
                    <div class="input-box">
                        <span>Cards Accepted :</span>
                        <img src="../IMAGE/Payment/imgcards.png" alt="">
                    </div>
                    <div class="input-box">
                        <span>Name On Card :</span>
                        <input type="text" placeholder="Mr. SK AMAN">
                    </div>
                    <div class="input-box">
                        <span>Credit Card Number :</span>
                        <input type="number" placeholder="1111 2222 3333 4444">
                    </div>
                    <div class="input-box">
                        <span>Exp. Month :</span>
                        <input type="text" placeholder="August">
                    </div>
                
                    <div class="flex">
                        <div class="input-box">
                            <span>Exp. Year :</span>
                            <input type="number" placeholder="2025">
                        </div>
                        <div class="input-box">
                            <span>CVV :</span>
                            <input type="number" placeholder="123">
                        </div>
                    </div>
                </div>
            </div>
			<input type="text" name="totalPrice" value="<%= vtotalPrice %>" style="display: none;">
            <button type="submit" class="btn" name="action" value="card_payment">Pay Using Card - <%= vtotalPrice %></button>
            <button type="submit" class="btn" name="action" value="cod" style="background: #F39EB6;">Cash On Delivery - <%= vtotalPrice %></button>
            <button type="button" onclick="location.href='cartCustomer.jsp'" class="btn back-btn">
    Back to Cart
</button>
            
        </form>
        
    </div>

</body>

</html>