package spring.web.api;

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
@EnableConfigurationProperties(value = { RedisDetailsConfig.class })
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		// var jdbcTemplate = context.getBean("JdbcTemplate", JdbcTemplate.class)
		// String scriptContent =
		// StreamUtils.copyToString(scriptResource.getInputStream(),
		// StandardCharsets.UTF_8);
		// jdbcTemplate.execute(scriptContent);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, ClientRepository clientRepository) {
		return args -> {
		};
	}
}
