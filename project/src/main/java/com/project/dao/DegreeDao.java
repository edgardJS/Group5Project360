package com.project.dao;

import com.project.Model.Degree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Brian on 11/30/2016.
 */
@Repository
public class DegreeDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * Adds a degree that is associated to a student by studentId.
     *
     * @param degree the degree to add
     */
    public void addStudentDegree(Degree degree) {
        String sql = "insert into StudentDegree(studentId,  degree, `program`, "
                    + "graduationTerm, graduationYear, gpa) "
                    + "values (?, ?, ?, ?, ?, ?)";
        Object[] parameters = {degree.getStudentId(), degree.getDegreeLevel(),
                                degree.getProgram(), degree.getGraduationTerm(),
                                degree.getGraduationYear(), degree.getGpa()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.INTEGER, Types.DOUBLE};
        jdbcTemplate.update(sql, parameters, types);
    }
    
    /**
     * Gets the all the degrees of a student by id.
     *
     * @param id id of student
     * @return list of degrees
     */
    public List<Degree> getStudentDegrees(int id) {
        String sql = "select * from StudentDegree where studentId = " + id;
        List<Degree> degrees = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row: rows) {
            Degree degree = new Degree();
            degree.setStudentId((Integer) row.get("studentId"));
            degree.setDegreeId((Integer) row.get("degreeId"));
            degree.setGpa((Double) row.get("gpa"));
            degree.setGraduationYear((Integer) row.get("graduationYear"));
            degree.setGraduationTerm((String) row.get("graduationTerm"));
            degree.setProgram((String) row.get("program"));
            degree.setDegreeLevel((String)row.get("degree"));
        }
        return degrees;
    }
    
    /**
     * Gets all degrees.
     * For use in combo box.
     *
     * @return list of degrees
     */
    public List<Degree> getDegrees() {
        String sql = "select * from Degree";
        List<Degree> degrees = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row: rows) {
            Degree degree = new Degree();
            degree.setProgram((String) row.get("program"));
            degree.setDegreeLevel((String)row.get("degree"));
        }
        return degrees;
    }
    
    /**
     * Gets a specific degree by id from a student.
     *
     * @param id degreeId to get
     * @return degree from DB
     */
    public Degree getDegree(int id) {
        String sql = "select * from StudentDegree where degreeId = ?";
        return  (Degree) jdbcTemplate.queryForObject(sql, new Object[]{id}, new DegreeRowMapper());

    }
}

/**
 * Gets data from row of MySQL data and maps it to a Degree.
 */
class DegreeRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Degree degree = new Degree();
        degree.setDegreeLevel(rs.getString("degree"));
        degree.setProgram(rs.getString("program"));
        degree.setGraduationTerm(rs.getString("graduationTerm"));
        degree.setGraduationYear(rs.getInt("graduationYear"));
        degree.setGpa(rs.getDouble("gpa"));
        return degree;
    }
}