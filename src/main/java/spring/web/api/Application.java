package spring.web.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import spring.web.api.repository.ClientRepository;
import spring.web.api.service.RedisDetailsConfig;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = {RedisDetailsConfig.class})
public class Application {

    public static void main(String[] args) {
        // // טען את משתני הסביבה מקובץ .env
        // Dotenv dotenv = Dotenv.configure().load();

        // // הגדר את המשתנים כמשתני מערכת כדי ש-Spring יוכל לגשת אליהם עם ${}
        // dotenv.entries().forEach(entry ->
        //     System.setProperty(entry.getKey(), entry.getValue())
        // );

        // הרץ את האפליקציה
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, ClientRepository clientRepository) {
        return args -> {
            // ניתן להוסיף כאן לוגיקה התחלתית שתרוץ באתחול
        };
    }
}
