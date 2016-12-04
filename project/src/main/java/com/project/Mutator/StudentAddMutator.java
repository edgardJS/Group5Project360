package com.project.Mutator;

import com.project.Model.AddStudentForm;
import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edgards on 11/30/16.
 */
@Component
public class StudentAddMutator {
    @Autowired
    StudentDao studentDao;

    @Autowired
    DegreeDao degreeDao;

    @Autowired
    EmploymentDao employmentDao;

    public String submitAddStudent(AddStudentForm addStudentForm) throws ParseException {
        Student student = new Student();
        student.setId(addStudentForm.getId());
        student.setLastName(addStudentForm.getLastName());
        student.setFirstName(addStudentForm.getFirstName());
        student.setUwEmail(addStudentForm.getUwEmail());
        student.setTransferColleges(addStudentForm.getTransferSchools());
        if (addStudentForm.getEmail() != null || !addStudentForm.getEmail().isEmpty()) {
            student.setEmail(addStudentForm.getEmail());
        }
        try {
            studentDao.addStudent(student);
            if (addStudentForm.getProgram() != null && !addStudentForm.getProgram().isEmpty()) {
                createDegree(addStudentForm);
            }
            if (addStudentForm.getCompanyName() != null && !addStudentForm.getCompanyName().isEmpty()) {
                createEmployment(addStudentForm);
            }
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
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

    private Employment createEmployment(AddStudentForm addStudentForm) throws ParseException {
        Employment employment = new Employment();
        employment.setStudentId(addStudentForm.getId());
        employment.setCompanyName(addStudentForm.getCompanyName());
        employment.setPosition(addStudentForm.getPosition());
        employment.setSkills(addStudentForm.getSkills());
        if (addStudentForm.getStartDate() !=null && !("").equals(addStudentForm.getStartDate())) {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(addStudentForm.getStartDate());
            employment.setStartDate(startDate);
        }
        if (addStudentForm.getEndDate() !=null && !("").equals(addStudentForm.getEndDate())) {
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(addStudentForm.getEndDate());
            employment.setStartDate(endDate);
        }
        employment.setIsCurrentJob(addStudentForm.getIsCurrentJob());
        employment.setInternship(addStudentForm.getIsInternship());
        employment.setWillBeHired(addStudentForm.getWillBeHired());
        employmentDao.addEmployment(employment);
        return  employment;
    }
}
