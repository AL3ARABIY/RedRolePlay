package org.data.redroleplay.error_handling;

import org.data.redroleplay.error_handling.costums.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

    private static final String ERROR = "error";

    @ExceptionHandler(UserNeedAuthentication.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserNeedAuthentication(Model model , UserNeedAuthentication ex) {
        model.addAttribute(ERROR, ex.getError());
        return ERROR;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRecordNotFoundException(Model model , RecordNotFoundException ex) {
        model.addAttribute(ERROR, ex.getError());
        return ERROR;
    }

    @ExceptionHandler(UserNeedAuthorisation.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNeedAuthorisation(Model model , UserNeedAuthorisation ex) {
        model.addAttribute(ERROR, ex.getError());
        return ERROR;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(Model model , ValidationException ex) {
        model.addAttribute(ERROR, ex.getError());
        return ERROR;
    }

    @ExceptionHandler(RedirectException.class)
    public String handleRedirectException(RedirectException ex) {
        return "redirect:" + ex.getRedirectUrl();
    }

}
