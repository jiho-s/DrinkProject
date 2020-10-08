package com.b511.drink.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    // 참고 : https://www.baeldung.com/spring-boot-custom-error-page

    @RequestMapping("/error")
    public String handlerError(Model model, HttpServletRequest request){

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Integer statusCode = Integer.valueOf(status.toString());
        model.addAttribute("errorCode", statusCode.toString());

        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }

}
