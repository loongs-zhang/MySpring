package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SuccessZhang
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping(value = "/id")
    public User id(HttpServletRequest request,
                   HttpServletResponse response,
                   @RequestParam String id) {
        //04a2536f-0f4a-11e9-8f01-309c23fd150a
        System.out.println(id);
        User result = testService.queryById(id);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/freemarker200")
    public String ok(ModelMap model) {
        model.addAttribute("data", "hello");
        return "200";
    }

    @RequestMapping("/freemarker500")
    public String error(ModelMap model) {
        try {
            throw new RuntimeException("故意抛出异常!");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            model.addAttribute("detail", throwable.getMessage());
            StringBuilder sb = new StringBuilder("\n");
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                sb.append(stackTraceElement).append("\n");
            }
            model.addAttribute("stackTrace", sb.toString());
            return "500";
        }
    }

    @ResponseBody
    @RequestMapping("/exception")
    public int exception(HttpServletRequest request,
                         HttpServletResponse response) {
        throw new RuntimeException("故意抛出异常!");
    }

}
