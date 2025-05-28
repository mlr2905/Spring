package spring.web.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.web.api.model.Client;
import spring.web.api.model.ClientFaultException;
import spring.web.api.repository.CacheRepositoryImpl;
import spring.web.api.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository ClientRepository;

    @Autowired
    private CacheRepositoryImpl cacheRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${maxvip}")
    private Integer maxVIP;

    @Value("${cache_on}")
    private Boolean cache_on;

    @Override
    public Client createClient(Client client) throws ClientFaultException {
//        System.out.println(maxVIP);
       
        return ClientRepository.createClientReturnId(client);
    }

    @Override
    public void updateClient(Client client, Integer id) {
        ClientRepository.updateClient(client, id);
    }

    @Override
    public void deleteClient(Integer id) {
        ClientRepository.deleteClient(id);
    }

    @Override
    public List<Client> getAllClients() {
        return ClientRepository.getAllClients();
    }

    @Override
    public Client getClientById(Integer id) {
        try {

            if (cache_on && cacheRepository.isKeyExist(String.valueOf(id))) {
                String client = cacheRepository.getCacheEntity(String.valueOf(id));
                System.out.println("reading from cache " + client);
                return objectMapper.readValue(client, Client.class);
            }

            Client result = ClientRepository.getClientById(id);

            if (cache_on) {
                cacheRepository.createCacheEntity(String.valueOf(id), objectMapper.writeValueAsString(result));
            }
            return result;

        } catch (JsonProcessingException e) {
            System.out.println(e);
            throw new IllegalStateException("cannot write json of Client");
        }
    }

    @Override
    public List<Integer> getAllIds() {
        return ClientRepository.getAllIds();
    }
}