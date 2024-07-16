/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lecturer;
import model.Student;
/**
 *
 * @author admin
 */
public class UserDBContext extends DBContext<User>{
    public User getUserByUsernamePassword(String username, String password) {
        User user = null;
        PreparedStatement stm = null;
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setDisplayname(rs.getString("displayname"));

                // Kiểm tra xem user có phải là lecturer hay student
                if (isLecturer(username)) {
                    Lecturer lecturer = getLecturerByUsername(username);
                    user.setLecturer(lecturer);
                } else if (isStudent(username)) {
                    Student student = getStudentByUsername(username);
                    user.setStudent(student);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    private boolean isLecturer(String username) {
        boolean isLecturer = false;
        try {
            String sql = "SELECT * FROM users_lecturers WHERE username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                isLecturer = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isLecturer;
    }

    private boolean isStudent(String username) {
        boolean isStudent = false;
        try {
            String sql = "SELECT * FROM users_students WHERE username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                isStudent = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isStudent;
    }

    private Lecturer getLecturerByUsername(String username) {
        Lecturer lecturer = null;
        try {
            String sql = "SELECT l.* FROM lecturers l INNER JOIN users_lecturers ul ON l.lid = ul.lid WHERE ul.username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                lecturer = new Lecturer();
                lecturer.setId(rs.getInt("lid"));
                lecturer.setName(rs.getString("lname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lecturer;
    }

    private Student getStudentByUsername(String username) {
        Student student = null;
        try {
            String sql = "SELECT s.* FROM students s INNER JOIN users_students us ON s.sid = us.sid WHERE us.username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("sid"));
                student.setName(rs.getString("sname"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return student;
    }


    @Override
    public ArrayList<User> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
