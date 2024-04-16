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
import spring1.web1.demo.model.Customer;
import spring1.web1.demo.model.CustomerStatus;
import spring1.web1.demo.repository.CustomerRepository;
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
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, CustomerRepository customerRepository) {
		return args -> {
			// SHOW --> ResponseEntity!!
			jdbcTemplate.execute(
			  "CREATE TABLE IF NOT EXISTS user (" +
				"id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass)," +
				"username character varying(60) COLLATE pg_catalog.\"default\" NOT NULL," +
				"password character varying COLLATE pg_catalog.\"default\",  // Hash passwords before storing! " +
				"email character varying(320) COLLATE pg_catalog.\"default\" NOT NULL," +
				"role_id integer NOT NULL," +
				"CONSTRAINT user_pkey PRIMARY KEY (id)," +
				"CONSTRAINT user_emall_key UNIQUE (email) INCLUDE(email)," +
				"CONSTRAINT user_username_key UNIQUE (username)," +
				"CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES public.roles (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);");
		  
			// Hash the password before creating the customer
			customerRepository.createCustomer(new Customer(0, "tomer", "dhsjds", "tomeravivi@gmail.com", 1));
		  };
		  
	}

}
