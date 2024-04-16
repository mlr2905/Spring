package spring1.web1.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;
import spring1.web1.demo.model.User;
import spring1.web1.demo.repository.UserRepository;
import spring1.web1.demo.services.RedisDetailsConfig;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = {RedisDetailsConfig.class})
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		//var jdbcTemplate = context.getBean("JdbcTemplate", JdbcTemplate.class)
		//String scriptContent = StreamUtils.copyToString(scriptResource.getInputStream(), StandardCharsets.UTF_8);
		//jdbcTemplate.execute(scriptContent);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
		return args -> {
			// SHOW --> ResponseEntity!!
			jdbcTemplate.execute(
				"CREATE TABLE IF NOT EXISTS \"user\" ("+
				"    id SERIAL PRIMARY KEY,\n" +
				"    username VARCHAR(60) NOT NULL DEFAULT '',\n" +
				"    password VARCHAR(255) NOT NULL DEFAULT '',\n" +
				"    email VARCHAR(320) NOT NULL DEFAULT '',\n" +
				"    role_id INTEGER NOT NULL DEFAULT 'REGULAR',\n" +
				"    CONSTRAINT users_email_key UNIQUE (email),\n" +
				"    CONSTRAINT users_username_key UNIQUE (username),\n" +
				"    CONSTRAINT role_id FOREIGN KEY (role_id)\n" +
				"        REFERENCES public.roles (id) MATCH SIMPLE\n" +
				"        ON UPDATE NO ACTION\n" +
				"        ON DELETE NO ACTION\n" +
				"        NOT VALID\n" +
				");"
			);
			userRepository.createUser(new User(1, "tomer", "avivi", "758547487@gmail.com", 1));

		};
	}

}
