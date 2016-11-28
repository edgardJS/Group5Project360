package com.project.Model;

import lombok.Data;

import java.util.DoubleSummaryStatistics;

/**
 * Created by edgards on 11/9/16.
 */
@Data
public class Job extends Employement {
    Double salary;
    Boolean isCurrentJob;
}
