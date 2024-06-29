/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Assessment;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Exam;
import model.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
/**
 *
 * @author admin
 */
public class AssessmentDBContext extends DBContext<Assessment>{
        
    public ArrayList<Exam> getRelatedExams(int cid) {
        PreparedStatement stm = null;
        ArrayList<Exam> exams = new ArrayList<>();
        
        try {
            String sql = "SELECT a.aid,a.aname,a.weight,sub.subid,sub.subname\n"
                    + "	,e.eid,e.[from],e.duration\n"
                    + "FROM\n"
                    + "exams e INNER JOIN assesments a ON e.aid = a.aid\n"
                    + "		INNER JOIN subjects sub ON sub.subid = a.subid\n"
                    + "		INNER JOIN courses c ON c.subid = sub.subid\n"
                    + "WHERE c.cid = ?";
            
            stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next())
            {
                Assessment a = new Assessment();
                a.setId(rs.getInt("aid"));
                a.setName(rs.getString("aname"));
                a.setWeight(rs.getFloat("weight"));
                
                Subject sub= new Subject();
                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                a.setSubject(sub);
                
                Exam exam = new Exam();
                exam.setId(rs.getInt("eid"));
                exam.setDate(rs.getTimestamp("from"));
                exam.setDuration(rs.getInt("duration"));
                exam.setAssessment(a);
                
                exams.add(exam);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AssessmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssessmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return exams;
    }

    @Override
    public ArrayList<Assessment> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Assessment get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Assessment model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Assessment model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Assessment model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
