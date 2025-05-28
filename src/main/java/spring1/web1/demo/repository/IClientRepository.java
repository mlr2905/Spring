package spring.web.api.repository;
import java.util.List;

import spring.web.api.model.Client;
import spring.web.api.model.ClientFaultException;
import spring.web.api.model.NamedAlreadyExistException;

public interface IClientRepository {

    String createClient(Client client);

    Client createClientReturnId(Client client) throws NamedAlreadyExistException, ClientFaultException;

    void updateClient(Client client, Integer id);

    void deleteClient(Integer id);

    List<Client> getAllClients();

    Client getClientById(Integer id);
    
    List<Integer> getAllIds();
}