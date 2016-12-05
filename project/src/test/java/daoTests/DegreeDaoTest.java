package com.project.Tests;

import com.project.Model.Degree;
import com.project.dao.DegreeDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by Brian on 12/1/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DegreeDaoTest {

//    @Autowired
//    private DegreeDao Dao;

    @Autowired
    DegreeDao degreeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Degree degree;

    @Before
    public void setUp() throws Exception {
        degree = new Degree();
        degree.setStudentId(12345);
        degree.setDegreeLevel("degree");
        degree.setProgram("program");
        degree.setGraduationTerm("Fall");
        degree.setGraduationYear(2011);
        degree.setGpa(4.0);
    }
    
    @Test
    public void addStudentDegree() throws Exception {
        try {
            jdbcTemplate = new JdbcTemplate();
            degreeDao.addStudentDegree(degree);
        } catch (Exception e) {
            Assert.fail("Failure to add to StudentDegree table with exception " + e);
        }
    }
    
    @Test
    public void getStudentDegrees() throws Exception {
        Assert.assertNotNull("StudentDegrees returned null",
                        degreeDao.getStudentDegrees(12345));
    }
    
    @Test
    public void getDegrees() throws Exception {
        Assert.assertNotNull("Degrees returned null",
                        degreeDao.getDegrees());
    }
    
    @Test
    public void getStudentDegree() throws Exception {
        Assert.assertSame(degree, degreeDao.getStudentDegree(degree.getDegreeId(), 12345));
    }
    
}