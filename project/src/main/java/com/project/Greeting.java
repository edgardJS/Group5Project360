package com.project;

import lombok.Data;

/**
 * Created by edgards on 10/29/16.
 */
@Data
public class Greeting {
    long id;
    String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
