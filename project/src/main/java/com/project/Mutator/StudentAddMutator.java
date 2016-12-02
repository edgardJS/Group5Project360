package com.project.Mutator;

import com.project.Model.AddStudentForm;
import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgards on 11/30/16.
 */
@Component
public class StudentAddMutator {
    @Autowired
    StudentDao studentDao;

    public void parseData(AddStudentForm addStudentForm) {
        Student student = new Student();
        student.setId(addStudentForm.getId());
        student.setLastName(addStudentForm.getLastName());
        student.setFirstName(addStudentForm.getFirstName());
        student.setUwEmail(addStudentForm.getUwEmail());
        student.setTransferColleges(addStudentForm.getTransferSchools());
        if (addStudentForm.getEmail() != null || !addStudentForm.getEmail().isEmpty()) {
            student.setEmail(addStudentForm.getEmail());
        }

        studentDao.testAddStudent(student);
    }

    private ArrayList<Employment> createEmployment(AddStudentForm addStudentForm) {
        ArrayList<Employment> employment = new ArrayList<>();
        return  employment;
    }

    private ArrayList<Degree> createDegree(AddStudentForm addStudentForm) {
        ArrayList<Degree> degree = new ArrayList<>();
        return degree;
    }
}
