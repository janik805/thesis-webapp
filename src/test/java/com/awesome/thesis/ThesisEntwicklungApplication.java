package com.awesome.thesis;

import com.awesome.thesis.configuration.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



public class ThesisEntwicklungApplication {

    public static void main(String[] args) {
        SpringApplication.from(ThesisApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }

}
