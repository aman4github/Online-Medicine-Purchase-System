package adminStock;

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
import oracle.jdbc.OracleResultSet;

@WebServlet("/availabilityCheck")
public class AvailabilityCheck extends HttpServlet {
    String vname, vqty, vstockId;
    String vMediId;

    // STEP 1: DECLARING ORACLE OBJECTS
    OracleConnection oconn;
    OracleResultSet ors = null;
    OraclePreparedStatement ops, ops2, ops3, ops4, ops5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        HttpSession sess = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Class - AvailabilityCheck called");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateData</title>");
            out.println("<body>");

            vMediId = request.getParameter("accNum");

            try {
                // STEP 2: REGISTERING THE ORACLE DRIVER WITH THIS SERVLEt
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

                // STEP 3: INSTANTIATING THE ORACLE CONNECTION OBJECT
                oconn = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "HEALTH_BASKET", "DATABASE");

                ops3 = (OraclePreparedStatement) oconn.prepareCall("SELECT * FROM STOCK WHERE STOCK_ID = ?");

                // STEP 6: FILLING UP THE BLANK QUERY PARAMETERS (?)

                ops3.setString(1, vMediId);
                ors = (OracleResultSet) ops3.executeQuery();

                if (ors.next()) {
                    vname = ors.getString("NAME");
                    vstockId = ors.getString("STOCK_ID");
                    vqty = ors.getString("QUANTITY");

                    String msg = "Stock Fetched Successfully...<br>Name - " + vname + "<br>Stock ID - " + vstockId
                            + "<br>Quantity - " + vqty;
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/availability_check.jsp";
                    String title = "Stock Fetched Successfull";
                    String navtitle = "Stock Fetched Successfully";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/checked.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);

                } else {

                    String msg = "Stock Fetching Failed...<br>OR<br>Stock Does not exist...";
                    String pathLink = "/online-medicine-purchase-system/JSP/ADMIN/availability_check.jsp";
                    String title = "Stock Fetching Failed";
                    String navtitle = "Stock Fetching Failed";
                    String modalImgPath = "/online-medicine-purchase-system/IMAGE/Modal/cross.png";

                    request.setAttribute("data", msg);
                    request.setAttribute("pathLink", pathLink);
                    request.setAttribute("title", title);
                    request.setAttribute("navtitle", navtitle);
                    request.setAttribute("modalImgPath", modalImgPath);

                    RequestDispatcher rd = request.getRequestDispatcher("/JSP/ALERT/alert_all_admin.jsp");
                    rd.forward(request, response);
                }

                // STEP 7: EXECUTING THE QUERY

                // STEP 8: CLOSING THE ORACLE OBJECTS
                ops3.close();

                ors.close();

                oconn.close();

            }

            // STEP 9: FORMATTING THE CATCH CLAUSE
            catch (SQLException ex) {
                Logger.getLogger(AvailabilityCheck.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<h2 style='color:red'> Error is : " + ex.toString() + "</h2>");
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
            Logger.getLogger(AvailabilityCheck.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AvailabilityCheck.class.getName()).log(Level.SEVERE, null, ex);
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