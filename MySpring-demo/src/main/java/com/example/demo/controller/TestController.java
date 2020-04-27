package com.example.demo.controller;

import com.example.demo.pojo.Type;
import com.example.demo.pojo.User;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                   @RequestParam String id) throws IOException {
        //04a2536f-0f4a-11e9-8f01-309c23fd150a
        System.out.println(id);
        User result = testService.queryById(id);
        System.out.println(result);
        return result;
    }

    @ResponseBody
    @RequestMapping("/type")
    public int type(HttpServletRequest request,
                    HttpServletResponse response,
                    @RequestParam String id,
                    @RequestParam Type type) {
        System.out.println(id + "," + type);
        return testService.setType(id, type);
    }

    @ResponseBody
    @RequestMapping("/exception")
    public int exception(HttpServletRequest request,
                         HttpServletResponse response) {
        throw new RuntimeException("故意抛出异常!");
    }

}
