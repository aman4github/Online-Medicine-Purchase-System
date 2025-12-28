/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminClosure;

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
@WebServlet("/closure_mail")
public class Closure_mail extends HttpServlet {

    String vto, vfrom, vcc, vbcc, vsubject, vbody, vUSERNAME, vEMAIL, vNAME;

    // int userName, userPass;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - Closure_mail called");
        try {
            if (sess != null) {

                vUSERNAME = sess.getAttribute("fnameadmin").toString();
            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error_admin.html");
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
            vNAME = sess.getAttribute("vNAME").toString();
            vto = sess.getAttribute("vMAIL").toString();
            vsubject = "Account Closed";
            vbody = "We're Sorry to see you go...";

            try {

                vbody += "\n\nDear, " + vNAME
                        + "\n\nYour Health Basket Account is Permanently Closed now...\nYou can re-open again later...";
                MailSender.sendMail(vto, vsubject, vbody);

                String msg = "Account Closed Successfully...";
                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/accountClosure.jsp";
                String title = "Account Clouser";
                String navtitle = "Account Clouser Successful";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                String msg = "Account Closed Successfully...";
                String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/accountClosure.jsp";
                String title = "Account Clouser";
                String navtitle = "Account Clouser Successful";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                request.setAttribute("data", msg);
                request.setAttribute("pathLink", pathLink);
                request.setAttribute("title", title);
                request.setAttribute("navtitle", navtitle);
                request.setAttribute("modalImgPath", modalImgPath);

                RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
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