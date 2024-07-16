/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth.student;

import dal.ExamDBContext;
import dal.GradeDBContext;
import dal.StudentDBContext; 
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import model.Exam;
import model.Grade;
import model.Student;
import model.User;

/**
 *
 * @author admin
 */
public class TakeExamStudentController extends BaseRequiredStudentAuthenticationController {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
       int studentId = Integer.parseInt(request.getParameter("sid"));
        int courseId = Integer.parseInt(request.getParameter("cid"));

        GradeDBContext gradeDBContext = new GradeDBContext();
        ArrayList<Grade> grades = gradeDBContext.getGradesByStudentIdAndCourse(studentId, courseId);

        ExamDBContext examDBContext = new ExamDBContext();
        ArrayList<Exam> exams = examDBContext.getExamsByCourse(courseId);

        request.setAttribute("grades", grades);
        request.setAttribute("exams", exams);
        request.getRequestDispatcher("..view/student/take.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        HashSet<Integer> eids = new HashSet<>();

        String[] raw_gradeids = request.getParameterValues("gradeid");
        ArrayList<Grade> grades = new ArrayList<>();
        for (String raw_gradeid : raw_gradeids) {
            int sid = Integer.parseInt(raw_gradeid.split("_")[0]);
            int eid = Integer.parseInt(raw_gradeid.split("_")[1]);

            eids.add(eid);

            String raw_score = request.getParameter("score" + sid + "_" + eid);
            if (raw_score != null && !raw_score.isEmpty()) {
                Grade g = new Grade();
                Exam e = new Exam();
                e.setId(eid);

                Student s = new Student();
                s.setId(sid);

                g.setExam(e);
                g.setStudent(s);
                g.setScore(Float.parseFloat(raw_score));

                grades.add(g);
            }
        }

        GradeDBContext db = new GradeDBContext();
        db.insertGradesForCourse(courseId, grades);

        String url_param = "";
        for (Integer eid : eids) {
            url_param += "&eid=" + eid;
        }
        response.sendRedirect("TakeExamStudentController?courseId=" + courseId + url_param);
    }
     @Override
    public String getServletInfo() {
        return "Short description";
    }
}
