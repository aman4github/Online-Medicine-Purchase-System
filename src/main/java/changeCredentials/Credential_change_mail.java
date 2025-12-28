/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package changeCredentials;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilMailSender.MailSender;

import javax.servlet.annotation.WebServlet;

/**
 *
 * @author skaman
 */
@WebServlet("/credential_change_mail")
public class Credential_change_mail extends HttpServlet {

    String vto, vfrom, vcc, vbcc, vsubject, vbody, vUSERNAME, vEMAIL, vMSG, vNAME;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - Credential_change_mail called");
        try {
            if (sess != null) {
                vEMAIL = sess.getAttribute("femail").toString();
                vNAME = sess.getAttribute("fname").toString();

            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error.html");

        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendMail</title>");
            out.println("</head>");
            out.println("<body>");
            // getting user data from html input

            vMSG = sess.getAttribute("vmsg").toString();
            vto = sess.getAttribute("email").toString();
            vsubject = "Credential Change Alert";

            try {

                if (vMSG.equals("PASSWORD")) {
                    vbody = "\nDear, " + vNAME
                            + "\nYou have successfully changed you password.\n\nIf it's not done by You, Contact with Company immediately...";
                } else if (vMSG.equals("USERNAME")) {
                    vbody = "\nDear, " + vNAME
                            + "\nYou have successfully changed you username.\n\nIf it's not done by You, Contact with Company immediately...";
                } else if (vMSG.equals("ADDRESS")) {
                    vbody = "\nDear, " + vNAME
                            + "\nYou have successfully changed you address.\n\nIf it's not done by You, Contact with Company immediately...";
                }

                MailSender.sendMail(vto, vsubject, vbody);

                if (vMSG.equals("PASSWORD")) {
                    String msg = "Password changed Successfully...<br>Please Login again.";
                    String pathLink = "/online-medicine-purchase-system/Logout";
                    String title = "Password Change";
                    String navtitle = "Password Change";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                    rd.forward(request, response);
                } else if (vMSG.equals("USERNAME")) {
                    String msg = "Username changed Successfully...<br>Please Login again.";
                    String pathLink = "/online-medicine-purchase-system/Logout";
                    String title = "Username Change";
                    String navtitle = "Username Change";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                    rd.forward(request, response);
                } else if (vMSG.equals("ADDRESS")) {
                    String msg = "Address changed Successfully...<br>Please Login again.";
                    String pathLink = "/online-medicine-purchase-system/Logout";
                    String title = "Address Change";
                    String navtitle = "Address Change";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                    rd.forward(request, response);
                }

            } catch (Exception e) {

                String msg = "Credential Change Successful.<br>Due to system error we are unable to mail your Updated Credential.<br>Kindly contact with Bank for further Details.";
                String pathLink = "/online-medicine-purchase-system/Logout";
                String title = "Credential Change";
                String navtitle = "Credential Change Successful";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/warning.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                rd.forward(request, response);

            } finally {
                out.println("</body>");
                out.println("</html>");
            }
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