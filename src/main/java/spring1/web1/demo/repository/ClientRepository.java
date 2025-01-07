package spring1.web1.demo.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public String createClient(Client client) {

        try {

            String query = "INSERT INTO " + CLIENTS_TABLE_NAME + " (username, email, role_id, mongo_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(query, client.getUsername(), client.getEmail(), client.getRole_id(), client.getMongo_id());
            System.out.println("Client created successfully.");

            // הוספתי הודעת הצלחה למקרה שבו הפעולה מתבצעת בהצלחה
            return "Client created successfully.";
        } catch (Exception e) {
            System.out.println(e);

            // החזרת הודעת שגיאה במקרה של חריגה
            return "{\"Error\": \"" + e.toString() + "\" }";
        }
    }
    
 

    @Override
    public Client createClientReturnId(Client client) throws ClientFaultException {
        
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    
            String queryNamedParam = String.format("INSERT INTO %s (username, email, role_id, mongo_id) VALUES (:username, :email, :role_id, :mongo_id)", CLIENTS_TABLE_NAME);
    
            Map<String, Object> params = new HashMap<>();
            params.put("username", client.getUsername());
            params.put("email", client.getEmail());
            params.put("role_id", client.getRole_id());
            params.put("mongo_id", client.getMongo_id());
    
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(params);
    
            KeyHolder keyHolder = new GeneratedKeyHolder();
    
            namedParameterJdbcTemplate.update(queryNamedParam, mapSqlParameterSource, keyHolder, new String[] {"id"});
    
            Integer id = keyHolder.getKey().intValue();
            System.out.println(id);
            client.setId(id);
            return client;
        }


    @Override
    public void updateClient(Client client, Integer id) {
        String query = String.format("UPDATE %s SET username=?, email=? role_id = %d ,mongo_id = ? WHERE id= ?", CLIENTS_TABLE_NAME);
        jdbcTemplate.update(query,  client.getUsername(), client.getEmail(),
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
