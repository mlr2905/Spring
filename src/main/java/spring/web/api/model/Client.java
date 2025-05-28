package spring.web.api.model;

/*
CREATE TABLE Client (
    id int auto_increment,
    first_name varchar(255) NOT NULL default '',
    last_name varchar(255) NOT NULL default '',
    email varchar(255) NOT NULL default '',
    PRIMARY KEY (id)
); */

import lombok.Getter;
import lombok.Setter;

public class Client {
    @Getter@Setter
    protected  Integer id;

    @Getter@Setter
    protected String username;

    @Getter@Setter
    protected String email;

    @Getter@Setter
    protected  Integer role_id;
    
    @Getter@Setter
    protected  String mongo_id;

    public Client(Integer id, String username, String email, Integer role_id,String mongo_id) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role_id = role_id;
        this.mongo_id = mongo_id;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role_id='" + role_id + '\'' +
                ", mongo_id='" + mongo_id + '\'' +

                '}';
    }
}
