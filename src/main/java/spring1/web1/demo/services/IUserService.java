package spring1.web1.demo.services;

import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Registered_users;
import java.util.List;

public interface IUserService {

    Registered_users createUser(Registered_users user) throws ClientFaultException;

    void updateUser(Registered_users user, Integer id);

    void deleteUser(Integer id);

    List<Registered_users> getAllUsers();

    Registered_users getUserById(Integer id);

    List<Integer> getAllIds();
}
