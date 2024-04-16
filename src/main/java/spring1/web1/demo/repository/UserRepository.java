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
public class UserRepository implements IUserRepository {

    private static final String USER_TABLE_NAME = "user";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String createUser(Registered_users user) {
       try {
           String query = String.format("INSERT INTO %s (username, password, email,role_id ) VALUES (?, ?, ?, ?)", USER_TABLE_NAME);
           jdbcTemplate.update(query, user.getUsername(), user.getPassword(),
                   user.getEmail(), user.getRole_id());
           return null;
       }
       catch (Exception e) {
           return "{\"Error\": \"" + e.toString() + "\" }";
       }
    }

    @Override
    public Registered_users createUserReturnId(Registered_users user) throws ClientFaultException {
        try {

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String query = String.format("INSERT INTO %s (username, password, email,role_id) VALUES (?, ?, ?, ?)", USER_TABLE_NAME);
            String queryNamedParam = String.format("INSERT INTO %s (username, password, email,role_id) VALUES (:username, :password, :email,:role_id,)", USER_TABLE_NAME);

            Map<String, Object> params = new HashMap<>();
            params.put("username", user.getUsername());
            params.put("password", user.getPassword());
            params.put("email", user.getEmail());
            params.put("role_id", user.getRole_id());

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(params);

            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(queryNamedParam, mapSqlParameterSource, generatedKeyHolder);

            Integer id = (Integer)generatedKeyHolder.getKeys().get("id");

            user.setId(id);

            System.out.println(id);

            return user;
        }
        catch (Exception e) {
            // investigate e.toString() ...
            // "sql server is down" ... ? -- not client fault

            // "name already exist" -- client fault
            throw new NamedAlreadyExistException(String.format("user %s %s already exist" + user.getPassword(), user.getUsername()));


            // check if it was client error ? i.e. name already exist
            // if client error ...

        }
    }

    @Override
    public void updateUser(Registered_users user, Integer id) {
        String query = String.format("UPDATE %s SET username=?, password=?, email=? role_id=?  WHERE id= ?", USER_TABLE_NAME);
        jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getEmail(),user.getRole_id(), id);
    }

    @Override
    public void deleteUser(Integer id) {
        String query = String.format("DELETE FROM %s WHERE id= ?", USER_TABLE_NAME);
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<Registered_users> getAllUsers() {
        String query = String.format("Select * from %s", USER_TABLE_NAME);
        return jdbcTemplate.query(query, new UserMapper());

        // inline mapper
        /*
        return jdbcTemplate.query(
                query,
                (rs, rowNum) ->
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email")));
         */

    }

    @Override
    public Registered_users getUserById(Integer id) {



        String query = String.format("Select * from %s where id=?", USER_TABLE_NAME);
        try
        {
            return jdbcTemplate.queryForObject(query, new UserMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }

        // inline mapper
        /*
        return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) ->
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email")),
                                id);
         */
    }

    @Override
    public List<Integer> getAllIds() {
        String query = String.format("SELECT id FROM %s", USER_TABLE_NAME);
        return jdbcTemplate.queryForList(query, Integer.class);
    }

    // @Override
    // public List<User> getUserByStatus(UserStatus status) {
    //     String query = String.format("Select * from %s where status like ?", USER_TABLE_NAME);
    //     try
    //     {
    //         return jdbcTemplate.query(query, new UserMapper(), status.name());
    //     }
    //     catch (EmptyResultDataAccessException e) {
    //         return null;
    //     }
    // }

}
