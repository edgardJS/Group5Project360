package com.project.Tests;

import com.project.Model.Student;
import com.project.dao.ReportDao;
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
public class ReportDaoTest {
    
    @Autowired
    ReportDao Dao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Test
    public void getSkills() throws Exception {
        List<String> skills = Dao.getSkills();
        Assert.assertTrue("Unable to get skills", !skills.isEmpty());
    }
    
    @Test
    public void getGraduatesByYear() throws Exception {
        List<Student> students = Dao.getGraduatesByYear(2011);
        for (Student s : students) {
            Assert.assertTrue("Student wasn't a graduate of the specified year",
                    s.getDegrees().get(0).getGraduationYear() == 2011);
        }
    }
    
    @Test
    public void getInternshipEmployedCount() throws Exception {
        int count = -1;
        count = Dao.getInternshipEmployedCount();
        Assert.assertTrue("Unable to get internship employed count",count > -1);
    }
    
    @Test
    public void getNoInternshipEmployedCount() throws Exception {
        int count = -1;
        count = Dao.getNoInternshipEmployedCount();
        Assert.assertTrue("Unable to get no internship employed count",count > -1);
    
    }
    
}