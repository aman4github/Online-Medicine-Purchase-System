package cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
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

@WebServlet("/removeFromCart")
public class RemoveFromCart extends HttpServlet {
    String vMAIL, vUSERNAME, vNAME, vCUSTOMERID, vSTOCKID, vDEST;
    int vqty;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OraclePreparedStatement ops, ops2, ops3;
    OracleResultSet ors = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - RemoveFromCart called");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateData</title>");
            out.println("<body>");

            vSTOCKID = request.getParameter("fstockid");
            vCUSTOMERID = request.getParameter("fcustomerid");
            vDEST = request.getParameter("fdest");

            try {
                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                ops2 = (OraclePreparedStatement) oconn
                        .prepareCall("SELECT * FROM CART WHERE STOCK_ID = ? AND CUSTOMER_ID = ?");

                ops2.setString(1, vSTOCKID);
                ops2.setString(2, vCUSTOMERID);
                ors = (OracleResultSet) ops2.executeQuery();

                if (ors.next()) {
                    vqty = ors.getInt("QUANTITY");
                }

                if (vqty > 1) {

                    ops3 = (OraclePreparedStatement) oconn.prepareCall(
                            "UPDATE cart SET quantity = quantity - 1 WHERE stock_id = ? AND customer_id = ?");

                    ops3.setString(1, vSTOCKID);
                    ops3.setString(2, vCUSTOMERID);
                    //

                    // STEP 7: EXECUTING THE QUERY
                    //
                    int x3 = ops3.executeUpdate();

                    // ops2.executeUpdate();

                    if (x3 > 0) {

                        String msg = "Medicine Quantity Decreased from Cart Successfully...";
                        String pathLink = "";
                        if (vDEST.equals("cart")) {
                            pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";
                        } else if (vDEST.equals("shop")) {
                            pathLink = "/online-medicine-purchase-system/JSP/shopCustomer.jsp";
                        }

                        String title = "Medicine Removed";
                        String navtitle = "Medicine Removed Successfully";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                        rd.forward(request, response);

                    } else {
                        String msg = "Medicine Quantity Decreased from Cart Failed...";
                        String pathLink = "";
                        if (vDEST.equals("cart")) {
                            pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";
                        } else if (vDEST.equals("shop")) {
                            pathLink = "/online-medicine-purchase-system/JSP/shopCustomer.jsp";
                        }
                        String title = "Medicine Decrement Failed";
                        String navtitle = "Medicine Decrement Failed";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                        rd.forward(request, response);
                    }
                } else if (vqty == 1) {
                    ops3 = (OraclePreparedStatement) oconn
                            .prepareCall("DELETE FROM CART WHERE stock_id = ? AND customer_id = ?");

                    ops3.setString(1, vSTOCKID);
                    ops3.setString(2, vCUSTOMERID);
                    //

                    // STEP 7: EXECUTING THE QUERY
                    //
                    int x3 = ops3.executeUpdate();

                    if (x3 > 0) {

                        String msg = "Medicine Removed from Cart Successfully...";
                        String pathLink = "";
                        if (vDEST.equals("cart")) {
                            pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";
                        } else if (vDEST.equals("shop")) {
                            pathLink = "/online-medicine-purchase-system/JSP/shopCustomer.jsp";
                        }

                        String title = "Medicine Removed";
                        String navtitle = "Medicine Removed Successfully";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                        rd.forward(request, response);

                    } else {
                        String msg = "Medicine Quantity Decreased from Cart Failed...";
                        String pathLink = "";
                        if (vDEST.equals("cart")) {
                            pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";
                        } else if (vDEST.equals("shop")) {
                            pathLink = "/online-medicine-purchase-system/JSP/shopCustomer.jsp";
                        }
                        String title = "Medicine Decrement Failed";
                        String navtitle = "Medicine Decrement Failed";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                        rd.forward(request, response);
                    }
                }

                if (ops != null)
                    ops.close();
                // STEP 8: CLOSING THE ORACLE OBJECTS

                ops2.close();
                if (ops3 != null)
                    ops3.close();
                oconn.close();

            }

            // STEP 9: FORMATTING THE CATCH CLAUSE
            catch (SQLException ex) {
                // Logger.getLogger(RemoveStock.class.getName()).log(Level.SEVERE, null, ex);
                // out.println("<h2 style='color:red'> Error is : " + ex.toString() + "</h2>");
                String msg = "Medicine Quantity Decreased from Cart Failed...";
                String pathLink = "";
                if (vDEST.equals("cart")) {
                    pathLink = "/online-medicine-purchase-system/JSP/cartCustomer.jsp";
                } else if (vDEST.equals("shop")) {
                    pathLink = "/online-medicine-purchase-system/JSP/shopCustomer.jsp";
                }
                String title = "Medicine Addition Failed";
                String navtitle = "Medicine Addition Failed";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                rd.forward(request, response);
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
            Logger.getLogger(RemoveFromCart.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RemoveFromCart.class.getName()).log(Level.SEVERE, null, ex);
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