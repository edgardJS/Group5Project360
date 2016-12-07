package com.project.Tests;

import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.EmploymentDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Jorgenson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmploymentDaoTest {

    @Autowired
    EmploymentDao Dao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private Employment employment;

    @Before
    public void setUp() throws Exception {
        employment = new Employment();
        employment.setStudentId(2);
        employment.setEmploymentId(1);
        employment.setCompanyName("testcompany");
        employment.setPosition("testposition");
        employment.setSkills(new ArrayList<String>());
        employment.setStartDate(new Date());
        employment.setEndDate(new Date());
        employment.setSalary(23.0);
        employment.setIsCurrentJob(Boolean.TRUE);
        employment.setInternship(Boolean.TRUE);
        employment.setWillBeHired(Boolean.TRUE);
    }

    @Test
    public void addEmployment() throws Exception {
        try {
            Dao.addEmployment(employment);
        } catch (Exception e) {
            Assert.fail("Failure to add to Employment table with exception " + e);
        }
    }

    @Test
    public void updateEmployment() throws Exception {
        employment.setSalary(24.0);
        try {
            Dao.updateEmployment(employment);
        } catch (Exception e) {
            Assert.fail("Failure to update Employment with exception " + e);
        }
    }

    @Test
    public void getEmployment() throws Exception {
        Employment emp = Dao.getEmployment(1);
        Assert.assertTrue("Unable to correctly get employment",
                emp.getCompanyName().equals(employment.getCompanyName()));
    }

    @Test
    public void getCompanies() throws Exception {
        List<String> companies = Dao.getCompanies();
        Assert.assertTrue("Unable to get companies", companies.size() > 0);
    }

    @Test
    public void getEmployments() throws Exception {
        Student s = new Student();
        s.setId(2);
        List<Employment> employments = Dao.getEmployments(s.getId());
        Assert.assertTrue("Unable to get employments", employments.size() > 0);
    }

}