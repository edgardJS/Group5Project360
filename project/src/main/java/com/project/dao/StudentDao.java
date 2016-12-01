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
 * Created by edgards on 11/27/16.
 */
@Repository
public class StudentDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void testAddDegree() {
        String sql = "insert into Degree(degree, graduationTerm," +
                " graduationYear, program) values "
                + "(?, ?, ?, ?)";
        Object[] parameters = {"degree", "term", 2010, "program"};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        jdbcTemplate.update(sql, parameters, types);
    }

    public void testAddStudent() {
        String sql = "insert into Student(studentId, lastName, firstName, uwEmail, externalEmail) values "
                + "(?, ?, ?, ?, ?)";
        Object[] parameters = {1234, "lstname", "frstname", "@email", "@external"};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        jdbcTemplate.update(sql, parameters, types);
    }

    public void testUpdateStudent() {
        String sql = "update Student set uwEmail = ?, externalEmail = ?  where studentId = " + 12345;
        Object[] parameters = {"@uwEmail", "@externalEmail"};
        int[] types = {Types.VARCHAR, Types.VARCHAR};
        jdbcTemplate.update(sql, parameters, types);
    }

    public void testGetStudent() {
        String sql = "select * from Student where studentId = ?";
        int id = 12345;
        Student student = (Student) jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
//        return student;
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        System.out.println(student.getUwEmail());
        System.out.println(student.getEmail());
        System.out.println(student.getId());
    }

    public void testGetStudents() {
        String sql = "select * from Student";
        List<Student> students = new ArrayList<Student>();
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
//        return students;
        for (Student s : students) {
            System.out.println(s);
        }
    }
}

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
