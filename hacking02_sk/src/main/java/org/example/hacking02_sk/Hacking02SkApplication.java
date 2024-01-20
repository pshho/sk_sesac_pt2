package org.example.hacking02_sk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("org.example.hacking02_sk.mapper")
public class Hacking02SkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hacking02SkApplication.class, args);
    }

}
