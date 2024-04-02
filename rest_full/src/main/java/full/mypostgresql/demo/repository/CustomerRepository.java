package full.mypostgresql.demo.repository;

import full.mypostgresql.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
