package com.project.Model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by edgards on 11/9/16.
 */
@Data
public class Degree {
    int degreeId;
    int studentId;
    String degreeLevel;
    String program;
    String graduationTerm;
    Integer graduationYear;
    @NotNull
    Double gpa;
}
