package com.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


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
}
