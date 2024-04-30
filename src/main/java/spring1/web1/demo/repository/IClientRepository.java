package spring1.web1.demo.repository;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Client;
import spring1.web1.demo.model.NamedAlreadyExistException;

import java.util.List;

public interface IClientRepository {

    String createClient(Client client);

    Client createClientReturnId(Client client) throws NamedAlreadyExistException, ClientFaultException;

    void updateClient(Client client, Integer id);

    void deleteClient(Integer id);

    List<Client> getAllClients();

    Client getClientById(Integer id);

    List<Integer> getAllIds();

}
