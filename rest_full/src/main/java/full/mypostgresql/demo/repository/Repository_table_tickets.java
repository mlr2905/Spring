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
    
      // Admin permission only

      public int updateTicket(int id, Ticket updatedTicket) {
        try {
            String sql = "UPDATE tickets SET column1 = ?, column2 = ... WHERE id = ?";
            return jdbcTemplate.update(sql, updatedTicket.getColumn1(), updatedTicket.getColumn2(), ..., id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update ticket", e);
        }
    }

    public List<Ticket> getAllTickets() {
        try {
            String sql = "SELECT * FROM get_all_tickets()";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get all tickets", e);
        }
    }

    public int deleteAllTickets() {
        try {
            String sql = "DELETE FROM tickets";
            jdbcTemplate.update(sql);
            sql = "ALTER SEQUENCE tickets_id_seq RESTART WITH 1";
            jdbcTemplate.update(sql);
            return 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete all tickets", e);
        }
    }

    // Test functions only

    public void setSequenceId(int id) {
        try {
            String sql = "ALTER SEQUENCE tickets_id_seq RESTART WITH ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to set sequence id", e);
        }
    }

    public Ticket getTicketByTicketCode(String code) {
        try {
            String sql = "SELECT * FROM tickets WHERE ticket_code = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<>(Ticket.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get ticket by ticket code", e);
        }
    }
        
        
    }
   

    
}
