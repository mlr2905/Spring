package spring1.web1.demo.services;

import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Client;
import java.util.List;

public interface IClientService {

    Client createClient(Client client) throws ClientFaultException;

    void updateClient(Client client, Integer id);

    void deleteClient(Integer id);

    List<Client> getAllClients();

    Client getClientById(Integer id);

    List<Integer> getAllIds();
}