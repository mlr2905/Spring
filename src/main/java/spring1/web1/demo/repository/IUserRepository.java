package spring1.web1.demo.repository;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.User;
import spring1.web1.demo.model.NamedAlreadyExistException;

import java.util.List;

public interface IUserRepository {

    String createUser(User user);

    User createUserReturnId(User user) throws NamedAlreadyExistException, ClientFaultException;

    void updateUser(User user, Integer id);

    void deleteUser(Integer id);

    List<User> getAllUsers();

    User getUserById(Integer id);

    List<Integer> getAllIds();

}
