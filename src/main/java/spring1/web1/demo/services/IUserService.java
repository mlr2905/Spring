package spring1.web1.demo.services;

import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.User;
import java.util.List;

public interface IUserService {

    User createUser(User user) throws ClientFaultException;

    void updateUser(User user, Integer id);

    void deleteUser(Integer id);

    List<User> getAllUsers();

    User getUserById(Integer id);

    List<Integer> getAllIds();
}
