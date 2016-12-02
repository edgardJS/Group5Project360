package com.project.Model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This class hold data on a degree that a student has completed.
 *
 * @author Edgard Solorzano
 */
@Data
public class Degree {
    Integer studentId;
    Integer degreeId;
    String degreeLevel;
    String program;
    String graduationTerm;
    Integer graduationYear;
    @NotNull
    Double gpa;
}
