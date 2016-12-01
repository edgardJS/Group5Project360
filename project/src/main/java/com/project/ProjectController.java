package com.project;

import com.project.Model.Student;
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

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "/addStudent")
    public String addStudent(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        studentDao.testAddStudent();
        model.addAttribute("name", name);
        return "add-student";
    }

    @PostMapping(value = "/addStudent")
    public String addStudentPost(@Valid Student student, BindingResult result,
                                 Model model) throws Exception {
        studentDao.testAddStudent();
//        // if any errors, re-render the user info edit form
        if (result.hasErrors()) {
            return "fragments/user :: info-form";
        }
        // let the service layer handle the saving of the validated form fields
        return "fragments/user :: info-success";
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
