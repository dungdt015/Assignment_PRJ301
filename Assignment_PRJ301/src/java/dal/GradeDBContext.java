/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assessment;
import model.Exam;
import model.Grade;
import model.Student;
import model.Subject;

/**
 *
 * @author admin
 */
public class GradeDBContext extends DBContext<Grade> {

    public ArrayList<Grade> getGradesFromExamIds(ArrayList<Integer> eids) {
    ArrayList<Grade> grades = new ArrayList<>();
    String sql = "SELECT eid, sid, score FROM grades WHERE (1 > 2)";
    for (Integer eid : eids) {
        sql += " OR eid = ? ";
    }
    PreparedStatement stm = null;
    try {
        stm = connection.prepareStatement(sql);
        for (int i = 0; i < eids.size(); i++) {
            stm.setInt((i + 1), eids.get(i));
        }

        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Exam exam = new Exam();
            exam.setId(rs.getInt("eid"));

            Student s = new Student();
            s.setId(rs.getInt("sid"));

            Grade g = new Grade();
            g.setExam(exam);
            g.setStudent(s);
            g.setScore(rs.getFloat("score"));

            grades.add(g);
        }
    } catch (SQLException ex) {
        Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return grades;
}

    public void saveGradesByCourse(int cid, ArrayList<Grade> grades) {
        ArrayList<PreparedStatement> stm_inserts = new ArrayList<>();
        try {
            connection.setAutoCommit(false);

            String sql_insert_grades = "MERGE INTO grades AS target "
                    + "USING (SELECT ? AS eid, ? AS sid, ? AS score) AS source "
                    + "ON target.eid = source.eid AND target.sid = source.sid "
                    + "WHEN MATCHED THEN "
                    + "UPDATE SET target.score = source.score "
                    + "WHEN NOT MATCHED THEN "
                    + "INSERT (eid, sid, score) "
                    + "VALUES (source.eid, source.sid, source.score);";

            for (Grade grade : grades) {
                PreparedStatement stm_insert = connection.prepareStatement(sql_insert_grades);
                stm_insert.setInt(1, grade.getExam().getId());
                stm_insert.setInt(2, grade.getStudent().getId());
                stm_insert.setFloat(3, grade.getScore());
                stm_insert.executeUpdate();
                stm_inserts.add(stm_insert);
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                for (PreparedStatement stm_insert : stm_inserts) {
                    stm_insert.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Grade> getGradesByStudentIdAndCourse(int studentId, int courseId) {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT g.eid, g.sid, g.score, e.[from], e.duration, a.aid, a.aname, a.weight, s.subid, s.subname "
                + "FROM grades g "
                + "INNER JOIN exams e ON g.eid = e.eid "
                + "INNER JOIN assessments a ON e.aid = a.aid "
                + "INNER JOIN subjects s ON a.subid = s.subid "
                + "INNER JOIN courses c ON s.subid = c.subid "
                + "WHERE g.sid = ? AND c.cid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, studentId);
            stm.setInt(2, courseId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setScore(rs.getFloat("score"));

                Exam exam = new Exam();
                exam.setId(rs.getInt("eid"));
                exam.setFrom(rs.getTimestamp("from"));
                exam.setDuration(rs.getInt("duration"));

                Assessment assessment = new Assessment();
                assessment.setId(rs.getInt("aid"));
                assessment.setName(rs.getString("aname"));
                assessment.setWeight(rs.getFloat("weight"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subid"));
                subject.setName(rs.getString("subname"));

                assessment.setSubject(subject);
                exam.setAssessment(assessment);

                grade.setExam(exam);

                Student student = new Student();
                student.setId(studentId);
                grade.setStudent(student);

                grades.add(grade);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grades;
    }

    public float calculateFinalScore(int studentId, int courseId) {
        float finalScore = 0.0f;
        String sql = "SELECT g.score, a.weight "
                + "FROM grades g "
                + "INNER JOIN exams e ON g.eid = e.eid "
                + "INNER JOIN assessments a ON e.aid = a.aid "
                + "INNER JOIN subjects s ON a.subid = s.subid "
                + "WHERE g.sid = ? AND s.subid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, studentId);
            stm.setInt(2, courseId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                float score = rs.getFloat("score");
                float weight = rs.getFloat("weight");
                finalScore += score * weight;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalScore;
    }

    public void insertGradesForCourse(int cid, ArrayList<Grade> grades) {
        String sql_remove = "DELETE grades WHERE sid IN (SELECT sid FROM students_courses WHERE cid = ?)";
        String sql_insert = "INSERT INTO [grades]\n"
                + "           ([eid]\n"
                + "           ,[sid]\n"
                + "           ,[score])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        
        PreparedStatement stm_remove =null;
        ArrayList<PreparedStatement> stm_inserts = new ArrayList<>();
        
        try {
            connection.setAutoCommit(false);
            stm_remove = connection.prepareStatement(sql_remove);
            stm_remove.setInt(1, cid);
            stm_remove.executeUpdate();
            
            for (Grade grade : grades) {
                PreparedStatement stm_insert = connection.prepareStatement(sql_insert);
                stm_insert.setInt(1, grade.getExam().getId());
                stm_insert.setInt(2,grade.getStudent().getId());
                stm_insert.setFloat(3, grade.getScore());
                stm_insert.executeUpdate();
                stm_inserts.add(stm_insert);
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            try {
                connection.setAutoCommit(true);
                stm_remove.close();
                for (PreparedStatement stm_insert : stm_inserts) {
                    stm_insert.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    @Override
    public ArrayList<Grade> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Grade get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    public ArrayList<Grade> getGradesFromExamIds(ArrayList<Integer> eids) {
//        ArrayList<Grade> grades = new ArrayList<>();
//        PreparedStatement stm = null;
//        try {
//            String sql = "SELECT eid,sid,score FROM grades WHERE (1>2)";
//            for (Integer eid : eids) {
//                sql += " OR eid = ?";
//            }
//
//            stm = connection.prepareStatement(sql);
//
//            for (int i = 0; i < eids.size(); i++) {
//                stm.setInt((i + 1), eids.get(i));
//            }
//
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Grade g = new Grade();
//                g.setScore(rs.getFloat("score"));
//
//                Student s = new Student();
//                s.setId(rs.getInt("sid"));
//                g.setStudent(s);
//
//                Exam e = new Exam();
//                e.setId(rs.getInt("eid"));
//                g.setExam(e);
//
//                grades.add(g);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                stm.close();
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return grades;
//    }
//
//   public void insertGradesForCourse(int cid, ArrayList<Grade> grades) {
//        String sql_remove = "DELETE grades WHERE sid IN (SELECT sid FROM students_courses WHERE cid = ?)";
//        String sql_insert = "INSERT INTO [grades]\n"
//                + "           ([eid]\n"
//                + "           ,[sid]\n"
//                + "           ,[score])\n"
//                + "     VALUES\n"
//                + "           (?\n"
//                + "           ,?\n"
//                + "           ,?)";
//        
//        PreparedStatement stm_remove =null;
//        ArrayList<PreparedStatement> stm_inserts = new ArrayList<>();
//        
//        try {
//            connection.setAutoCommit(false);
//            stm_remove = connection.prepareStatement(sql_remove);
//            stm_remove.setInt(1, cid);
//            stm_remove.executeUpdate();
//            
//            for (Grade grade : grades) {
//                PreparedStatement stm_insert = connection.prepareStatement(sql_insert);
//                stm_insert.setInt(1, grade.getExam().getId());
//                stm_insert.setInt(2,grade.getStudent().getId());
//                stm_insert.setFloat(3, grade.getScore());
//                stm_insert.executeUpdate();
//                stm_inserts.add(stm_insert);
//            }
//            connection.commit();
//        } catch (SQLException ex) {
//            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            try {
//                connection.rollback();
//            } catch (SQLException ex1) {
//                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        }
//        finally
//        {
//            try {
//                connection.setAutoCommit(true);
//                stm_remove.close();
//                for (PreparedStatement stm_insert : stm_inserts) {
//                    stm_insert.close();
//                }
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
}
