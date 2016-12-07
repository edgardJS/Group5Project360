package com.project.dao;

import com.project.Model.Degree;
import com.project.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class holds methods that are for running reports.
 *
 * @author Brian Jorgenson
 * @author Edgard Solorzano
 * @author Adam Waldron
 */
@Repository
public class ReportDao {
    
    @Autowired
    DegreeDao degreeDao;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Gets all skills used in employment.
     *
     * @return list of skills
     */
    public List<Map<String, Object>> getSkillsUsed() {
        String sql = "select company, skills from Employment group by company";
        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * Gets the students who graduated at the provided year.
     *
     * @param year year to get graduates for
     * @return list of students by graduate year
     */
    public List<Student> getGraduatesByYear(int year) {
        String sql = "select studentId from StudentDegree where graduationYear = " + year;
        List<Integer> studentIds = (List<Integer>) jdbcTemplate.queryForList(sql, Integer.class);
        List<Student> students = new ArrayList<>();
        for (Integer id : studentIds) {
            sql = "select * from Student where studentId = " + id;
            students.add((Student) jdbcTemplate.queryForObject(sql, new StudentRowMapper()));
        }
        for (Student s : students) {
            s.setDegrees((ArrayList<Degree>)degreeDao.getStudentDegrees(s.getId()));
        }
        return students;
    }

    /**
     * Gets students graduated by year and employed
     * at all years that have both.
     * @return
     */
    public List<Map<String, Object>> getStudentsGraduatedandEmployedByYear() {
        String sql = "Select yearG, graduated, employed from (SELECT graduationYear as yearG, count(*) as graduated " +
                "FROM StudentDegree GROUP BY graduationYear) as x, " +
                "(select Year(startDate) as yearE, count(*) as employed " +
                "FROM Employment GROUP BY YEAR(startDate)) as y " +
                "where x.yearG = y.yearE";

        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * Gets the number of students who found work with an internship.
     *
     * @return the number of students who found work with an internship
     */
    public int getInternshipEmployedCount() {
        String sql = "select count(*) from Employment where "
                    + "internship = TRUE and willBeHired = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    
    /**
     * Gets the number of students who are not in an internship and found work.
     *
     * @return number of students who found work without an internship
     */
    public int getNoInternshipEmployedCount() {
        String sql = "select count(*) from Employment where "
                + "internship = FALSE and willBeHired = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
}

