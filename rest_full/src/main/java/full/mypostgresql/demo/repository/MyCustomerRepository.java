package full.mypostgresql.demo.repository;

import full.mypostgresql.demo.model.Customer;
import full.mypostgresql.demo.model.CustomerMapper;
import full.mypostgresql.demo.model.CustomerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class MyCustomerRepository {

    private static final String CUSTOMER_TABLE_NAME = "customer";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> getAllCustomers() {
        String query = String.format("Select * from %s", CUSTOMER_TABLE_NAME);
//        List<Customer> result =  jdbcTemplate.query(query,  (rs, rowNum) ->
//                new Customer(
//                        rs.getInt("id"),
//                        rs.getString("first_name"),
//                        rs.getString("last_name"),
//                        rs.getString("email")));

        List<Customer> result =  jdbcTemplate.query(query, new CustomerMapper());

        return result;
    }

    public Customer getCustomerById(Integer id) {
        String query = String.format("Select * from %s where id=?", CUSTOMER_TABLE_NAME);
        try
        {
            return jdbcTemplate.queryForObject(query, new CustomerMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        String query = String.format("Select * from %s where status like ?", CUSTOMER_TABLE_NAME);
        try
        {
            return jdbcTemplate.query(query, new CustomerMapper(), status.name());
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String createCustomer(Customer customer) {
        try {
            String query = String.format("INSERT INTO %s (first_name, last_name, email, status) VALUES (?, ?, ?, ?)", CUSTOMER_TABLE_NAME);
            jdbcTemplate.update(query, customer.getFirstName(), customer.getLastName(),
                    customer.getEmail(), customer.getStatus().name());
            return null;
        }
        catch (Exception e) {
            return "{\"Error\": \"" + e.toString() + "\" }";
        }
    }

    public void updateCustomer(Customer customer, Integer id) {
        String query = String.format("UPDATE %s SET first_name=?, last_name=?, email=? status = ? WHERE id= ?", CUSTOMER_TABLE_NAME);
        jdbcTemplate.update(query, customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getStatus().name(), id);
    }

    public void deleteCustomer(Integer id) {
        String query = String.format("DELETE FROM %s WHERE id= ?", CUSTOMER_TABLE_NAME);
        jdbcTemplate.update(query, id);
    }

    // run this if you do NOT have the table in your postgresql
    public void createTable() {
        String query = "CREATE TABLE customer ("+
        "id SERIAL NOT NULL,"+
        "first_name VARCHAR(255) DEFAULT '' NOT NULL,"+
        "last_name VARCHAR(255) DEFAULT '' NOT NULL,"+
        "email VARCHAR(255) DEFAULT '' NOT NULL,"+
        "status VARCHAR(255) DEFAULT 'REGULAR' NOT NULL,"+
        "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);
    }

}
