/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.util.ArrayList;
import model.Semester;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vanxuan
 */
public class SemesterDBContext extends DBContext<Semester> {

    public ArrayList<Semester> getSemesterBySid(int sid) {
    ArrayList<Semester> semesters = new ArrayList<>();
    PreparedStatement stm = null;
    ResultSet rs = null;
    try {
        String sql = "SELECT DISTINCT s.semid, s.year, s.season,\n"
                + "    CASE s.season\n"
                + "        WHEN 'Spring' THEN 1\n"
                + "        WHEN 'Summer' THEN 2\n"
                + "        WHEN 'Fall' THEN 3\n"
                + "    END AS season_order\n"
                + "FROM semester s\n"
                + "JOIN courses c ON s.semid = c.semid\n"
                + "JOIN students_courses sc ON c.cid = sc.cid\n"
                + "WHERE sc.sid = ? \n"
                + "ORDER BY s.year, season_order;";
        stm = connection.prepareStatement(sql);
        stm.setInt(1, sid);
        rs = stm.executeQuery();
        while (rs.next()) {
            Semester semester = new Semester();
            semester.setSemId(rs.getInt("semid"));
            semester.setSeason(rs.getString("season"));
            semester.setYear(rs.getInt("year"));
            
            semesters.add(semester);
        }
    } catch (SQLException ex) {
        Logger.getLogger(SemesterDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SemesterDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return semesters;
}

    @Override
    public ArrayList<Semester> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Semester get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Semester model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Semester model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Semester model) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    }