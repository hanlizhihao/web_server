package com.hlz;

import com.hlz.config.AuthorSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@SpringBootApplication
public class TestApplication {
    @Autowired
    private AuthorSettings authorSettings;
    
    @RequestMapping("/")
    String index(){
        return "author name is"+authorSettings.getName()+"author age is:"+authorSettings.getAge();
    }
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
