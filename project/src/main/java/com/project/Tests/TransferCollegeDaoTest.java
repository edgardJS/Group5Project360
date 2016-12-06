package com.project.Tests;

import com.project.dao.TransferCollegeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Brian Jorgenson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferCollegeDaoTest {
    
    @Autowired
    TransferCollegeDao Dao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void getTransferCollegesById() throws Exception {
        List<String> colleges = Dao.getTransferCollegeById(2);
        Assert.assertTrue("", colleges.get(0).equals("UWT"));
    }
    
    @Test
    public void getTransferColleges() throws Exception {
        List<String> colleges = Dao.getTransferColleges();
        Assert.assertTrue(colleges.size() > 0);
    }
    
}