package com.project;

import com.project.Model.AddStudentForm;
import com.project.Model.Student;
import com.project.Mutator.StudentAddMutator;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.ReportDao;
import com.project.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    ReportDao reportDao;

    @Autowired
    StudentAddMutator studentAddMutator;

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "/addStudent")
    public ModelAndView addStudent() {
        ModelMap modelMap = new ModelMap();
        List<String> transferColleges = degreeDao.getTransferColleges();
        List<String> degreePrograms = degreeDao.getDegreePrograms();
        List<String> degreeLevels = degreeDao.getDegreeLevels();
        modelMap.addAttribute("transferColleges", transferColleges);
        modelMap.addAttribute("program", degreePrograms);
        modelMap.addAttribute("degreeLevel", degreeLevels);
        return new ModelAndView("add-student", modelMap);
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

    @PostMapping(value = "/searchStudent")
    @ResponseBody
    public ModelAndView searchStudent(@RequestParam(value = "studentId") String studentId) throws Exception {
        Integer id = Integer.valueOf(studentId);
        Student student = studentDao.getStudent(id);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("student", student);
        return new ModelAndView("updateStudent", modelMap);
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
        return "view-students";
    }

}
