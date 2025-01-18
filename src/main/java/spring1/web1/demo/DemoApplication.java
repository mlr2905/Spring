package spring1.web1.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import spring1.web1.demo.repository.ClientRepository;
import spring1.web1.demo.services.RedisDetailsConfig;

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
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, ClientRepository clientRepository) {
		return args -> {
			 };
	}
}
