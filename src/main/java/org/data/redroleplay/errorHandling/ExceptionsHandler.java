package org.data.redroleplay.errorHandling;

import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthorisation;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserNeedAuthentication.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserNeedAuthorisation(Model model , UserNeedAuthorisation ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }
}
