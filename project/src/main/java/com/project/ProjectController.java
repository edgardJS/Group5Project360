package com.project;

import com.project.Model.AddStudentForm;
import com.project.Model.Student;
import com.project.Mutator.StudentAddMutator;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.ReportDao;
import com.project.dao.StudentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;


/**
 * @author Edgard Solorzano
 * @author Adam Waldron
 * @author Brian Jorgenson
 */
@Controller
public class ProjectController {

    @Autowired
    StudentDaoImpl studentDaoImpl;

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

    @GetMapping(value = "/header")
    public String header() {
        return "header";
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

    //@PostMapping(value = "/addStudentForm")
    @RequestMapping(value = "/addStudentForm", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addStudentPost(@Valid AddStudentForm addStudentForm) throws Exception {
            //studentAddMutator.submitAddStudent(addStudentForm);
            try {
                if (studentAddMutator.submitAddStudent(addStudentForm)) {
                    studentAddMutator.submitAddStudent(addStudentForm);
                    return ResponseEntity.ok("success");
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate");
                }
            } catch (Exception e) {
                //e.getCause().getMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getMessage());
            }
    }

    @PostMapping(value = "/searchStudent")
    @ResponseBody
    public ModelAndView searchStudent(@RequestParam(value = "studentId") String studentId) throws Exception {
        Integer id = Integer.valueOf(studentId);
        Student student = studentDaoImpl.getStudent(id);
        viewStudents(student);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("student", student);
        return new ModelAndView("view-edit-student", modelMap);
    }

    @GetMapping(value = "/searchStudent")
    public String updateStudent(@RequestParam(value = "student", required = false) Student student) {
//        studentDao.getStudent();
//        studentDao.testUpdateStudent();
        studentDaoImpl.getStudents();
        return "search-student";
    }

    @GetMapping(value = "/createReport")
    public String createReport() {
        return "create-report";
    }

    @GetMapping(value = "/viewStudents")
    private ModelAndView viewStudents(Student student) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("student", student);
        return new ModelAndView("view-edit-student", modelMap);
    }
}
