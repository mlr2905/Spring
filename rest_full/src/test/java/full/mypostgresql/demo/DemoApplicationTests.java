package full.mypostgresql.demo;

import full.mypostgresql.demo.model.Customer;
import full.mypostgresql.demo.repository.CustomerRepository;
import full.mypostgresql.demo.repository.MyCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@BeforeEach
	void before() {
		System.out.println("Clean data-base");
	}

	@Autowired
	private MyCustomerRepository myCustomerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void test1() {
		// call bl
		// get all flights
		// check if we got correctly all the flights
		assertThat(1).isEqualTo(1);
	}

	@Test
	void test2() {
		//assertThat(1).isEqualTo(2);
		List<Customer> result = myCustomerRepository.getAllCustomers();
		System.out.println(result);
	}

	@Test
	void test3() {
		//assertThat(1).isEqualTo(2);
		Iterable<Customer> result = customerRepository.findAll();
		List<Customer> list = new ArrayList<>();
		result.forEach(list::add);
		System.out.println(list);
	}

	@Test
	void test4() {
		//assertThat(1).isEqualTo(2);
		myCustomerRepository.createTable();
	}
}