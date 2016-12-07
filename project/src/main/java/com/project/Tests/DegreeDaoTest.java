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

import java.util.List;


/**
 * @author Brian Jorgenson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DegreeDaoTest {

    @Autowired
    DegreeDao degreeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Degree degree;

    @Before
    public void setUp() throws Exception {
        degree = new Degree();
        degree.setDegreeId(5);
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
            degreeDao.addStudentDegree(degree);
        } catch (Exception e) {
            Assert.fail("Failure to add to StudentDegree table with exception " + e);
        }
    }

    @Test
    public void getStudentDegrees() throws Exception {
        List<Degree> d = degreeDao.getStudentDegrees(2);
        Assert.assertNotNull("StudentDegrees returned null", d);
    }

    @Test
    public void getDegrees() throws Exception {
        Assert.assertNotNull("Degrees returned null",
                degreeDao.getDegrees());
    }

    @Test
    public void getStudentDegree() throws Exception {
        Degree d = degreeDao.getStudentDegree(degree.getDegreeId(), 12345);
        Assert.assertSame("Degree ids not same",
                degree.getDegreeId(), d.getDegreeId());
        Assert.assertTrue("Degree student ids not same",
                degree.getStudentId() == d.getStudentId());
        Assert.assertTrue("Degree levels not same",
                degree.getDegreeLevel().equals(d.getDegreeLevel()));
        Assert.assertEquals(degree.getGpa(), d.getGpa(), .01);
        Assert.assertTrue("Degree graduation terms not same",
                degree.getGraduationTerm().equals(d.getGraduationTerm()));
        Assert.assertEquals("Degree graduation years not same",
                degree.getGraduationYear(), d.getGraduationYear());
        Assert.assertTrue("Degree programs not same",
                degree.getProgram().equals(d.getProgram()));
    }
}