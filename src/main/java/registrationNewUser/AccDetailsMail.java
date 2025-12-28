/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registrationNewUser;

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
@WebServlet("/accDetailsMail")
public class AccDetailsMail extends HttpServlet {

    String vto, vfrom, vcc, vbcc, vsubject, vbody, vNAME, vUSERID, vEMAIL, vPASS;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Class - AccDetailMail called");
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        try {
            if (sess != null) {
                vNAME = sess.getAttribute("fname").toString();
                vUSERID = sess.getAttribute("userId").toString();
                vEMAIL = sess.getAttribute("femail").toString();
                vPASS = sess.getAttribute("userPass").toString();
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

            vto = sess.getAttribute("email").toString();
            vsubject = "Account Number";
            vbody = "Welcome";

            try {

                vbody += "\nDear, " + vNAME + "\n\nYour User ID = " + vUSERID + " .\nYour Temporary Password = " + vPASS
                        + "\nKindly Change your temporary Username and password after your first login.\n\nThank You.";

                MailSender.sendMail(vto, vsubject, vbody);
                //
                String msg = "Registration Successful.<br>Check Your mail to get your Account Credentials.";
                String pathLink = "/online-medicine-purchase-system/Logout";
                String title = "Registration Successful";
                String navtitle = "Registration Successful";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_customer.jsp");
                rd.forward(request, response);

            } catch (Exception e) {

                String msg = "Registration Successful.<br>Due to system error we are unable to mail your Account Details.<br>Kindly contact with Company for further Details.";
                String pathLink = "/online-medicine-purchase-system/Logout";
                String title = "Registration Successful";
                String navtitle = "Registration Successful";
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