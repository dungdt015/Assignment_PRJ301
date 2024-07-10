/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.auth;
import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author admin
 */
public class LoginController extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDBContext db = new UserDBContext();
        User user = null;

        if ("student".equalsIgnoreCase(role)) {
            user = db.getUserByUsernamePassword(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", "student");
                response.sendRedirect("exam/student");
            } else {
                response.getWriter().println("Login failed: invalid username or password for student!");
            }
        } else if ("lecturer".equalsIgnoreCase(role)) {
            user = db.getUserByUsernamePassword(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", "lecturer");
                response.sendRedirect("exam/lecturer");
            } else {
                response.getWriter().println("Login failed: invalid username or password for lecturer!");
            }
        } else {
            response.getWriter().println("Login failed: unknown role!");
        }
    }
    

//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /** 
//     * Handles the HTTP <code>GET</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
//    } 
//
//    /** 
//     * Handles the HTTP <code>POST</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        
//        UserDBContext db = new UserDBContext();
//        User user = db.getUserByUsernamePassword(username, password);
//        if(user !=null)
//        {
//            request.getSession().setAttribute("user", user);
//            response.getWriter().println("login successful: "+ user.getDisplayname());
//        }
//        else
//        {
//            response.getWriter().println("login failed!");
//        }
//        RoleStudentDBContext dBContext = new RoleStudentDBContext();
//        Role role = new Role();
//        if(role != null) {
//            request.getSession().setAttribute("role", role);
//            response.getWriter().println("login successful" + role.getDisplayname());
//        }
//        else{
//            response.getWriter().println("login failed!");
//        }
//    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
