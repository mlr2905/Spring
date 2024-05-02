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
public class ClientRepository implements IClientRepository {

    private static final String CLIENTS_TABLE_NAME = "users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String createClient(Client client) {
       try {
           String query = String.format("INSERT INTO %s (id,username, password, email, role_id) VALUES (?,?, ?, ?, ?)", CLIENTS_TABLE_NAME);
           jdbcTemplate.update(query, client.getUsername(), client.getPassword(),
                   client.getEmail(),client.getRole_id());
           return null;
       }
       catch (Exception e) {
           return "{\"Error\": \"" + e.toString() + "\" }";
       }
    }

    @Override
    public Client createClientReturnId(Client client) throws ClientFaultException {
        try {

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String query = String.format("INSERT INTO %s (username, password, email, role_id) VALUES (?,?, ?, ?)", CLIENTS_TABLE_NAME);
            String queryNamedParam = String.format("INSERT INTO %s (username, password, email, role_id) VALUES (:username, :password, :email,role_id )", CLIENTS_TABLE_NAME);

            Map<String, Object> params = new HashMap<>();
            params.put("id", client.getId());
            params.put("username", client.getUsername());
            params.put("password", client.getPassword());
            params.put("email", client.getEmail());
            params.put("role_id", client.getRole_id());

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(params);

            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(queryNamedParam, mapSqlParameterSource, generatedKeyHolder);

            Integer id = (Integer)generatedKeyHolder.getKeys().get("id");

            client.setId(id);

            System.out.println(id);

            return client;
        }
        catch (Exception e) {
            // investigate e.toString() ...
            // "sql server is down" ... ? -- not client fault

            // "name already exist" -- client fault
            throw new NamedAlreadyExistException(String.format("client %s %s already exist" + client.getPassword(), client.getUsername()));


            // check if it was client error ? i.e. name already exist
            // if client error ...

        }
    }

    @Override
    public void updateClient(Client client, Integer id) {
        String query = String.format("UPDATE %s SET username=?, password=?, email=? role_id = ? WHERE id= ?", CLIENTS_TABLE_NAME);
        jdbcTemplate.update(query, client.getPassword(), client.getUsername(), client.getEmail(),
                client.getRole_id(), id);
    }

    @Override
    public void deleteClient(Integer id) {
        String query = String.format("DELETE FROM %s WHERE id= ?", CLIENTS_TABLE_NAME);
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Client> getAllClients() {
        String query = String.format("Select * from %s", CLIENTS_TABLE_NAME);
        return jdbcTemplate.query(query, new ClientMapper());

    }

    @Override
    public Client getClientById(Integer id) {



        String query = String.format("Select * from %s where id=?", CLIENTS_TABLE_NAME);
        try
        {
            return jdbcTemplate.queryForObject(query, new ClientMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }

   
    }

    @Override
    public List<Integer> getAllIds() {
        String query = String.format("SELECT id FROM %s", CLIENTS_TABLE_NAME);
        return jdbcTemplate.queryForList(query, Integer.class);
    }

  

}
