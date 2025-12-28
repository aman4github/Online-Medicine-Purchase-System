package changeCredentials;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import utilMailSender.MailSender;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ForgotPassCustomerRetrive")
public class ForgotPassCustomerRetrive extends HttpServlet {
    OracleConnection oconn;
    OraclePreparedStatement ost;
    OracleResultSet ors = null;
    String vemail, vpass, vname, vphone, vusername;
    String vto, vfrom, vcc, vbcc, vsubject, vbody;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - ForgotPassCustomerRetrive called");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SessServe</title>");
            out.println("</head>");
            out.println("<body>");

            vemail = request.getParameter("txtusername");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:XE",
                    "HEALTH_BASKET", "DATABASE");
            ost = (OraclePreparedStatement) oconn
                    .prepareStatement("SELECT * FROM CUSTOMER WHERE USERNAME=? OR EMAIL=?");
            ost.setString(1, vemail);
            ost.setString(2, vemail);
            ors = (OracleResultSet) ost.executeQuery();
            if (ors.next()) {
                vname = ors.getString("NAME");
                vemail = ors.getString("EMAIL");
                vusername = ors.getString("USERNAME");
                HttpSession sess = request.getSession(true);
                String vmsg = "PASSWORD";
                sess.setAttribute("fname", vname);
                sess.setAttribute("femail", vemail);
                sess.setAttribute("fusername", vusername);
                sess.setAttribute("vmsg", vmsg);

                // PLS NOTE THAT U CAN IGNORE MANY LINES BELOW IF U R NOT DEALING WITH OTP OR
                // MAIL SENDING
                vto = vemail;
                vsubject = "New OTP for Changing Password !!!";
                vbody = "Enter the OTP to change Password.";

                try {

                    Random random = new Random();
                    int x = 0;
                    while (x < 1000) {
                        x = random.nextInt(9999);
                    }
                    System.out.println("OTP for forgot password = " + x);
                    vbody += "\nYour new OTP for Password reset is " + x
                            + "\nThis OTP us valid for only 30 mins\nPlease do not share this OTP with anyone...";
                    sess.setAttribute("otp", x);
                    sess.setAttribute("email", vemail);

                    MailSender.sendMail(vto, vsubject, vbody);

                    response.sendRedirect("/online-medicine-purchase-system/JSP/verifyOTPforgotPassCustomer.jsp");

                } catch (Exception e) {

                    out.println("<h2 style='color:red'>" + e.getMessage() + "</h2>");
                }

            } else {
                response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/WrongInfoRetrival.html");

            }
            ost.close();
            oconn.close();
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPassCustomerRetrive.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
        processRequest(request, response);
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