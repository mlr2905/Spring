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
public class MyCustomerRepository {
   
    
    @Repository
    public class YourRepository {
    
        private final JdbcTemplate jdbcTemplate;
    
        @Autowired
        public YourRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
        public int getRegisteredTablesCount() {
            try {
                // השתמש ב־jdbcTemplate כדי לבצע שאילתת SQL לבסיס הנתונים
                String sql = "SELECT registered_Tables()";
                return jdbcTemplate.queryForObject(sql, Integer.class);
            } catch (Exception e) {
                // טיפול בשגיאות אם הייתה כל שגיאה במהלך הביצוע
                e.printStackTrace();
                throw e;
            }

            
        }
        public async int getFlightsRecordsTablesCount() {
            try {
                // השתמש ב־jdbcTemplate כדי לבצע שאילתת SQL לבסיס הנתונים
                String sql = "SELECT flights_records_tables()";
                int count = await jdbcTemplate.queryForObject(sql, Integer.class);
                return count;
            } catch (Exception e) {
                // טיפול בשגיאות אם הייתה כל שגיאה במהלך הביצוע
                e.printStackTrace();
                throw e;
            }
        }
        
        
    }
   

    
}
