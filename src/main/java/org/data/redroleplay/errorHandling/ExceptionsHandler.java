package org.data.redroleplay.errorHandling;

import org.data.redroleplay.errorHandling.costums.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserNeedAuthentication.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserNeedAuthentication(Model model , UserNeedAuthentication ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRecordNotFoundException(Model model , RecordNotFoundException ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }

    @ExceptionHandler(UserNeedAuthorisation.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNeedAuthorisation(Model model , UserNeedAuthorisation ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(Model model , ValidationException ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }

}
