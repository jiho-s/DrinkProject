package com.b511.drink.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public void authenticate(@RequestParam Map<String, String> map,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication result = new UsernamePasswordAuthenticationToken(
                map.get("username"), map.get("password"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(result);
        handler.onAuthenticationSuccess(request, response, result);
    }

}
