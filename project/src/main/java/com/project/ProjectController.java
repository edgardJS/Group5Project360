package com.project;

import com.project.Model.AddStudentForm;
import com.project.Model.Student;
import com.project.Mutator.StudentAddMutator;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


/**
 * Created by edgards on 10/29/16.
 */
@Controller
public class ProjectController {

    @Autowired
    StudentDao studentDao;

    @Autowired
    StudentAddMutator studentAddMutator;

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "/addStudent")
    public String addStudent() {
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
//        studentDao.testGetStudent();
        studentDao.testUpdateStudent();
        return "update-student";
    }

    @GetMapping(value = "/createReport")
    public String createReport() {
        return "create-report";
    }

    @GetMapping(value = "/viewStudents")
    public String viewStudents() {
        studentDao.testGetStudents();
        return "view-students";
    }

}
