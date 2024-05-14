package org.data.redroleplay.errorHandling;

import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserNeedAuthentication.class)
    public String handleUserNeedAuthentication(Model model , UserNeedAuthentication ex) {
        model.addAttribute("error", ex.getError());
        return "error";
    }
}
