package com.project;

import com.project.Model.AddStudentForm;
import com.project.Mutator.StudentAddMutator;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * @author Edgard Solorzano
 * @author Adam Waldron
 * @author Brian Jorgenson
 */
@Controller
public class ProjectController {

    @Autowired
    StudentDao studentDao;
    DegreeDao degreeDao;
    EmploymentDao employmentDao;

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

    @PostMapping(value = "/addStudentForm")
    @ResponseBody
    public ResponseEntity<String> addStudentPost(@Valid AddStudentForm addStudentForm, BindingResult result,
                                                 Model model) throws Exception {
        // if any errors, re-render the user info edit form
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            studentAddMutator.parseData(addStudentForm);
            return ResponseEntity.ok("success");
        }
    }

    @GetMapping(value = "/updateStudent")
    public String updateStudent() {
//        studentDao.getStudent();
//        studentDao.testUpdateStudent();
        studentDao.getStudents();
        return "update-student";
    }

    @GetMapping(value = "/createReport")
    public String createReport() {
        return "create-report";
    }

    @GetMapping(value = "/viewStudents")
    public String viewStudents() {
        studentDao.getStudents();
        return "view-students";
    }

}
