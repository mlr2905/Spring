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
    
    // User functions only and admin

    public boolean checkPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            return !jdbcTemplate.queryForList(sql, username, password).isEmpty();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to check password", e);
        }
    }

    public User getUserByName(String name) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get user by name", e);
        }
    }

    public Object newUserRole1(User user) {
        try {
            String call = user.getPassword().equals("null") ?
                    "CALL sp_pass_users(?, ?, '')" : "CALL sp_i_users(?, ?, ?)";
            String password = user.getPassword().equals("null") ? "" : user.getPassword();
            return jdbcTemplate.queryForObject(call, new Object[]{user.getUsername(), user.getEmail(), password}, Object.class);
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Object newUserRole2(User user) {
        try {
            String call = user.getPassword().equals("null") ?
                    "CALL sp_pass_users_airlines(?, ?, '')" : "CALL sp_i_users_airlines(?, ?, ?)";
            String password = user.getPassword().equals("null") ? "" : user.getPassword();
            return jdbcTemplate.queryForObject(call, new Object[]{user.getUsername(), user.getEmail(), password}, Object.class);
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Object updateUser(int id, User user) {
        try {
            String sql = "CALL update_user_info(?, ?, ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{id, user.getEmail(), user.getPassword()}, Object.class);
        } catch (DataAccessException e) {
            return e;
        }
    }

    public User getByIdType(String type, int id) {
        try {
            String sql = "SELECT users.*, roles.role_name FROM users JOIN roles ON users.role_id = roles.id WHERE users." + type + " = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            return false;
        }
    }

    public User getUserById(int id) {
        try {
            String sql = "SELECT users.*, roles.role_name FROM users JOIN roles ON users.role_id = roles.id WHERE users.id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Object deleteUser(int id) {
        try {
            User user = getUserById(id);
            if (user.getRole() == 1) {
                String sql = "CALL delete_user(?)";
                return jdbcTemplate.update(sql, id);
            } else {
                String sql = "DELETE FROM users WHERE id = ?";
                return jdbcTemplate.update(sql, id);
            }
        } catch (DataAccessException e) {
            return e;
        }
    }

    // Admin permission only

    public List<User> getAllUsers() {
        try {
            String sql = "SELECT * FROM get_all_users()";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get all users", e);
        }
    }

    public int deleteAll() {
        try {
            String sql = "DELETE FROM users";
            jdbcTemplate.update(sql);
            sql = "ALTER SEQUENCE users_id_seq RESTART WITH 1";
            jdbcTemplate.update(sql);
            return 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete all users", e);
        }
    }

    // Test functions only

    public void setSequenceId(int id) {
        try {
            String sql = "ALTER SEQUENCE users_id_seq RESTART WITH ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to set sequence id", e);
        }
    }
        
    }
   

    

