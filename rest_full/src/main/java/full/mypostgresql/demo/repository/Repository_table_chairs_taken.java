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

        // ---------------User functions only and admin---------------

        public int addNewChair(Chair newChair) {
            try {
                String sql = "INSERT INTO chairs_taken (column1, column2, ...) VALUES (?, ?, ...)";
                return jdbcTemplate.update(sql, newChair.getColumn1(), newChair.getColumn2(), ...);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to add new chair", e);
            }
        }

        // ---------------Admin permission only---------------

        public Chair getChairById(int id) {
            try {
                String sql = "SELECT * FROM chairs_taken WHERE user_id = ?";
                return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Chair.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to get chair by id", e);
            }
        }
        public int updateChair(int id, Chair updatedChair) {
            try {
                String sql = "UPDATE chairs_taken SET column1 = ?, column2 = ? WHERE id = ?";
                return jdbcTemplate.update(sql, updatedChair.getColumn1(), updatedChair.getColumn2(), id);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to update chair", e);
            }
        }

        public List<Chair> getAllChairsTaken() {
            try {
                String sql = "SELECT * FROM get_all_chairs_taken()";
                return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chair.class));
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to get all chairs taken", e);
            }
        }

        public int deleteAllChairsTaken() {
            try {
                String sql = "DELETE FROM chairs_taken";
                jdbcTemplate.update(sql);
                
                sql = "ALTER SEQUENCE chairs_id_seq RESTART WITH 1";
                jdbcTemplate.update(sql);
                
                return 1;
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to delete all chairs taken", e);
            }
        }
        // ---------------Test functions only---------------

        public void setSequenceId(int id) {
            try {
                String sql = "ALTER SEQUENCE chairs_id_seq RESTART WITH ?";
                jdbcTemplate.update(sql, id);
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to set sequence id", e);
            }
        }
    }
   

    

