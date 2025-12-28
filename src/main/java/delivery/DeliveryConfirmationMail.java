package delivery;

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

@WebServlet("/deliveryConfirmationMail")
public class DeliveryConfirmationMail extends HttpServlet {
    OracleConnection oconn;
    OraclePreparedStatement ost, ost2, ost3;
    OracleResultSet ors = null;
    String vemail, vdistrict, vstate, vpass, vpin, vcity, vid, vaddress, vgender, vpassword, vname, vaccNum, vusername,
            vphone;
    String vto, vfrom, vcc, vbcc, vsubject, vbody, vUSERNAME;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Class - DeliveryConfirmationMail called");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        try {
            if (sess != null) {

                vUSERNAME = sess.getAttribute("fnameadmin").toString();
            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error_admin.html");
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SessServe</title>");
            out.println("</head>");
            out.println("<body>");

            String vuserid = request.getParameter("fcusid").toString();
            String vordid = request.getParameter("fordid").toString();
            String vmsg = request.getParameter("fmsg").toString();
            String vloc = request.getParameter("floc").toString();

            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@LOCALHOST:1521:XE",
                    "HEALTH_BASKET", "DATABASE");
            ost2 = (OraclePreparedStatement) oconn
                    .prepareStatement("UPDATE ORDERS SET DELIVERY_STATUS = ? WHERE CUSTOMER_ID = ? AND ORDER_ID = ?");
            ost2.setString(1, vmsg);
            ost2.setString(2, vuserid);
            ost2.setString(3, vordid);

            int x = ost2.executeUpdate();

            if (x > 0) {
                ost = (OraclePreparedStatement) oconn.prepareStatement("SELECT * FROM CUSTOMER WHERE ID = ?");
                ost.setString(1, vuserid);
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

                    // PLS NOTE THAT U CAN IGNORE MANY LINES BELOW IF U R NOT DEALING WITH OTP OR
                    // MAIL SENDING
                    vto = vemail;
                    vsubject = "Delivery Status";
                    vbody = "Notification For Delivery Status";

                    try {

                        if (vmsg.equals("DELIVERED")) {
                            vbody += "\n\nDear, " + vname + "\n\nYour Ordered Medicines are Delivered Successfully...";
                        } else if (vmsg.equals("DECLINED")) {
                            vbody += "\n\nDear, " + vname + "\n\nYour Ordered Medicines Delivery is Declined...";
                        }

                        MailSender.sendMail(vto, vsubject, vbody);

                        String msg = "Delivery Status Updated Successfully...";
                        String pathLink = "";
                        if (vloc.equals("alluserloc")) {
                            pathLink = "/online-medicine-purchase-system/JSP/ADMIN/allDeliveryStatusAdmin.jsp";
                        } else if (vloc.equals("useridloc")) {
                            pathLink = "/online-medicine-purchase-system/JSP/ADMIN/deliveryStatusAdmin.jsp";
                        }
                        String title = "Delivery Successful";
                        String navtitle = "Delivery Successful";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                        rd.forward(request, response);

                    } catch (Exception e) {

                        String msg = "Delivery Status Updated Successfully...";
                        String pathLink = "";
                        if (vloc.equals("alluserloc")) {
                            pathLink = "/online-medicine-purchase-system/JSP/ADMIN/allDeliveryStatusAdmin.jsp";
                        } else if (vloc.equals("useridloc")) {
                            pathLink = "/online-medicine-purchase-system/JSP/ADMIN/deliveryStatusAdmin.jsp";
                        }
                        String title = "Delivery Successful";
                        String navtitle = "Delivery Successful";
                        String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                        request.setAttribute("data", msg);
                        request.setAttribute("pathLink", pathLink);
                        request.setAttribute("title", title);
                        request.setAttribute("navtitle", navtitle);
                        request.setAttribute("modalImgPath", modalImgPath);

                        RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                        rd.forward(request, response);
                    }

                } else {

                    String msg = "Delivery Status Updated Successfully...";
                    String pathLink = "";
                    if (vloc.equals("alluserloc")) {
                        pathLink = "/online-medicine-purchase-system/JSP/ADMIN/allDeliveryStatusAdmin.jsp";
                    } else if (vloc.equals("useridloc")) {
                        pathLink = "/online-medicine-purchase-system/JSP/ADMIN/deliveryStatusAdmin.jsp";
                    }
                    String title = "Delivery Successful";
                    String navtitle = "Delivery Successful";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);
                }
            }
            ost.close();
            oconn.close();
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryConfirmationMail.class.getName()).log(Level.SEVERE, null, ex);
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