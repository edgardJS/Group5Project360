package com.project.Model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by edgards on 11/9/16.
 */
@Data
public class Student {
    Integer id;
    String name;
    String uwEmail;
    String email;
    Double gpa;
    List<Degree> degrees;
    HashMap<String, String> transferColleges;
    List<Employement> employments;
}
