package com.project.dao;

import com.project.Model.Student;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by edgards on 12/4/16.
 */
public interface StudentDao {
    public void addStudent(Student student) throws SQLException;

    /**
     * Updates a student.
     *
     * @param student student to update
     */
    public void updateStudent(Student student);

    /**
     * Gets a student by id.
     *
     * @param id id to get by
     * @return the student
     */
    public Student getStudent(int id);

    /**
     * Gets all students
     *
     * @return list of students
     */
    public List<Student> getStudents();
}
