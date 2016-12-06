package com.project.dao;

import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class hold database queries and actions for the student object/table.
 *
 * @author Edgard Solorzano
 * @author Adam Waldron
 * @author Brian Jorgenson
 */
@Repository
public class StudentDao {
    
    @Autowired
    DegreeDao degreeDao;
    
    @Autowired
    EmploymentDao employmentDao;
    
    @Autowired
    TransferCollegeDao transferCollegeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Adds a student.
     *
     * @param student student to add
     */
    public void addStudent(Student student) {
        String sql = "insert into Student(studentId, lastName, firstName, uwEmail, externalEmail) "
                + "values (?, ?, ?, ?, ?)";
        Object[] parameters = {student.getId(), student.getLastName(), student.getFirstName(),
                            student.getUwEmail(), student.getEmail()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        jdbcTemplate.update(sql, parameters, types);
    }
    
    /**
     * Updates a student.
     *
     * @param student student to update
     */
    public void updateStudent(Student student) {
        String sql = "update Student set uwEmail = ?, externalEmail = ?"
                    + "where studentId = ?";
        Object[] parameters = {student.getUwEmail(), student.getEmail(), student.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        jdbcTemplate.update(sql, parameters, types);
    }
    
    /**
     * Gets a student by id.
     *
     * @param id id to get by
     * @return the student
     */
    public Student getStudent(int id) {
        String sql = "select * from Student where studentId = ?";
        Student s = (Student) jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
        getDataOutsideOfTable(s);
        return s;
    }
    
    /**
     * Sets data to student not from Student table.
     *
     * @param student student to get data for
     */
    private void getDataOutsideOfTable(Student student) {
        student.setDegrees((ArrayList<Degree>)degreeDao.getStudentDegrees(student.getId()));
        student.setEmployments((ArrayList<Employment>) employmentDao.getEmployments(student));
        List<String> colleges = transferCollegeDao.getTransferCollegeById(student.getId());
        student.setTransferColleges((ArrayList<String>) colleges);
    }
    
    /**
     * Gets all students
     *
     * @return list of students
     */
    public List<Student> getStudents() {
        String sql = "select * from Student";
        List<Student> students = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Student student = new Student();
            student.setId((Integer) row.get("studentId"));
            student.setFirstName((String) row.get("firstName"));
            student.setLastName((String) row.get("lastName"));
            student.setUwEmail((String) row.get("uwEmail"));
            student.setEmail((String) row.get("externalEmail"));
            getDataOutsideOfTable(student);
            students.add(student);
        }
        return students;
    }
}
