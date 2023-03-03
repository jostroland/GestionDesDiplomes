package com.hyperaccess.gestiondiplome.exception;

import lombok.Getter;

import java.util.List;

public class InvalidEntityException extends RuntimeException {
    private Integer id;


    @Getter
    private List<String> errors;

    public InvalidEntityException(Integer id) {
        super("Entity not found!");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }



}

