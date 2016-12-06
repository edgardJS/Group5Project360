package com.project.dao;

import com.project.Model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by edgards on 12/4/16.
 */
interface StudentDao {
    void addStudent(Student student) throws SQLException;

    /**
     * Updates a student.
     *
     * @param student student to update
     */
     void updateStudent(Student student);

    /**
     * Gets a student by id.
     *
     * @param id id to get by
     * @return the student
     */
     Student getStudent(int id);

    /**
     * Gets all students
     *
     * @return list of students
     */

     List<Student> getStudents();
}
