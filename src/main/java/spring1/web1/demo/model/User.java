package spring1.web1.demo.model;


import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter@Setter
    protected Integer id;

    @Getter@Setter
    protected String username;

    @Getter@Setter
    protected String password;

    @Getter@Setter
    protected String email;

    @Getter@Setter
    protected Integer role_id;
    
 
    public User(Integer id, String username, String password, String email, Integer role_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role_id = role_id;


    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role_id='" + role_id + '\'' +
                '}';
    }


}

