package com.hyperaccess.gestiondiplome.handlers;


import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.exception.InvalidEntityException;
import com.hyperaccess.gestiondiplome.exception.ObjectsValidationException;
import com.hyperaccess.gestiondiplome.exception.OperationNonPermittedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handlePostNotFoundException(EntityNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ProblemDetail handleIOException(IOException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
    }


    @ExceptionHandler(ObjectsValidationException.class)
    public ProblemDetail handlePostNotFoundException(ObjectsValidationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
        problemDetail.setProperty("source",e.getViolationSource());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }



    @ExceptionHandler(InvalidEntityException.class)
    public ProblemDetail onExceptionInvalidEntity(InvalidEntityException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(OperationNonPermittedException.class)
    public ProblemDetail onOperationNonPermittedException(InvalidEntityException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail onDataIntegrityViolationException(DataIntegrityViolationException e){
        //"A user already exists with the provided Email"
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail onBadCredentialsException(BadCredentialsException e){
        //"Your email and / or password is incorrect"
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

}
