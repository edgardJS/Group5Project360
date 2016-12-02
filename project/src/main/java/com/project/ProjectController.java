package com.project;

import com.project.Model.AddStudentForm;
import com.project.Model.Employment;
import com.project.Mutator.StudentAddMutator;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Edgard Solorzano
 * @author Adam Waldron
 * @author Brian Jorgenson
 */
@Controller
public class ProjectController {

    @Autowired
    StudentDao studentDao;
    @Autowired
    DegreeDao degreeDao;
    @Autowired
    EmploymentDao employmentDao;

    @Autowired
    StudentAddMutator studentAddMutator;

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }
    
    @GetMapping(value = "/addStudent")
    public String addStudent() {
        List<String> skill = new ArrayList<>();
        Employment e = new Employment();
        e.setStudentId(12345);
        e.setCompanyName("company");
        e.setPosition("position");
        e.setSkills(new ArrayList<String>());
        e.setStartDate(new java.sql.Date(new Date().getTime()));
        e.setEndDate(new java.sql.Date(new Date().getTime()));
        e.setIsCurrentJob(true);
        employmentDao.addEmployment(e);
        return "add-student";
    }

    @PostMapping(value = "/addStudent")
    public String addStudentPost(@Valid AddStudentForm addStudentForm, BindingResult result,
                                 Model model) throws Exception {
        // if any errors, re-render the user info edit form
        if (result.hasErrors()) {
            return "error";
        } else {
            studentAddMutator.parseData(addStudentForm);
            return "success";
        }
    }

    @GetMapping(value = "/updateStudent")
    public String updateStudent() {
        return "update-student";
    }

    @GetMapping(value = "/createReport")
    public String createReport() {
        return "create-report";
    }

    @GetMapping(value = "/viewStudents")
    public String viewStudents() {
        return "view-students";
    }

}
