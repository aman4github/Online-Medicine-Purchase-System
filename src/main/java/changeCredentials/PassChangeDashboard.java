package changeCredentials;

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

@WebServlet("/PassChangeDashboard")
public class PassChangeDashboard extends HttpServlet {
    String vPass, vUsername, vaccNum, vaccType, vEMAIL, vNAME;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OraclePreparedStatement ops;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sess = request.getSession(false);
        System.out.println("Class - PassChangeDashboard called");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateData</title>");
            out.println("<body>");

            vPass = request.getParameter("fpass");
            vUsername = request.getParameter("fUsername");
            vEMAIL = request.getParameter("femail");
            vNAME = request.getParameter("fname");

            try {
                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                // STEP 4: INSTANTIATING THE ORACLE PREPARED STATEMENT OBJECT
                ops = (OraclePreparedStatement) oconn
                        .prepareCall("UPDATE CUSTOMER SET PASSWORD = (?) WHERE EMAIL = (?)");

                // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)

                ops.setString(1, vPass);
                ops.setString(2, vEMAIL);

                // STEP 7: EXECUTING THE QUERY
                int x = ops.executeUpdate();

                if (x > 0) {

                    String vmsg2 = "PASSWORD";
                    sess.setAttribute("vmsg", vmsg2);
                    sess.setAttribute("femail", vEMAIL);
                    sess.setAttribute("fname", vNAME);

                    RequestDispatcher rd = request.getRequestDispatcher("/credential_change_mail");
                    rd.forward(request, response);
                } else {

                    String msg = "Password Change Failed...<br>Please Try again after some time...";
                    String pathLink = "/online-medicine-purchase-system/JSP/dashboard_profile_password.jsp";
                    String title = "Password Change";
                    String navtitle = "Password Change Failure";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                    rd.forward(request, response);
                }

                // STEP 8: CLOSING THE ORACLE OBJECTS
                ops.close();
                oconn.close();

            }

            // STEP 9: FORMATTING THE CATCH CLAUSE
            catch (SQLException ex) {
                Logger.getLogger(PassChangeDashboard.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PassChangeDashboard.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PassChangeDashboard.class.getName()).log(Level.SEVERE, null, ex);
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