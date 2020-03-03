package io.dfjinxin.modules.test.controller;

import io.dfjinxin.modules.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/sql")
    public String test(String key, String sql){
//        String keyValue = testService.test(key);
        return testService.toVal(key, sql);
    }

    @Autowired
    private TestService testService;
}
