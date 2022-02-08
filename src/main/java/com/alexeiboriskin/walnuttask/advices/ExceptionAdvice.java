package com.alexeiboriskin.walnuttask.advices;


import com.alexeiboriskin.walnuttask.domain.ErrorResponse;
import com.alexeiboriskin.walnuttask.exceptions.ParamValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ParamValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse missingRequestParameter(ParamValidationException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
