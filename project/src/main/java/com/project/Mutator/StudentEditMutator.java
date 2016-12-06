package com.project.Mutator;

import com.project.Model.AddStudentForm;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.StudentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * Created by edgards on 12/5/16.
 */
@Component
public class StudentEditMutator {
    @Autowired
    StudentDaoImpl studentDaoImpl;

    @Autowired
    DegreeDao degreeDao;

    @Autowired
    EmploymentDao employmentDao;

    public boolean editStudentSubmit(AddStudentForm addStudentForm) {
        Student student = new Student();
        student.setId(addStudentForm.getId());
        student.setLastName(addStudentForm.getLastName());
        student.setFirstName(addStudentForm.getFirstName());
        student.setUwEmail(addStudentForm.getUwEmail());
        student.setEmail(addStudentForm.getEmail());
        student.setTransferColleges(addStudentForm.getTransferSchools());
//        if (addStudentForm.getEmail() != null || !addStudentForm.getEmail().isEmpty()) {
//            student.setEmail(addStudentForm.getEmail());
//        }
        studentDaoImpl.updateStudent(student);
        return true;
    }
}
