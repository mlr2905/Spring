package spring1.web1.demo.repository;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Registered_users;
import spring1.web1.demo.model.NamedAlreadyExistException;

import java.util.List;

public interface IUserRepository {

    String createUser(Registered_users user);

    Registered_users createUserReturnId(Registered_users user) throws NamedAlreadyExistException, ClientFaultException;

    void updateUser(Registered_users user, Integer id);

    void deleteUser(Integer id);

    List<Registered_users> getAllUsers();

    Registered_users getUserById(Integer id);

    List<Integer> getAllIds();

}
