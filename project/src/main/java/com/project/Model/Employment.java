package com.project.Model;

import com.sun.deploy.util.StringUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Edgard Solorzano
 * @author Adam Waldron
 * @author Brian Jorgenson
 */
@Data
public class Employment {
    int studentId;
    int employmentId;
    String companyName;
    String position;
    List<String> skills;
    Date startDate;
    Date endDate;
    
    /**
     * Turns a list of skills into a string of skills.
     * In the format "skill1, skill2, etc".
     *
     * @return string of skills
     */
    public String skillsToString() {
        return StringUtils.join(skills, ", ");
    }
}
