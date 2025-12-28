package adminStock;

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

@WebServlet("/removeStock")
public class RemoveStock extends HttpServlet {
    String vMAIL, vUSERNAME, vNAME;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OraclePreparedStatement ops, ops2, ops3;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - RemoveStock called");
        try {
            if (sess != null) {

                vUSERNAME = sess.getAttribute("fnameadmin").toString();
            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error_admin.html");
        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateData</title>");
            out.println("<body>");

            vNAME = request.getParameter("fname");

            try {
                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                // STEP 4: INSTANTIATING THE ORACLE PREPARED STATEMENT OBJECT
                ops = (OraclePreparedStatement) oconn.prepareCall("DELETE FROM STOCK WHERE STOCK_ID = ?");
                ops2 = (OraclePreparedStatement) oconn.prepareCall("DELETE FROM STOCK_RECORD WHERE STOCK_ID = ?");
                ops3 = (OraclePreparedStatement) oconn.prepareCall("DELETE FROM CART WHERE STOCK_ID = ?");

                // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)

                ops.setString(1, vNAME);
                ops2.setString(1, vNAME);
                ops3.setString(1, vNAME);
                // ops2.setString(1, vACC);

                // STEP 7: EXECUTING THE QUERY
                int x1 = ops2.executeUpdate();
                int x2 = ops3.executeUpdate();
                int x = ops.executeUpdate();

                if (x > 0) {

                    String msg = "Medicine Removed Successfully...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/removeStock.jsp";
                    String title = "Medicine Remove";
                    String navtitle = "Medicine Removed Successfully";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);

                } else {
                    String msg = "Medicine Removal Failed...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/removeStock.jsp";
                    String title = "Medicine Remove";
                    String navtitle = "Medicine Removal Failed";
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
                String msg = "Medicine Removal Failed...";
                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/removeStock.jsp";
                String title = "Medicine Remove";
                String navtitle = "Medicine Removal Failed";
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
            Logger.getLogger(RemoveStock.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RemoveStock.class.getName()).log(Level.SEVERE, null, ex);
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