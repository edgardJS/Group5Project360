package com.project;

import com.project.Model.AddStudentForm;
import com.project.Model.Degree;
import com.project.Model.Employment;
import com.project.Model.Student;
import com.project.Mutator.StudentAddMutator;
import com.project.Mutator.StudentEditMutator;
import com.project.dao.DegreeDao;
import com.project.dao.EmploymentDao;
import com.project.dao.ReportDao;
import com.project.dao.StudentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;


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

    @Autowired
    StudentEditMutator studentEditMutator;

    /**
     * The GET for main page
     * @return the main.html
     */
    @GetMapping(value = {"", "/","/main"})
    public String main() {
        return "main";
    }


    /**
     * The GET for the header.
     * @return the header.html
     */
    @GetMapping(value = "/header")
    public String header() {
        return "header";
    }

    /**
     * GET for the add student page
     * @return the html page and model
     */
    @GetMapping(value = "/addStudent")
    public ModelAndView addStudent() {
        ModelMap modelMap = new ModelMap();
        List<String> transferColleges = degreeDao.getTransferColleges();
        Set<String> degreePrograms = degreeDao.getDegreePrograms();
        Set<String> degreeLevels = degreeDao.getDegreeLevels();
        modelMap.addAttribute("transferColleges", transferColleges);
        modelMap.addAttribute("program", degreePrograms);
        modelMap.addAttribute("degreeLevel", degreeLevels);
        return new ModelAndView("add-student", modelMap);
    }

    /**
     * Post for the add student form.
     * @param addStudentForm
     * @return response on a fail or success
     * @throws Exception
     */
    @RequestMapping(value = "/addStudentForm", method = POST)
    @ResponseBody
    public ResponseEntity<String> addStudentPost(@Valid AddStudentForm addStudentForm) throws Exception {
            try {
                if (addStudentForm.getId() != null && studentAddMutator.submitAddStudent(addStudentForm)) {
                    return ResponseEntity.ok("success");
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getMessage());
            }
    }

    /**
     * The GET for search student page.
     * @param student
     * @return search-student.html
     */
    @GetMapping(value = "/searchStudent")
    public String updateStudent(@RequestParam(value = "student", required = false) Student student) {
        studentDaoImpl.getStudents();
        return "search-student";
    }

    /**
     * The POST for searching a student.
     * @param studentId
     * @return the view-student.html or same page if duplicate
     * @throws Exception
     */
    @PostMapping(value = "/searchStudent")
    @ResponseBody
    public ModelAndView searchStudent(@RequestParam(value = "studentId") String studentId) throws Exception {
        Integer id = Integer.valueOf(studentId);
        try {
            Student student = studentDaoImpl.getStudent(id);
            ArrayList<Degree> degree = degreeDao.getStudentDegrees(id);
            List<Employment> employment = employmentDao.getEmployments(id);
            List<String> transferColleges = studentDaoImpl.getStudentTransferSchool(id);
            Student completeStudent = StudentAddMutator.createStudent(student, degree, employment);
            student.setTransferColleges(transferColleges);
            viewStudents(completeStudent);
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("student", student);
            return new ModelAndView("view-edit-student", modelMap);
        } catch (Exception e) {
            ModelMap failModel = new ModelMap();
            failModel.addAttribute("studentDupe", String.class);
            return new ModelAndView("search-student", failModel);
        }
    }

    /**
     * The GET for the report graduate and employed per year page.
     * @return report-graduated-year.html and model
     */
    @GetMapping(value = "/reportGraduatedYear")
    public ModelAndView reportGraduatedYear() {
        List<Map<String, Object>> data = reportDao.getStudentsGraduatedandEmployedByYear();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("data", data);
        return new ModelAndView("report-graduated-year", modelMap);
    }

    /**
     * The GET for the report skills page.
     * @return report-skills.html and model
     */
    @GetMapping(value = "/reportSkills")
    public ModelAndView reportSkills() {
        List<Map<String, Object>> data = reportDao.getSkillsUsed();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("data", data);
        return new ModelAndView("report-skills", modelMap);
    }

    /**
     * The GET for the report for internship vs no internship page.
     * @return report-internship-employment.html and model
     */
    @GetMapping(value = "/reportInternshipEmployment")
    public ModelAndView reportInternshipEmployment() {
        int internToJob = reportDao.getInternshipEmployedCount();
        int noInternToJob = reportDao.getNoInternshipEmployedCount();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("internToJob", internToJob);
        modelMap.addAttribute("noInternToJob", noInternToJob);
        return new ModelAndView("report-internship-employment", modelMap);
    }

    /**
     * The GET for the view student page
     * @param student
     * @return view-edit-student.html and model
     */
    @GetMapping(value = "/viewStudents")
    private ModelAndView viewStudents(Student student) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("student", student);
        return new ModelAndView("view-edit-student", modelMap);
    }

    /**
     * The POST for editing a student.
     * @param addStudentForm
     * @return if the request was success or error.
     * @throws Exception
     */
    @RequestMapping(value = "/editStudentForm", method = POST)
    @ResponseBody
    public ResponseEntity<String> editStudentPost(@Valid AddStudentForm addStudentForm) throws Exception {
        try {
            studentEditMutator.editStudentSubmit(addStudentForm);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().getMessage());
        }
    }
}
