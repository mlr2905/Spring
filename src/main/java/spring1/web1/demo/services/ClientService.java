package spring1.web1.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Client;
import spring1.web1.demo.repository.CacheRepositoryImpl;
import spring1.web1.demo.repository.ClientRepository;

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
        // System.out.println(maxVIP);

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
    public Client getClientByEmail(String email) {
        
        return ClientRepository.getClientByEmail(email);

    }

    @Override
    public List<Integer> getAllIds() {
        return ClientRepository.getAllIds();
    }

}
