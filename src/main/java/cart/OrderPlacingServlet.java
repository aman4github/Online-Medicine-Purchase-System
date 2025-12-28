package cart;

import java.io.IOException;
import java.sql.DriverManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

@WebServlet("/orderPlacingServlet")
public class OrderPlacingServlet extends HttpServlet {

    OracleConnection oconn;
    OraclePreparedStatement ops, ops2, ops3, ops4, ops5, ops6;
    OracleResultSet ors;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Class - OrderPlacingServlet called");
        HttpSession sess = request.getSession(false);
        String customerId = sess.getAttribute("fid").toString();
        String paymentType = sess.getAttribute("payment_type").toString();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            oconn = (OracleConnection) DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "HEALTH_BASKET", "DATABASE");

            String sql = "SELECT s.name as STOCKNAME, s.stock_id as STOCKID, s.price as STOCKPRICE, c.quantity as CARTQUANTITY "
                    +
                    "FROM cart c " +
                    "JOIN stock s ON c.stock_id = s.stock_id " +
                    "WHERE c.customer_id = ? ";

            ops = (OraclePreparedStatement) oconn.prepareStatement(sql);
            ops.setString(1, customerId);
            ors = (OracleResultSet) ops.executeQuery();

            while (ors.next()) {
                Random random = new Random();

                int orderId = random.nextInt(999999 - 111111) + 111111;
                String stock_id = ors.getString("STOCKID");
                String stock_name = ors.getString("STOCKNAME");
                String stock_price = ors.getString("STOCKPRICE");
                String cart_Quantity = ors.getString("CARTQUANTITY");

                String sql2 = "INSERT INTO ORDERS (ORDER_ID, CUSTOMER_ID, STOCK_ID, STOCK_NAME, QUANTITY, TOTAL_PRICE, PAYMENT_TYPE) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                ops2 = (OraclePreparedStatement) oconn.prepareStatement(sql2);
                ops2.setInt(1, orderId);
                ops2.setString(2, customerId);
                ops2.setString(3, stock_id);
                ops2.setString(4, stock_name);
                ops2.setString(5, cart_Quantity);
                int calculatedPrice = Integer.parseInt(stock_price) * Integer.parseInt(cart_Quantity);
                ops2.setInt(6, calculatedPrice);
                ops2.setString(7, paymentType);

                int x = ops2.executeUpdate();

                if (x > 0) {

                    String sql3 = "DELETE FROM CART WHERE CUSTOMER_ID = ? AND STOCK_ID = ?";

                    ops3 = (OraclePreparedStatement) oconn.prepareStatement(sql3);
                    ops3.setString(1, customerId);
                    ops3.setString(2, stock_id);

                    int x1 = ops3.executeUpdate();

                    if (x1 > 0) {
                        String sql4 = "UPDATE STOCK SET quantity = quantity - ? WHERE stock_id = ?";

                        ops4 = (OraclePreparedStatement) oconn.prepareStatement(sql4);
                        ops4.setString(1, cart_Quantity);
                        ops4.setString(2, stock_id);

                        int x2 = ops4.executeUpdate();

                        if (x2 > 0) {
                            ops5 = (OraclePreparedStatement) oconn.prepareCall(
                                    "INSERT INTO STOCK_RECORD (RECORD_ID, STOCK_ID, STOCK_OUT, NOTE) values(?,?,?,?)");

                            Random random1 = new Random();

                            int recordId = random1.nextInt(999999 - 111111) + 111111;

                            ops5.setInt(1, recordId);
                            ops5.setString(2, stock_id);
                            ops5.setString(3, cart_Quantity);
                            ops5.setString(4, "SOLD TO " + customerId);

                            int x3 = ops5.executeUpdate();

                            if (x3 > 0) {

                                String sql5 = "INSERT INTO transactions (transaction_id, order_id, customer_id, stock_id, stock_name, stock_out, credit, payment_type, note) VALUES (?,?,?,?,?,?,?,?,?)";

                                ops6 = (OraclePreparedStatement) oconn.prepareStatement(sql5);

                                Random random2 = new Random();

                                int transactionId = random1.nextInt(999999 - 111111) + 111111;
                                ops6.setInt(1, transactionId);
                                ops6.setInt(2, orderId);
                                ops6.setString(3, customerId);
                                ops6.setString(4, stock_id);
                                ops6.setString(5, stock_name);
                                ops6.setString(6, cart_Quantity);
                                ops6.setInt(7, calculatedPrice);
                                ops6.setString(8, paymentType);
                                ops6.setString(9, "ORDER PLACED");

                                int x4 = ops6.executeUpdate();

                                if (x4 > 0) {
                                    System.out.println("Ordered Successfully...");
                                }
                            }
                        }
                    }

                }

            }

            String msg = "Order Placed Successfully...<br>Please Check Your Orders Section...";
            String pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";

            String title = "Order Placed";
            String navtitle = "Order Placed";
            String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

            request.setAttribute("data", msg);
            request.setAttribute("pathLink", pathLink);
            request.setAttribute("title", title);
            request.setAttribute("navtitle", navtitle);
            request.setAttribute("modalImgPath", modalImgPath);

            RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
