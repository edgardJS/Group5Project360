package com.project.dao;

import com.project.Model.Degree;
import com.project.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Types;


/**
 * Created by edgards on 11/27/16.
 */
@Repository
public class StudentDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void testGetItems() {
        int rowCount = this.jdbcTemplate.queryForObject("select count(*) from Item", Integer.class);
        System.out.println(rowCount);
    }

    public void testAddDegree() {
        String sql = "insert into `Degree`(`degree`, graduationTerm," +
                " graduationYear, program) values "
                + "(?, ?, ?, ?)";
        Object[] parameters = {"degree", "term", 2010, "program"};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        jdbcTemplate.update(sql, parameters, types);
    }

    public void testAddStudent() {
        String sql = "insert into Student(studentId, lastName, firstName, uwEmail, externalEmail, " +
                "transferColleges, gpa) values "
                + "(?, ?, ?, ?, ?, ?, ?)";
        Object[] parameters = {12345, "lstname", "frstname", "@email", "@external", "transfers", 4};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.INTEGER};
        jdbcTemplate.update(sql, parameters, types);
    }

    public void testGetStudent() {

    }
}
