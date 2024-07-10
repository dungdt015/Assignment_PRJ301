/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.exam;

import controller.auth.BaseRequiredLecturerAuthenticationController;

import dal.CourseDBContext;
import dal.ExamDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Course;
import model.Exam;
import model.Lecturer;
import model.User;
/**
 *
 * @author admin
 */
public class LecturerViewExamController extends BaseRequiredLecturerAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
       CourseDBContext db = new CourseDBContext();
        int lid = lecturer.getId();
        ArrayList<Course> courses = db.getCoursesByLecturer(lid);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("../view/exam/lecturer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
       int cid = Integer.parseInt(request.getParameter("cid"));
        int lid = lecturer.getId();
        
        ExamDBContext db = new ExamDBContext();
        ArrayList<Exam> exams = db.getExamsByCourse(cid);
        request.setAttribute("exams", exams);
        
        request.getRequestDispatcher("../view/exam/lecturer.jsp").forward(request, response);
        
        
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
//    protected void doGet(HttpServletRequest request, HttpServletResponse response )
//    throws ServletException, IOException {
//         CourseDBContext db = new CourseDBContext();
//        int lid = Integer.parseInt(request.getParameter("lid"));
//        ArrayList<Course> courses = db.filterByLecturerID(lid);
//        request.setAttribute("courses", courses);
//        request.getRequestDispatcher("../view/exam/lecturer.jsp").forward(request, response);
//        
//    } 
//
//    /** 
//     * Handles the HTTP <code>POST</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response, Lecturer lecturer)
//    throws ServletException, IOException {
//    int cid = Integer.parseInt(request.getParameter("cid"));
//    int lid = lecturer.getId();
//        AssessmentDBContext db = new AssessmentDBContext();
//        ArrayList<Exam> exams = db.getRelatedExams(cid);
//        request.setAttribute("exams", exams);
//        request.getRequestDispatcher("../view/exam/lecturer.jsp").forward(request, response);
//        
//    }
//
//    /** 
//     * Returns a short description of the servlet.
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

}
