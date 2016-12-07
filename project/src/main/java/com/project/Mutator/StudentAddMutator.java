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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

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
                if (addStudentForm.getCompanyName() != null && !addStudentForm.getCompanyName().isEmpty()) {
                    try {
                        createEmployment(addStudentForm);
                    } catch (ParseException e) {
                        return false;
                    }
                }
            }
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
        List<String> transferSchools = addStudentForm.getTransferSchools();
        if (transferSchools.size() > 0) {
            IntStream.range(0, transferSchools.size())
                    .forEach(s -> degreeDao.addStudentTransferCollege(addStudentForm.getId(), transferSchools.get(s)));
        }
        degreeDao.addStudentDegree(degree);
        return degree;
    }

    public Employment createEmployment(AddStudentForm addStudentForm) throws ParseException {
        Employment employment = new Employment();
        employment.setStudentId(addStudentForm.getId());
        employment.setCompanyName(addStudentForm.getCompanyName());
        employment.setPosition(addStudentForm.getPosition());
        employment.setSkills(addStudentForm.getSkills());
        employment.setSalary(Double.parseDouble(addStudentForm.getSalary().replaceAll(",", "")));
        if (addStudentForm.getStartDate() != null && !("").equals(addStudentForm.getStartDate())) {
            Date javaDate = new SimpleDateFormat("dd/MM/yyyy").parse(addStudentForm.getStartDate());
            java.sql.Date date = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(javaDate));
            employment.setStartDate(date);
        }
        if (addStudentForm.getEndDate() != null && !("").equals(addStudentForm.getEndDate())) {
            Date javaDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(addStudentForm.getEndDate());
            java.sql.Date date2 = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(javaDate2));
            employment.setEndDate(date2);
        }
        employment.setIsCurrentJob(addStudentForm.getIsCurrentJob());
        employment.setInternship(addStudentForm.getIsInternship());
        employment.setWillBeHired(addStudentForm.getWillBeHired());
        employmentDao.addEmployment(employment);
        return employment;
    }

    private boolean checkStudentDupe(Student student) {
        List<Student> studentList = studentDaoImpl.getStudents();
        return studentList.stream().filter(s -> Objects.equals(s.getId(), student.getId())).findAny().isPresent();
    }

    public static Student createStudent(Student student, ArrayList<Degree> degree, List<Employment> employment) {
        student.setDegrees(degree);
        student.setEmployments(employment);
        student.setGpa(student.getDegrees().get(0).getGpa());
        return student;
    }
}
