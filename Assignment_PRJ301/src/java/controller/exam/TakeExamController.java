/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.exam;

import dal.ExamDBContext;
import dal.GradeDBContext;
import dal.StudentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import model.Exam;
import model.Grade;
import model.Student;
/*
 *
 * @author admin
 */
public class TakeExamController extends HttpServlet {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         StudentDBContext stuDB = new StudentDBContext();
        ExamDBContext examDB = new ExamDBContext();
        GradeDBContext gradeDB = new GradeDBContext();
        String[] raw_eids = request.getParameterValues("eid");
        if (raw_eids.length == 0) {
            response.getWriter().println("you must select at least one exam");
        } else {
            int cid = Integer.parseInt(request.getParameter("cid"));
            ArrayList<Student> students = stuDB.getStudentsByCourse(cid);
            int[] eids = new int[raw_eids.length];
            for (int i = 0; i < raw_eids.length; i++) {
                eids[i] = Integer.parseInt(raw_eids[i]);
            }
            ArrayList<Exam> exams = examDB.getExamsByEids(eids);
            ArrayList<Grade> grades = gradeDB.getGradesByEids(eids);

            request.setAttribute("students", students);
            request.setAttribute("exams", exams);
            request.setAttribute("grades", grades);

            request.getRequestDispatcher("../view/exam/take.jsp").forward(request, response);
        }

    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        String[] raw_gradeids = request.getParameterValues("gradeid");
        HashSet<String> eids = new HashSet<>();
        ArrayList<Grade> grades = new ArrayList<>();
        for (String raw_gradeid : raw_gradeids) {
            int sid = Integer.parseInt(raw_gradeid.split("_")[0]);
            int eid = Integer.parseInt(raw_gradeid.split("_")[1]);
            eids.add(eid + "");//redirect later
            String raw_score = request.getParameter("grade" + sid + "_" + eid);
            if (raw_score.length() > 0) {
                Grade g = new Grade();
                Student s = new Student();
                s.setId(sid);
                Exam e = new Exam();
                e.setId(eid);
                g.setExam(e);
                g.setStudent(s);
                g.setScore(Float.parseFloat(raw_score));
                grades.add(g);
            }
        }

        GradeDBContext db = new GradeDBContext();
        db.saveGradesByCourse(cid, grades);
        String url_eid = "";
        for (String eid : eids) {
            url_eid += "&eid=" + eid;
        }

        response.sendRedirect("take?cid=" + cid + url_eid);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
