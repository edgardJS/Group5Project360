package com.project.Tests;

import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.StudentDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Jorgenson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentDaoImplTest {
    
    @Autowired
    StudentDao Dao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private Student student;
    
    @Before
    public void setUp() throws Exception {
        student = new Student();
        student.setFirstName("firstTest");
        student.setLastName("lastTest");
        student.setEmail("test@test");
        student.setUwEmail("test@testUW");
        student.setGpa(4.0);
        student.setTransferColleges(new ArrayList<String>());
        student.setDegrees(new ArrayList<Degree>());
        student.setEmployments(new ArrayList<Employment>());
        student.setId(2);
    }
    
    @Test
    public void testAddStudent() throws Exception {
        try {
            Dao.addStudent(student);
        } catch (Exception e) {
            String s = "org.springframework.dao.DuplicateKeyException";
            if (e.toString().substring(0, s.length()).equals(s)) {
                System.err.println("This students id is already in the student table.");
            } else {
                Assert.fail("Failure to add to Student table with exception " + e);
            }
        }
    }
    
    @Test
    public void testUpdateStudent() throws Exception {
        student.setGpa(3.0);
        try {
            Dao.updateStudent(student);
        } catch (Exception e) {
            Assert.fail("Failure to update student with exception " + e);
        }
    }
    
    @Test
    public void getStudent() throws Exception {
        Student test = Dao.getStudent(2);
        Assert.assertEquals("Got wrong student", (int) test.getId(), 2);
    }
    
    @Test
    public void getStudents() throws Exception {
        Assert.assertTrue(((List<Student>)Dao.getStudents()).size() > 0);
    }
}