package org.hanghae99.tddframeworkstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TddFrameworkStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TddFrameworkStudyApplication.class, args);
    }

}
