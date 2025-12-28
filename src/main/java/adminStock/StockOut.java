package adminStock;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

@WebServlet("/stockOut")
public class StockOut extends HttpServlet {
    String vQTY, vMedID;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OracleResultSet ors = null;
    OraclePreparedStatement ops, ops2, ops3, ops4, ops5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        HttpSession sess = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - StockOut called");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateData</title>");
            out.println("<body>");
            vMedID = request.getParameter("accNum");
            vQTY = request.getParameter("accQty");

            try {
                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                // STEP 4: INSTANTIATING THE ORACLE PREPARED STATEMENT OBJECT
                ops4 = (OraclePreparedStatement) oconn.prepareCall("SELECT * FROM STOCK WHERE STOCK_ID = ?");
                ops4.setString(1, vMedID);
                ors = (OracleResultSet) ops4.executeQuery();
                String stockName = "";
                String stockPrice = "";
                if (ors.next()) {
                    stockName = ors.getString("NAME");
                    stockPrice = ors.getString("PRICE");
                }

                int a = ops4.executeUpdate();

                if (a > 0) {
                    ops2 = (OraclePreparedStatement) oconn
                            .prepareCall("SELECT * FROM STOCK WHERE STOCK_ID = ? AND QUANTITY >= ?");

                    // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)
                    ops2.setString(1, vMedID);
                    ops2.setString(2, vQTY);

                    int y = ops2.executeUpdate();

                    if (y > 0) {
                        ops3 = (OraclePreparedStatement) oconn
                                .prepareCall("UPDATE STOCK SET QUANTITY = QUANTITY - ? WHERE STOCK_ID = ?");

                        ops3.setString(1, vQTY);
                        ops3.setString(2, vMedID);

                        // STEP 7: EXECUTING THE QUERY

                        int z = ops3.executeUpdate();

                        if (z > 0) {

                            ops = (OraclePreparedStatement) oconn.prepareCall(
                                    "INSERT INTO STOCK_RECORD (RECORD_ID, STOCK_ID, STOCK_OUT, NOTE) values(?,?,?,'STOCK OUT')");

                            Random random = new Random();

                            int recordId = random.nextInt(999999 - 111111) + 111111;

                            ops.setInt(1, recordId);
                            ops.setString(2, vMedID);
                            ops.setString(3, vQTY);

                            int x = ops.executeUpdate();

                            if (x > 0) {

                                ops5 = (OraclePreparedStatement) oconn.prepareCall(
                                        "INSERT INTO TRANSACTIONS (transaction_id, STOCK_ID, STOCK_NAME, STOCK_OUT,CREDIT,PAYMENT_TYPE, NOTE) values(?,?,?,?,?,?,?)");

                                Random random1 = new Random();

                                int transactionId = random1.nextInt(999999 - 111111) + 111111;

                                ops5.setInt(1, transactionId);
                                ops5.setString(2, vMedID);
                                ops5.setString(3, stockName);
                                ops5.setString(4, vQTY);
                                int priceTotal = Integer.parseInt(vQTY) * Integer.parseInt(stockPrice);
                                ops5.setInt(5, priceTotal);
                                ops5.setString(6, "COMPANY_PAYMENT");
                                ops5.setString(7, "STOCK OUT");

                                int x2 = ops5.executeUpdate();

                                String msg = "Stock Out Successfully...";
                                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/stockOut.jsp";
                                String title = "Stock Out Successful";
                                String navtitle = "Stock Out Successful";
                                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                                request.setAttribute("data", msg);
                                request.setAttribute("pathLink", pathLink);
                                request.setAttribute("title", title);
                                request.setAttribute("navtitle", navtitle);
                                request.setAttribute("modalImgPath", modalImgPath);

                                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                                rd.forward(request, response);
                            } else {

                                String msg = "Stock Out Successfully...<br>BUT<br>Stock Record Not Updated...";
                                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/stockOut.jsp";
                                String title = "Withdraw Successful";
                                String navtitle = "Withdraw Successful";
                                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/warning.png";

                                request.setAttribute("data", msg);
                                request.setAttribute("pathLink", pathLink);
                                request.setAttribute("title", title);
                                request.setAttribute("navtitle", navtitle);
                                request.setAttribute("modalImgPath", modalImgPath);

                                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                                rd.forward(request, response);
                            }
                        } else {
                            String msg = "Stock Out Failed...";
                            String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/stockOut.jsp";
                            String title = "Stock Out Failed";
                            String navtitle = "Stock Out Failed";
                            String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                            request.setAttribute("data", msg);
                            request.setAttribute("pathLink", pathLink);
                            request.setAttribute("title", title);
                            request.setAttribute("navtitle", navtitle);
                            request.setAttribute("modalImgPath", modalImgPath);

                            RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                            rd.forward(request, response);
                        }
                    } else {

                        String msg = "Insufficient Quantity Available...";
                        String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/stockOut.jsp";
                        String title = "Stock Out Failed";
                        String navtitle = "Stock Out Failed";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/warning.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                        rd.forward(request, response);
                    }
                }

                else {
                    String msg = "Medicine Does not Exists...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/stockOut.jsp";
                    String title = "Stock Out Failed";
                    String navtitle = "Stock Out Failed";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);
                }

                // STEP 8: CLOSING THE ORACLE OBJECTS
                ops.close();

                oconn.close();

            }

            // STEP 9: FORMATTING THE CATCH CLAUSE
            catch (SQLException ex) {
                Logger.getLogger(StockOut.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2 style='color:red'> Error is : " + ex.toString() + "</h2>");
            }

            out.println("</head>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(StockOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(StockOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}