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
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDBContext db = new UserDBContext();
        User user = db.getUserByUsernamePassword(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            if (user.getLecturer() != null) {
                response.sendRedirect("exam/lecturer");
            }
            else if(user.getStudent() != null){ 
              response.sendRedirect("exam/student");
                
            }
            else{
                response.getWriter().println("login failed! ");
            }
    }
    }
     @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}