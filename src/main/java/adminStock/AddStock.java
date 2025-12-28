package adminStock;

import java.util.Random;
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

@WebServlet("/addStock")
public class AddStock extends HttpServlet {
    String vNAME, vDESC, vUSERNAME, vPRICE, vQTY, vIMG;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OraclePreparedStatement ops, ops2, ops3;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - AddStock called");
        try {
            if (sess != null) {

                vUSERNAME = sess.getAttribute("fnameadmin").toString();
            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error_admin.html");
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<body>");
            vNAME = request.getParameter("accName").toUpperCase();
            vDESC = request.getParameter("accDesc");
            vPRICE = request.getParameter("accPrice");
            vQTY = request.getParameter("accQty");
            vIMG = request.getParameter("accImg");
            try {
                Random random = new Random();

                int mediId = random.nextInt(999999 - 111111) + 111111;
                int recordId = random.nextInt(999999 - 111111) + 111111;
                int transactionId = random.nextInt(999999 - 111111) + 111111;

                System.out.println(mediId);
                System.out.println(recordId);

                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                // STEP 4: INSTANTIATING THE ORACLE PREPARED STATEMENT OBJECT
                ops = (OraclePreparedStatement) oconn.prepareCall(
                        "INSERT INTO STOCK(STOCK_ID, NAME, DESCRIPTION, PRICE, QUANTITY, IMAGE_URL) values(?,?,?,?,?,?)");
                ops2 = (OraclePreparedStatement) oconn
                        .prepareCall("INSERT INTO STOCK_RECORD(RECORD_ID, STOCK_ID, STOCK_IN, NOTE) values(?,?,?,?)");
                ops3 = (OraclePreparedStatement) oconn.prepareCall(
                        "INSERT INTO TRANSACTIONS (transaction_id, STOCK_ID, STOCK_NAME, STOCK_IN,DEBIT,PAYMENT_TYPE, NOTE) values(?,?,?,?,?,?,?)");
                ops3.setInt(1, transactionId);
                ops3.setInt(2, mediId);
                ops3.setString(3, vNAME);
                ops3.setString(4, vQTY);
                int priceTotal = Integer.parseInt(vQTY) * Integer.parseInt(vPRICE);
                ops3.setInt(5, priceTotal);
                ops3.setString(6, "COMPANY_PAYMENT");
                ops3.setString(7, "STOCK ADDED");

                // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)
                ops.setInt(1, mediId);
                ops.setString(2, vNAME);
                ops.setString(3, vDESC);
                ops.setString(4, vPRICE);
                ops.setString(5, vQTY);
                ops.setString(6, vIMG);

                ops2.setInt(1, recordId);
                ops2.setInt(2, mediId);
                ops2.setString(3, vQTY);
                ops2.setString(4, "STOCK ADDED");

                System.out.println("Insertion Started....");
                // STEP 7: EXECUTING THE QUERY
                int x = ops.executeUpdate();
                int x1 = ops2.executeUpdate();
                int x2 = ops3.executeUpdate();

                System.out.println("Insertion Ended....");

                if (x > 0) {
                    String msg = "Medicine Added Successfully...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/add_remove_stock.jsp";
                    String title = "Medicine Added Successfull";
                    String navtitle = "Medicine Added Successfully";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);

                } else {
                    String msg = "Medicine Addition Failed or Medicine already exists...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/add_remove_stock.jsp";
                    String title = "Medicine Addition Failed";
                    String navtitle = "Medicine Addition Failed";
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
                Logger.getLogger(AddStock.class.getName()).log(Level.SEVERE, null, ex);
                String msg = "Medicine Addition Failed or Medicine already exists...";
                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/add_remove_stock.jsp";
                String title = "Medicine Addition Failed";
                String navtitle = "Medicine Addition Failed";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
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
            Logger.getLogger(AddStock.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddStock.class.getName()).log(Level.SEVERE, null, ex);
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