package com.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Brian Jorgenson
 */
@Repository
public class TransferCollegeDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Gets the name of the transfer college by student id.
     *
     * @param id student id
     * @return transfer college
     */
    public List<String> getTransferCollegeById(int id) {
        String sql = "select collegeName from StudentTransferCollege where studentId = " + id;
        return (List<String>) jdbcTemplate.queryForList(sql, String.class);
    }
    
    /**
     * Gets all transfer colleges.
     *
     * @return list of transfer colleges
     */
    public List<String> getTransferColleges() {
        String sql = "select * from TransferCollege";
        return (List<String>) jdbcTemplate.queryForList(sql, String.class);
    }
}
