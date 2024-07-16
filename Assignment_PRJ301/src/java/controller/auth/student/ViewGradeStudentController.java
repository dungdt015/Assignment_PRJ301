/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.auth.student;

import dal.ExamDBContext;
import dal.GradeDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Exam;
import model.Grade;
import model.Student;
import model.User;

/**
 *
 * @author admin
 */
 public class ViewGradeStudentController extends BaseRequiredStudentAuthenticationController {
  
    /**
     *
     * @param request
     * @param response
     * @param user
     * @param student
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int studentId = (int) session.getAttribute("sid");
        int courseId = Integer.parseInt(request.getParameter("cid"));

        GradeDBContext gradeDBContext = new GradeDBContext();
        ArrayList<Grade> grades = gradeDBContext.getGradesByStudentIdAndCourse(studentId, courseId);

        ExamDBContext examDBContext = new ExamDBContext();
        ArrayList<Exam> exams = examDBContext.getExamsByCourse(courseId);

        request.setAttribute("grades", grades);
        request.setAttribute("exams", exams);
        request.getRequestDispatcher("..view/student/grades.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int studentId = (int) session.getAttribute("sid");
        int courseId = Integer.parseInt(request.getParameter("cid"));

        GradeDBContext gradeDBContext = new GradeDBContext();
        ArrayList<Grade> grades = gradeDBContext.getGradesByStudentIdAndCourse(studentId, courseId);

        ExamDBContext examDBContext = new ExamDBContext();
        ArrayList<Exam> exams = examDBContext.getExamsByCourse(courseId);

        request.setAttribute("grades", grades);
        request.setAttribute("exams", exams);
        request.getRequestDispatcher("..view/student/grades.jsp").forward(request, response);
    }

}
