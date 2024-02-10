package org.example.projectspringserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectSpringServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectSpringServerApplication.class, args);
    }

}
