package com.project.Mutator;

import com.project.Model.AddStudentForm;
import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.StudentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by edgards on 11/30/16.
 */
@Component
public class StudentAddMutator {
    @Autowired
    StudentDaoImpl studentDaoImpl;

    @Autowired
    DegreeDao degreeDao;

    @Autowired
    EmploymentDao employmentDao;

    public boolean submitAddStudent(AddStudentForm addStudentForm) {
        Student student = new Student();
        student.setId(addStudentForm.getId());
        student.setLastName(addStudentForm.getLastName());
        student.setFirstName(addStudentForm.getFirstName());
        student.setUwEmail(addStudentForm.getUwEmail());
        student.setTransferColleges(addStudentForm.getTransferSchools());
        if (addStudentForm.getEmail() != null || !addStudentForm.getEmail().isEmpty()) {
            student.setEmail(addStudentForm.getEmail());
        }
            if (addStudentForm.getProgram() != null && !addStudentForm.getProgram().isEmpty()) {
                if (checkStudentDupe(student)) {
                    return false;
                } else {
                    studentDaoImpl.addStudent(student);
                    createDegree(addStudentForm);
                }
            }
            if (addStudentForm.getCompanyName() != null && !addStudentForm.getCompanyName().isEmpty()) {
                createEmployment(addStudentForm);
            }
            return true;
    }

    private Degree createDegree(AddStudentForm addStudentForm) {
        Degree degree = new Degree();
        degree.setStudentId(addStudentForm.getId());
        degree.setDegreeLevel(addStudentForm.getDegreeLevel());
        degree.setProgram(addStudentForm.getProgram());
        degree.setGpa(addStudentForm.getGpa());
        degree.setGraduationTerm(addStudentForm.getGraduationTerm());
        degree.setGraduationYear(addStudentForm.getGraduationYear());
        degreeDao.addStudentDegree(degree);
        return degree;
    }

    private Employment createEmployment(AddStudentForm addStudentForm) {
        Employment employment = new Employment();
        employment.setStudentId(addStudentForm.getId());
        employment.setCompanyName(addStudentForm.getCompanyName());
        employment.setPosition(addStudentForm.getPosition());
        employment.setSkills(addStudentForm.getSkills());
//        if (addStudentForm.getStartDate() !=null && !("").equals(addStudentForm.getStartDate())) {
//            //Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(addStudentForm.getStartDate());
//            //employment.setStartDate(startDate);
//        }
//        if (addStudentForm.getEndDate() !=null && !("").equals(addStudentForm.getEndDate())) {
//            /Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(addStudentForm.getEndDate());
//            //employment.setStartDate(endDate);
//        }
        employment.setIsCurrentJob(addStudentForm.getIsCurrentJob());
        employment.setInternship(addStudentForm.getIsInternship());
        employment.setWillBeHired(addStudentForm.getWillBeHired());
        employmentDao.addEmployment(employment);
        return  employment;
    }

    private boolean checkStudentDupe(Student student) {
        List<Student> studentList = studentDaoImpl.getStudents();
        return studentList.stream().filter(s -> Objects.equals(s.getId(), student.getId())).findAny().isPresent();
    }
}
