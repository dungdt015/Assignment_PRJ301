/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Student;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;

/**
 *
 * @author admin
 */
public class StudentDBContext extends DBContext<Student>{
    public ArrayList<Student> getStudentsByCourse(int cid) {
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT s.sid,s.sname FROM students s INNER JOIN students_courses sc ON s.sid = sc.sid\n"
                    + "						INNER JOIN courses c ON c.cid = sc.cid\n"
                    + "						WHERE c.cid = ?";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                students.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

   public ArrayList<Course> getStudentCourses(int studentId) {
        ArrayList<Course> courses = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT c.cid, c.cname FROM courses c "
                       + "INNER JOIN students_courses sc ON c.cid = sc.cid "
                       + "WHERE sc.sid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, studentId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("cid"));
                course.setName(rs.getString("cname"));
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) stm.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return courses;
    }


    @Override
    public ArrayList<Student> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
