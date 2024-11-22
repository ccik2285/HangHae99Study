package org.hanghae99.tddframeworkstudy;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@SpringBootApplication
public class TddFrameworkStudyApplication {

    public static void main(String[] args) {

        SpringApplication.run(TddFrameworkStudyApplication.class, args);
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }



}
