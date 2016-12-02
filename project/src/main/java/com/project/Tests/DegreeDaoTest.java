package com.project.Tests;

import com.project.Model.Degree;
import com.project.dao.DegreeDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Brian on 12/1/2016.
 */
public class DegreeDaoTest {

    @Autowired
    private DegreeDao Dao;

    private Degree degree;

    @Before
    public void setUp() throws Exception {
        degree = new Degree();
        degree.setStudentId(12345);
        degree.setDegreeLevel("degree");
        degree.setProgram("program");
        degree.setGraduationTerm("Fall");
        degree.setGraduationYear(2011);
    }
    
    @Test
    public void addStudentDegree() throws Exception {
        
    }
    
    @Test
    public void getStudentDegrees() throws Exception {
        
    }
    
    @Test
    public void getDegrees() throws Exception {
        
    }
    
    @Test
    public void getStudentDegree() throws Exception {
        
    }
    
}