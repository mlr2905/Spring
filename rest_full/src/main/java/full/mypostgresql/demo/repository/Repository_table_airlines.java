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

        
        public async Airline addNewAirline(Airline newAirline) {
            try {
                // השתמש ב־jdbcTemplate כדי להכניס את המידע החדש לבסיס הנתונים
                String sql = "INSERT INTO airlines (name, code, country) VALUES (?, ?, ?)";
                int rowsAffected = await jdbcTemplate.update(sql, newAirline.getName(), newAirline.getCode(), newAirline.getCountry());
                
                if (rowsAffected > 0) {
                    return newAirline;
                } else {
                    throw new Exception("Failed to add new airline");
                }
            } catch (Exception e) {
                // טיפול בשגיאות אם הייתה כל שגיאה במהלך הביצוע
                e.printStackTrace();
                throw e;
            }
            
        }
        
        public Airline getAirlineById(int id) {
            try {
                String sql = "SELECT airlines.*, countries.country_name, users.username AS user_name " +
                             "FROM airlines " +
                             "LEFT JOIN users ON users.id = airlines.user_id " +
                             "LEFT JOIN countries ON countries.id = airlines.country_id " +
                             "WHERE airlines.id = ?";
                return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Airline.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to fetch airline by ID", e);
            }
        }

        public int updateAirline(int id, Airline updatedAirline) {
            try {
                String sql = "UPDATE airlines SET name = ?, code = ?, country = ? WHERE id = ?";
                return jdbcTemplate.update(sql, updatedAirline.getName(), updatedAirline.getCode(), updatedAirline.getCountry(), id);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to update airline", e);
            }
        }

        
        public int deleteAirline(int id) {
            try {
                String sql = "DELETE FROM airlines WHERE id = ?";
                return jdbcTemplate.update(sql, id);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to delete airline", e);
            }
        }

        
        public List<Airline> getAllAirlines() {
            try {
                String sql = "SELECT * FROM get_all_airlines()";
                return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Airline.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to retrieve airlines", e);
            }
        }

        
        public int deleteAllAirlines() {
            try {
                String sql = "DELETE FROM airlines";
                jdbcTemplate.update(sql);
                
                sql = "ALTER SEQUENCE airlines_id_seq RESTART WITH 1";
                jdbcTemplate.update(sql);
                
                return 1;
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to delete all airlines", e);
            }
        }

        // ---------------Test functions only---------------

        
        public void setSequenceId(int id) {
            try {
                String sql = "ALTER SEQUENCE airlines_id_seq RESTART WITH ?";
                jdbcTemplate.update(sql, id);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to set sequence id", e);
            }
        }

        public Airline getAirlineByName(String name) {
            try {
                String sql = "SELECT * FROM airlines WHERE name = ?";
                return jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Airline.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to get airline by name", e);
            }
        }

        
        public Airline getAirlineByIdOrType(String type, String id) {
            try {
                String sql = "SELECT * FROM airlines WHERE " + type + " = ?";
                return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Airline.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to get airline by " + type, e);
            }
        }

        public List<Flight> getFlightsByAirlineId(int id) {
            try {
                String sql = "SELECT * FROM flights WHERE airline_id = ?";
                return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to get flights by airline id", e);
            }
        }
    }
   

    

