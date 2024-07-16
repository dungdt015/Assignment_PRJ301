/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import controller.auth.student.BaseRequiredStudentAuthenticationController;
import dal.CourseDBContext;
import dal.ExamDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Student;
import model.Course;
import model.Exam;
import model.Lecturer;
                                                                                                                 
import model.User;
/**
 *
 * @author admin
 */
public class StudentViewExamController extends BaseRequiredStudentAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User  user, Student student)
    throws ServletException, IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        int sid = student.getId();
        
        ExamDBContext db = new ExamDBContext();
        ArrayList<Exam> exams = db.getExamsByCourse(cid);
        request.setAttribute("exams", exams);
        
        request.getRequestDispatcher("../view/exam/student.jsp").forward(request, response);
        
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        CourseDBContext db = new CourseDBContext();
        int sid = student.getId();
        ArrayList<Course> courses = db.filterByStudentID(sid);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("../view/exam/student.jsp").forward(request, response);
    }
}
    
    
    

    


