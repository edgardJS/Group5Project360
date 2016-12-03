package com.project.dao;

import com.project.Model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets data from row of MySQL data and maps it to a Student.
 *
 * @author Brian Jorgenson
 */
public class StudentRowMapper implements RowMapper{
    
    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Student student = new Student();
        student.setFirstName(rs.getString("firstName"));
        student.setLastName(rs.getString("lastName"));
        student.setUwEmail(rs.getString("uwEmail"));
        student.setEmail(rs.getString("externalEmail"));
        student.setId(rs.getInt("studentId"));
        return student;
    }
}
