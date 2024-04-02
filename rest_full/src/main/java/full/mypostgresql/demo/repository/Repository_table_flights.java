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
    
     // All types of users can activate

    public boolean flightsRecordsTables(V v) {
        try {
            String sql = "SELECT flights_records_tables(?, ?, ?, ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{v.getAirlineId(), v.getOriginCountryId(), v.getDestinationCountryId(), v.getPlaneId()}, Boolean.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to check flights records tables", e);
        }
    }

    public boolean checkFlightExistence(V v) {
        try {
            String sql = "SELECT check_flight_existence(?, ?, ?, ?, ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{v.getAirlineId(), v.getOriginCountryId(), v.getDestinationCountryId(), v.getDepartureTime(), v.getLandingTime()}, Boolean.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to check flight existence", e);
        }
    }

    public List<Flight> getAllFlights() {
        try {
            String sql = "SELECT * FROM get_all_flights()";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Flight.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get all flights", e);
        }
    }

    // User functions only and admin

    public void updateRemainingTickets(int id) {
        try {
            String sql = "CALL update_remaining_tickets(?)";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update remaining tickets", e);
        }
    }

    // Airline user functions only and admin

    public Flight addNewFlight(Flight newFlight) {
        try {
            String sql = "INSERT INTO flights (column1, column2, ...) VALUES (?, ?, ...)";
            return jdbcTemplate.queryForObject(sql, new Object[]{newFlight.getColumn1(), newFlight.getColumn2(), ...}, Flight.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to add new flight", e);
        }
    }

    public Flight getFlightById(int id) {
        try {
            String sql = "SELECT flights.*, airlines.name AS airline_name, origin_countries.country_name AS origin_country_name, destination_countries.country_name AS destination_country_name, planes.seat AS total_tickets " +
                         "FROM flights " +
                         "LEFT JOIN airlines ON airlines.id = flights.airline_id " +
                         "LEFT JOIN countries AS origin_countries ON origin_countries.id = flights.origin_country_id " +
                         "LEFT JOIN countries AS destination_countries ON destination_countries.id = flights.destination_country_id " +
                         "LEFT JOIN planes ON planes.id = flights.plane_id " +
                         "WHERE flights.id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get flight by id", e);
        }
    }

    public List<Flight> getFlightsByAirlineId(int id) {
        try {
            String sql = "SELECT get_flight_details_by_airline(?)";
            return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get flights by airline id", e);
        }
    }

    public Flight updateFlight(int id, Flight updatedFlight) {
        try {
            String sql = "UPDATE flights SET column1 = ?, column2 = ... WHERE id = ?";
            jdbcTemplate.update(sql, updatedFlight.getColumn1(), updatedFlight.getColumn2(), ..., id);
            return updatedFlight;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update flight", e);
        }
    }

    public int deleteFlight(int id) {
        try {
            String sql = "CALL delete_flight(?)";
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete flight", e);
        }
    }

    // Admin permission only

    public int deleteAllFlights() {
        try {
            String sql = "DELETE FROM flights";
            jdbcTemplate.update(sql);
            sql = "ALTER SEQUENCE flights_id_seq RESTART WITH 1";
            jdbcTemplate.update(sql);
            return 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete all flights", e);
        }
    }

    // Test functions only

    public void setSequenceId(int id) {
        try {
            String sql = "ALTER SEQUENCE flights_id_seq RESTART WITH ?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to set sequence id", e);
        }
    }

    public Flight getFlightByFlightCode(String code) {
        try {
            String sql = "SELECT * FROM flights WHERE flight_code = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<>(Flight.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get flight by flight code", e);
        }
    }
      
    public List<Flight> getFlightsByAirlineIdTest(int id) {
        try {
            String sql = "SELECT get_flights_by_airline(?)";
            return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Flight.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to get flights by airline id for testing", e);
        }
    }
    }
   

    

