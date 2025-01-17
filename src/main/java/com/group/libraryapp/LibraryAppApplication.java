package com.group.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LibraryAppApplication {

    @RequestMapping("/library")
    String home() {
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }
}