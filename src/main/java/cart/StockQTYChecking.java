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
import java.util.List;
import java.util.ArrayList;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

@WebServlet("/stockQTYChecking")
public class StockQTYChecking extends HttpServlet {

    OracleConnection oconn;
    OraclePreparedStatement ops;
    OracleResultSet ors;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Class - StockQTYChecking called");
        HttpSession sess = request.getSession(false);
        String customerId = sess.getAttribute("fid").toString();

        List<String> insufficientItems = new ArrayList<>();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            oconn = (OracleConnection) DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "HEALTH_BASKET", "DATABASE");

            String sql = "SELECT s.name " +
                    "FROM cart c " +
                    "JOIN stock s ON c.stock_id = s.stock_id " +
                    "WHERE c.customer_id = ? " +
                    "AND c.quantity > s.quantity";

            ops = (OraclePreparedStatement) oconn.prepareStatement(sql);
            ops.setString(1, customerId);
            ors = (OracleResultSet) ops.executeQuery();

            while (ors.next()) {
                insufficientItems.add(ors.getString("NAME"));
            }

            if (!insufficientItems.isEmpty()) {
                List<String> msg = insufficientItems;

                String pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";

                String title = "Insufficient Stock";
                String navtitle = "Insufficient Stock";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_qty_cart.jsp");
                rd.forward(request, response);
            } else {

                response.sendRedirect("/online-medicine-purchase-system/JSP/paymentGateway.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
