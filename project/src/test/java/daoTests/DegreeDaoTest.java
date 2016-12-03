package daoTests;

import com.project.Model.Degree;
import com.project.dao.DegreeDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLDataException;
import java.sql.SQLException;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

/**
 * Created by Brian on 12/1/2016.
 */
public class DegreeDaoTest {

//    @Autowired
//    private DegreeDao Dao;

    @Mock
    DegreeDao Dao;

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
            Dao.addStudentDegree(degree);
        } catch (Exception e) {
            fail("Failure to add to StudentDegree table");
        }
    }
    
    @Test
    public void getStudentDegrees() throws Exception {
        assertNotNull("StudentDegrees returned null",
                        Dao.getStudentDegrees(12345));
    }
    
    @Test
    public void getDegrees() throws Exception {
        assertNotNull("Degrees returned null",
                        Dao.getDegrees());
    }
    
    @Test
    public void getStudentDegree() throws Exception {
        assertSame(degree, Dao.getStudentDegree(degree.getDegreeId(), 12345));
    }
    
}