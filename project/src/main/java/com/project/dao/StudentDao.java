package com.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;


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

    public void testAddStudent() {
        String sql = "insert into Degree(studentId, degree, graduationTerm," +
                " graduationYear) values "
                + "(?, ?, ?, ?); ";
        PreparedStatement stmt = null;

    }
}
