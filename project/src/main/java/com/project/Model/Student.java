package com.project.Model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by edgards on 11/9/16.
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
    List<Degree> degrees;
    List<String> transferColleges;
    List<Employment> employments;
}
