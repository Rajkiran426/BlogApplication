package com.blogProject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogAPIException extends RuntimeException{

    public BlogAPIException(String massage){
        super(massage);
    }
}
