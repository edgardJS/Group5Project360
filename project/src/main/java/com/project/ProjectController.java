package com.project;

import com.project.Model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by edgards on 10/29/16.
 */
@Controller
public class ProjectController {

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "/addStudent")
    public String addStudent(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "addStudent";
    }

    @PostMapping(value = "/addStudent")
    ResponseEntity<Student> add(@RequestParam (value = "name") String name, @RequestParam (value = "id") Integer id) {
        //System.out.println(id);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setUwEmail("test@uw.edu");
        student.setGpa(4.0);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(value = "/updateStudent")
    public String updateStudent() {
        return "updateStudent";
    }

    @GetMapping(value = "/createReport")
    public String createReport() {
        return "createReport";
    }

}
