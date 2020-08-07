package com.example.demo.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SuccessZhang
 * @date 2020/08/05
 */
@Controller
@RequestMapping("**")
public class GlobalResolver implements HandlerExceptionResolver {

    @RequestMapping
    public String resolveNotFound() {
        return "404";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        ModelMap model = mv.getModelMap();
        model.addAttribute("detail", ex.getMessage());
        StringBuilder sb = new StringBuilder("\n");
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            sb.append(stackTraceElement).append("\n");
        }
        model.addAttribute("stackTrace", sb.toString());
        mv.setViewName("500");
        return mv;
    }

}
