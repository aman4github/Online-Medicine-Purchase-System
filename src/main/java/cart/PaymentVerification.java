/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

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
@WebServlet("/paymentVerification")
public class PaymentVerification extends HttpServlet {

    String vto, vfrom, vcc, vbcc, vsubject, vbody, vtotalPrice, vpaymenttype;
    String vNAME, vEMAIL, vGEN, vPASSWORD, vPHONE, vUSERNAME, vADDRESS, vCITY, vPIN, vDISTRICT, vSTATE;
    int userName, userPass;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - PaymentVerification called");
        try (PrintWriter out = response.getWriter()) {

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendMail</title>");
            out.println("</head>");
            out.println("<body>");
            // getting user data from html input
            HttpSession sess = request.getSession(false);
            vEMAIL = sess.getAttribute("femail").toString();
            vto = sess.getAttribute("femail").toString();
            vsubject = "Verify your Payment";
            vbody = "Email verification for Payment.";
            vtotalPrice = request.getParameter("totalPrice").toString();
            vpaymenttype = request.getParameter("action").toString();
            sess.setAttribute("payment_type", vpaymenttype);

            try {

                Random random = new Random();
                int x = 0;
                while (x < 1000) {
                    x = random.nextInt(9999);
                }
                System.out.println("OTP for payment = " + x);
                vbody += "\nYour new OTP for payment of " + vtotalPrice + " is (" + x
                        + ")\nThis OTP is valid for only 30 mins";
                sess.setAttribute("otp", x);
                sess.setAttribute("email", vEMAIL);

                MailSender.sendMail(vto, vsubject, vbody);

                response.sendRedirect("/online-medicine-purchase-system/JSP/paymentOtpVerification.jsp");

            } catch (Exception e) {
                String msg = "Payment Failed .<br>Something error occured.<br>Please Try again later...";
                String pathLink = "/online-medicine-purchase-system/JSP/paymentGateway.jsp";
                String title = "Payment Failed";
                String navtitle = "Payment Failed";
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