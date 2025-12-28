package registrationNewUser;

import java.util.Random;
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

@WebServlet("/registrationNewAccount")
public class RegistrationNewAccount extends HttpServlet {
    String vNAME, vEMAIL, vGEN, vPASSWORD, vPHONE, vNOMINEE, vACCOUNT_TYPE, vADDRESS, vCITY, vPIN, vDISTRICT, vSTATE;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OraclePreparedStatement ops;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        HttpSession sess = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        System.out.println("Class - RegistrationNewAccount called");
        try {
            if (sess != null) {
                vNAME = sess.getAttribute("fname").toString();
                vEMAIL = sess.getAttribute("femail").toString();
                vPHONE = sess.getAttribute("fphone").toString();
                vGEN = sess.getAttribute("fgender").toString();
                vADDRESS = sess.getAttribute("faddress").toString();
                vCITY = sess.getAttribute("fcity").toString();
                vPIN = sess.getAttribute("fpin").toString();
                vDISTRICT = sess.getAttribute("fdistrict").toString();
                vSTATE = sess.getAttribute("fstate").toString();
            }
        } catch (Exception ex) {
            response.sendRedirect("/online-medicine-purchase-system/HTML/ALERT/session_error.html");

        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<body>");
            try {
                Random random = new Random();

                int userId = random.nextInt(999999 - 111111) + 111111;
                int userIdMain = random.nextInt(999999 - 111111) + 111111;
                int userPass = random.nextInt(999999 - 111111) + 111111;

                System.out.println(userId);
                System.out.println(userPass);

                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                // STEP 4: INSTANTIATING THE ORACLE PREPARED STATEMENT OBJECT
                ops = (OraclePreparedStatement) oconn.prepareCall(
                        "INSERT INTO CUSTOMER(USERNAME,NAME,PHONE,EMAIL,PASSWORD,GENDER,ADDRESS,CITY,PIN,DISTRICT,STATE,ID) values(?,?,?,?,?,?,?,?,?,?,?,?)");

                // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)
                ops.setInt(1, userId);
                ops.setString(2, vNAME);
                ops.setString(3, vPHONE);
                ops.setString(4, vEMAIL);
                ops.setInt(5, userPass);
                ops.setString(6, vGEN);
                ops.setString(7, vADDRESS);
                ops.setString(8, vCITY);
                ops.setString(9, vPIN);
                ops.setString(10, vDISTRICT);
                ops.setString(11, vSTATE);
                ops.setInt(12, userIdMain);

                System.out.println("Insertion Started....");
                // STEP 7: EXECUTING THE QUERY
                int x = ops.executeUpdate();

                System.out.println("Insertion Ended....");

                if (x > 0) {
                    sess.setAttribute("userId", userId);
                    sess.setAttribute("userPass", userPass);
                    sess.setAttribute("userIdMain", userIdMain);

                    RequestDispatcher rd = request.getRequestDispatcher("/accDetailsMail");
                    rd.forward(request, response);

                } else {
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

                }

                // STEP 8: CLOSING THE ORACLE OBJECTS
                ops.close();
                oconn.close();

            }

            // STEP 9: FORMATTING THE CATCH CLAUSE
            catch (SQLException ex) {
                Logger.getLogger(RegistrationNewAccount.class.getName()).log(Level.SEVERE, null, ex);
                String msg = "Registration Failed .<br>You already have an Account.";
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
            Logger.getLogger(RegistrationNewAccount.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegistrationNewAccount.class.getName()).log(Level.SEVERE, null, ex);
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