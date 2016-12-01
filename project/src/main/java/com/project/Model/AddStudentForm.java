package com.project.Model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgards on 11/30/16.
 *
 * This is for the add a student form page.
 * Will take in all these values and create
 * Objects to store in DB.
 */
@Data
public class AddStudentForm {
    @NotNull
    Integer id;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @Email
    String uwEmail;
    @Email
    String email;
    String degreeLevel;
    String program;
    String graduationTerm;
    Integer graduationYear;
    ArrayList<String> transferSchools;
    Double gpa;
    String companyName;
    String position;
    ArrayList<String> skills;
    String startDate; // converted to date
    String endDate; //converted to date
    Double salary;
    Boolean isCurrentJob;
    Boolean willBeHired;
}
