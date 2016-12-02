package com.project.Model;

import com.sun.deploy.util.StringUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Edgard Solorzano
 * @author Adam Waldron
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
    ArrayList<Degree> degrees;
    ArrayList<String> transferColleges;
    ArrayList<Employment> employments;

    public String toString() {
        return String.format("%s %s %s %s %s", id, firstName, lastName, uwEmail, email);
    }
    
    public String degreesToString() {
        return StringUtils.join(degrees, ", ");
    }
    
    public String transferCollegesToString() {
        return StringUtils.join(transferColleges, ", ");
    }
    
    public String employmentsToString() {
        return StringUtils.join(employments, ", ");
    }
}
