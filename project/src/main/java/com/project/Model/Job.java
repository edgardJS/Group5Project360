package com.project.Model;

import lombok.Data;

/**
 * Created by edgards on 11/9/16.
 */
@Data
public class Job extends Employment {
    Double salary;
    Boolean isCurrentJob;
}
