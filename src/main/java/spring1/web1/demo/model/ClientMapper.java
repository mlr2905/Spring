package spring1.web1.demo.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getInt("role_id"));
    }

}
