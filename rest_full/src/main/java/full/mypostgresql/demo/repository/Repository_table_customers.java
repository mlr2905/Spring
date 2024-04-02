package full.mypostgresql.demo.repository;

import full.mypostgresql.demo.model.Customer;
import full.mypostgresql.demo.model.CustomerMapper;
import full.mypostgresql.demo.model.CustomerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


    
    @Repository
    public class YourRepository {
    
        private final JdbcTemplate jdbcTemplate;
    
        @Autowired
        public YourRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
         // User and Admin functions

    public boolean checkCreditCardValidity(String card) {
        try {
            String sql = "SELECT checkcreditcardvalidity(?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{card}, Boolean.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to check credit card validity", e);
        }
    }

    public Customer addNewCustomer(Customer newCustomer) {
        try {
            String sql = "INSERT INTO customers (column1, column2, ...) VALUES (?, ?, ...)";
            return jdbcTemplate.queryForObject(sql, new Object[]{newCustomer.getColumn1(), newCustomer.getColumn2(), ...}, Customer.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to add new customer", e);
        }
    }

    public Customer getCustomerById(int id) {
        try {
            String sql = "SELECT customers.*, users.username AS user_name, CONCAT('************', RIGHT(customers.credit_card_no, 4)) AS credit_card_no " +
                         "FROM customers LEFT JOIN users ON users.id = customers.user_id " +
                         "WHERE customers.id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get customer by id", e);
        }
    }

    public Customer updateCustomer(int id, Customer updatedCustomer) {
        try {
            String sql = "UPDATE customers SET column1 = ?, column2 = ... WHERE id = ?";
            jdbcTemplate.update(sql, updatedCustomer.getColumn1(), updatedCustomer.getColumn2(), ..., id);
            return updatedCustomer;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update customer", e);
        }
    }

    // Admin functions

    public Customer getCustomerByName(String name) {
        try {
            String sql = "SELECT * FROM customers WHERE last_name = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get customer by name", e);
        }
    }

    public int deleteCustomer(int id) {
        try {
            String sql = "DELETE FROM customers WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete customer", e);
        }
    }

    public int deleteAllCustomers() {
        try {
            String sql = "DELETE FROM customers";
            jdbcTemplate.update(sql);
            sql = "ALTER SEQUENCE customers_id_seq RESTART WITH 1";
            jdbcTemplate.update(sql);
            return 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete all customers", e);
        }
    }

    public List<Customer> getAllCustomers() {
        try {
            String sql = "SELECT * FROM get_all_customers()";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get all customers", e);
        }
    }

    // Test function

    public void setSequenceId(int id) {
        try {
            String sql = "ALTER SEQUENCE customers_id_seq RESTART WITH ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to set sequence id", e);
        }
    }
        
        
    }
   

    

