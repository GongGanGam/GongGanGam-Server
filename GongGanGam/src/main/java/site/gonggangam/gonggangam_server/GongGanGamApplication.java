package site.gonggangam.gonggangam_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GongGanGamApplication {

    public static void main(String[] args) {
        SpringApplication.run(GongGanGamApplication.class, args);
    }

}
