package com.project;

import com.project.Model.Student;
import com.project.dao.StudentDao;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


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
    public String addStudent(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        studentDao.testAddStudent();
        model.addAttribute("name", name);
        return "add-student";
    }

    @PostMapping(value = "/addStudent")
    public String addStudentPost(@Valid AddStudentForm addStudentForm, BindingResult result,
                                 Model model) throws Exception {
        // if any errors, re-render the user info edit form
        studentDao.testAddStudent();
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
