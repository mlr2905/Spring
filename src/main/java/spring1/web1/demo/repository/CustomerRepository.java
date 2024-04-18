package spring1.web1.demo.repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import spring1.web1.demo.model.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository implements ICustomerRepository {

    private static final String CUSTOMER_TABLE_NAME = "users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String createCustomer(Customer customer) {
       try {
           String query = String.format("INSERT INTO %s (id,username, password, email, role_id) VALUES (?,?, ?, ?, ?)", CUSTOMER_TABLE_NAME);
           jdbcTemplate.update(query, customer.getUsername(), customer.getPassword(),
                   customer.getEmail(),customer.getRole_id());
           return null;
       }
       catch (Exception e) {
           return "{\"Error\": \"" + e.toString() + "\" }";
       }
    }

    @Override
    public Customer createCustomerReturnId(Customer customer) throws ClientFaultException {
        try {

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String query = String.format("INSERT INTO %s (id,username, password, email, role_id) VALUES (?,?, ?, ?, ?)", CUSTOMER_TABLE_NAME);
            String queryNamedParam = String.format("INSERT INTO %s (id,username, password, email, status) VALUES (:id,:username, :password, :email,role_id )", CUSTOMER_TABLE_NAME);

            Map<String, Object> params = new HashMap<>();
            params.put("id", customer.getId());
            params.put("username", customer.getUsername());
            params.put("password", customer.getPassword());
            params.put("email", customer.getEmail());
            params.put("role_id", customer.getRole_id());

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(params);

            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(queryNamedParam, mapSqlParameterSource, generatedKeyHolder);

            Integer id = (Integer)generatedKeyHolder.getKeys().get("id");

            customer.setId(id);

            System.out.println(id);

            return customer;
        }
        catch (Exception e) {
            // investigate e.toString() ...
            // "sql server is down" ... ? -- not client fault

            // "name already exist" -- client fault
            throw new NamedAlreadyExistException(String.format("customer %s %s already exist" + customer.getPassword(), customer.getUsername()));


            // check if it was client error ? i.e. name already exist
            // if client error ...

        }
    }

    @Override
    public void updateCustomer(Customer customer, Integer id) {
        String query = String.format("UPDATE %s SET first_name=?, last_name=?, email=? status = ? WHERE id= ?", CUSTOMER_TABLE_NAME);
        jdbcTemplate.update(query, customer.getPassword(), customer.getUsername(), customer.getEmail(),
                customer.getRole_id(), id);
    }

    @Override
    public void deleteCustomer(Integer id) {
        String query = String.format("DELETE FROM %s WHERE id= ?", CUSTOMER_TABLE_NAME);
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        String query = String.format("Select * from %s", CUSTOMER_TABLE_NAME);
        return jdbcTemplate.query(query, new CustomerMapper());

        // inline mapper
        /*
        return jdbcTemplate.query(
                query,
                (rs, rowNum) ->
                        new Customer(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email")));
         */

    }

    @Override
    public Customer getCustomerById(Integer id) {



        String query = String.format("Select * from %s where id=?", CUSTOMER_TABLE_NAME);
        try
        {
            return jdbcTemplate.queryForObject(query, new CustomerMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }

        // inline mapper
        /*
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) ->
                        new Customer(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email")),
                                id);
         */
    }

    @Override
    public List<Integer> getAllIds() {
        String query = String.format("SELECT id FROM %s", CUSTOMER_TABLE_NAME);
        return jdbcTemplate.queryForList(query, Integer.class);
    }

  

}
