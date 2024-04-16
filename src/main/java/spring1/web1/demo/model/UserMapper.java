package spring1.web1.demo.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<Registered_users> {

    @Override
    public Registered_users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Registered_users(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                Integer.parseInt(rs.getString("role_id"))
               );
    }

}
