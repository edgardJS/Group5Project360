package com.project.Model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by edgards on 11/9/16.
 */
@Data
public class Employment {
    String companyName;
    List<String> skills;
    Date startDate;
    Date endDate;
    String position;
}
