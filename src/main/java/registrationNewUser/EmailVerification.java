/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registrationNewUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilMailSender.MailSender;

/**
 *
 * @author skaman
 */
@WebServlet("/emailVerification")
public class EmailVerification extends HttpServlet {

    String vto, vfrom, vcc, vbcc, vsubject, vbody;
    String vNAME, vEMAIL, vGEN, vPASSWORD, vPHONE, vUSERNAME, vADDRESS, vCITY, vPIN, vDISTRICT, vSTATE;
    int userName, userPass;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - EmailVerification called");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("Class - EmailVerification called");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendMail</title>");
            out.println("</head>");
            out.println("<body>");
            // getting user data from html input
            vNAME = request.getParameter("fname");
            vPHONE = request.getParameter("fphone");
            vGEN = request.getParameter("fgender");
            vEMAIL = request.getParameter("femail");
            vADDRESS = request.getParameter("faddress");
            vCITY = request.getParameter("fcity");
            vPIN = request.getParameter("fpin");
            vDISTRICT = request.getParameter("fdistrict");
            vSTATE = request.getParameter("fstate");

            HttpSession sess = request.getSession(true);
            sess.setAttribute("fname", vNAME);
            sess.setAttribute("femail", vEMAIL);
            sess.setAttribute("fphone", vPHONE);
            sess.setAttribute("fgender", vGEN);
            sess.setAttribute("faddress", vADDRESS);
            sess.setAttribute("fcity", vCITY);
            sess.setAttribute("fpin", vPIN);
            sess.setAttribute("fdistrict", vDISTRICT);
            sess.setAttribute("fstate", vSTATE);

            vto = request.getParameter("femail");
            vsubject = "Verify your Email";
            vbody = "Email verification for Account opening.";

            try {

                Random random = new Random();
                int x = 0;
                while (x < 1000) {
                    x = random.nextInt(9999);
                }
                System.out.println("OTP for new user Registration = " + x);
                vbody += "\nYour new OTP for Email verifiaction is " + x + "\nThis OTP is valid for only 30 mins";
                sess.setAttribute("otp", x);
                sess.setAttribute("email", vEMAIL);

                MailSender.sendMail(vto, vsubject, vbody);

                response.sendRedirect("/online-medicine-purchase-system/JSP/emailOtpVerification.jsp");

            } catch (Exception e) {
                String msg = "Registration Failed .<br>Something error occured.<br>Please Try again later...";
                String pathLink = "/online-medicine-purchase-system/Logout";
                String title = "Registration Failed";
                String navtitle = "Registration Failed";
                String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

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