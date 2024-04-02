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

    public Passenger addNewPassenger(Passenger newPassenger) {
        try {
            String sql = "INSERT INTO passengers (column1, column2, ...) VALUES (?, ?, ...)";
            return jdbcTemplate.queryForObject(sql, new Object[]{newPassenger.getColumn1(), newPassenger.getColumn2(), ...}, Passenger.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to add new passenger", e);
        }
    }

    public Passenger getPassengerById(int id) {
        try {
            String sql = "SELECT * FROM passengers WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Passenger.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get passenger by id", e);
        }
    }

    // Admin permission only

    public Passenger updatePassenger(int id, Passenger updatedPassenger) {
        try {
            String sql = "UPDATE passengers SET column1 = ?, column2 = ... WHERE id = ?";
            jdbcTemplate.update(sql, updatedPassenger.getColumn1(), updatedPassenger.getColumn2(), ..., id);
            return updatedPassenger;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update passenger", e);
        }
    }

    public int deletePassenger(int id) {
        try {
            String sql = "DELETE FROM passengers WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete passenger", e);
        }
    }

    public List<Passenger> getAllPassengers() {
        try {
            String sql = "SELECT * FROM get_all_passengers()";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Passenger.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get all passengers", e);
        }
    }

    public int deleteAllPassengers() {
        try {
            String sql = "DELETE FROM passengers";
            jdbcTemplate.update(sql);
            sql = "ALTER SEQUENCE passengers_id_seq RESTART WITH 1";
            jdbcTemplate.update(sql);
            return 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete all passengers", e);
        }
    }

    // Test functions only

    public void setSequenceId(int id) {
        try {
            String sql = "ALTER SEQUENCE passengers_id_seq RESTART WITH ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to set sequence id", e);
        }
    }

    public Passenger getPassengerByPassportNumber(int id) {
        try {
            String sql = "SELECT * FROM passengers WHERE passport_number = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Passenger.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get passenger by passport number", e);
        }
    }
        
        
    }
   

    
}
