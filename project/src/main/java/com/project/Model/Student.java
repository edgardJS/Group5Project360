package com.project.Model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds data on a student.
 *
 * @author Edgard Solorzano
 * @author Brian Jorgenson
 */
@Data
public class Student {
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
    Double gpa;
    ArrayList<Degree> degrees;
    List<String> transferColleges;
    List<Employment> employments;

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", id, firstName, lastName, uwEmail, email);
    }
}
