package changeCredentials;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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

@WebServlet("/ForgotUsernameCustomerRetrive")
public class ForgotUsernameCustomerRetrive extends HttpServlet {
    OracleConnection oconn;
    OraclePreparedStatement ost;
    OracleResultSet ors = null;
    String vemail, vpass, vname, vphone, vusername;
    String vto, vfrom, vcc, vbcc, vsubject, vbody;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - ForgotUsernameCustomerRetrive called");
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
            ost = (OraclePreparedStatement) oconn.prepareStatement("SELECT * FROM CUSTOMER where EMAIL=?");
            ost.setString(1, vemail);
            ors = (OracleResultSet) ost.executeQuery();
            if (ors.next()) {
                vname = ors.getString("NAME");
                vemail = ors.getString("EMAIL");
                vusername = ors.getString("USERNAME");
                HttpSession sess = request.getSession(true);
                sess.setAttribute("fname", vname);
                sess.setAttribute("femail", vemail);
                sess.setAttribute("fusername", vusername);
                // PLS NOTE THAT U CAN IGNORE MANY LINES BELOW IF U R NOT DEALING WITH OTP OR
                // MAIL SENDING
                vto = vemail;
                vsubject = "Request for Retrival of Username";
                vbody = "Enter the User Name to Login.";

                try {

                    vbody += "\n\nDear, " + vname + "\n\nYour Username is " + vusername
                            + " .\n\nIf the request was not made by You, Contact With Company immediately...";

                    MailSender.sendMail(vto, vsubject, vbody);

                    String msg = "Mail sent Successfully.<br>Check Your mail to get your Username.";
                    String pathLink = "/online-medicine-purchase-system/Logout";
                    String title = "Retrive Username";
                    String navtitle = "Retrive Username Successful";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                    rd.forward(request, response);

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
            Logger.getLogger(ForgotUsernameCustomerRetrive.class.getName()).log(Level.SEVERE, null, ex);
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