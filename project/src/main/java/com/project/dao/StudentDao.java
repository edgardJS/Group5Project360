package com.project.dao;

import com.project.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Adds a student.
     *
     * @param student student to add
     */
    public void testAddStudent(Student student) {
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
    public void testUpdateStudent(Student student) {
        String sql = "update Student set uwEmail = ?, externalEmail = ?"
                    + "where studentId = ?";
        Object[] parameters = {student.getUwEmail(), student.getEmail(), student.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR};
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
        return (Student) jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
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
            students.add(student);
        }
//        for(Student student: students) {
//            System.out.println(student);
//        }
        return students;
    }
}

/**
 * Gets data from row of MySQL data and maps it to a Student.
 */
class StudentRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Student student = new Student();
        student.setFirstName(rs.getString("firstName"));
        student.setLastName(rs.getString("lastName"));
        student.setUwEmail(rs.getString("uwEmail"));
        student.setEmail(rs.getString("externalEmail"));
        student.setId(rs.getInt("studentId"));
        return student;
    }
}
