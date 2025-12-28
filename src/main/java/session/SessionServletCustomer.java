package session;

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

@WebServlet("/SessionServletCustomer")
public class SessionServletCustomer extends HttpServlet {
    OracleConnection oconn;
    OraclePreparedStatement ost;
    OracleResultSet ors = null;
    String vemail, vdistrict, vstate, vpass, vpin, vcity, vid, vaddress, vgender, vpassword, vname, vaccNum, vusername,
            vphone;
    String vto, vfrom, vcc, vbcc, vsubject, vbody;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Class - SessionServletCustomer called");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SessServe</title>");
            out.println("</head>");
            out.println("<body>");

            vemail = request.getParameter("txtusername");
            vpass = request.getParameter("txtpassword");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:XE",
                    "HEALTH_BASKET", "DATABASE");
            ost = (OraclePreparedStatement) oconn
                    .prepareStatement("SELECT * FROM CUSTOMER WHERE (USERNAME=? OR EMAIL=?) AND PASSWORD=?");
            ost.setString(1, vemail);
            ost.setString(2, vemail);
            ost.setString(3, vpass);
            ors = (OracleResultSet) ost.executeQuery();
            if (ors.next()) {
                vname = ors.getString("NAME");
                vusername = ors.getString("USERNAME");
                vphone = ors.getString("PHONE");
                vemail = ors.getString("EMAIL");
                vpassword = ors.getString("PASSWORD");
                vgender = ors.getString("GENDER");
                vaddress = ors.getString("ADDRESS");
                vcity = ors.getString("CITY");
                vpin = ors.getString("PIN");
                vdistrict = ors.getString("DISTRICT");
                vstate = ors.getString("STATE");
                vid = ors.getString("ID");

                HttpSession sess = request.getSession(true);
                sess.setAttribute("fname", vname);
                sess.setAttribute("fusername", vusername);
                sess.setAttribute("fphone", vphone);
                sess.setAttribute("femail", vemail);
                sess.setAttribute("fpassword", vpassword);
                sess.setAttribute("fgender", vgender);
                sess.setAttribute("faddress", vaddress);
                sess.setAttribute("fcity", vcity);
                sess.setAttribute("fpin", vpin);
                sess.setAttribute("fdistrict", vdistrict);
                sess.setAttribute("fstate", vstate);
                sess.setAttribute("fid", vid);
                // PLS NOTE THAT U CAN IGNORE MANY LINES BELOW IF U R NOT DEALING WITH OTP OR
                // MAIL SENDING
                vto = vemail;
                vsubject = "New OTP for Logging in !!!";
                vbody = "Enter the OTP for signing in.";

                try {

                    Random random = new Random();
                    int x = 0;
                    while (x < 1000) {
                        x = random.nextInt(9999);
                    }
                    System.out.println("OTP for customer mail verification = " + x);
                    vbody += "\n\nDear, " + vname + "\n\nYour new OTP for Login is " + x
                            + "\nThis OTP is valid for only 30 mins";
                    sess.setAttribute("otp", x);
                    sess.setAttribute("email", vemail);

                    MailSender.sendMail(vto, vsubject, vbody);

                    response.sendRedirect("/online-medicine-purchase-system/JSP/verifyOTPCustomer.jsp");

                } catch (Exception e) {

                    out.println("<h2 style='color:red'>" + e.getMessage() + "</h2>");
                }

            } else {
                response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/WrongInfoCustomer.html");

            }
            ost.close();
            oconn.close();
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(SessionServletCustomer.class.getName()).log(Level.SEVERE, null, ex);
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