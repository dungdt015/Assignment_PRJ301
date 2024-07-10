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
import model.Lecturer;
import model.Student;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDBContext db = new UserDBContext();
        User user = db.getUserByUsernamePassword(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            if ("student".equalsIgnoreCase(role) && user.getStudent() != null) {
                request.getSession().setAttribute("role", "student");
                response.sendRedirect("exam/student");
            } else if ("lecturer".equalsIgnoreCase(role) && user.getLecturer() != null) {
                request.getSession().setAttribute("role", "lecturer");
                response.sendRedirect("exam/lecturer");
            } else {
                request.getSession().removeAttribute("user");
                response.getWriter().println("Login failed: invalid username, password, or role!");
            }
        } else {
            response.getWriter().println("Login failed: invalid username or password!");
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

   
   
}
