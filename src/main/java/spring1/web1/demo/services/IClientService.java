package spring.web.api.service;

import java.util.List;

import spring.web.api.model.Client;
import spring.web.api.model.ClientFaultException;

public interface IClientService {

    Client createClient(Client client) throws ClientFaultException;

    void updateClient(Client client, Integer id);

    void deleteClient(Integer id);

    List<Client> getAllClients();

    Client getClientById(Integer id);

    List<Integer> getAllIds();
}
