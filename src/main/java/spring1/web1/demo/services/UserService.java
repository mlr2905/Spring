package spring1.web1.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Registered_users;
import spring1.web1.demo.model.ExceedVIPException;
import spring1.web1.demo.repository.CacheRepositoryImpl;
import spring1.web1.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheRepositoryImpl cacheRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${maxvip}")
    private Integer maxVIP;

    @Value("${cache_on}")
    private Boolean cache_on;

    @Override
    public Registered_users createUser(Registered_users user) throws ClientFaultException {
//        System.out.println(maxVIP);
        
        // without id
        //return userRepository.createCustomer(customer);

        // with id
        return userRepository.createUserReturnId(user);
    }

    @Override
    public void updateUser(Registered_users user, Integer id) {
        userRepository.updateUser(user, id);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteUser(id);
    }

    @Override
    public List<Registered_users> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Registered_users getUserById(Integer id) {
        try {

            if (cache_on && cacheRepository.isKeyExist(String.valueOf(id))) {
                String user = cacheRepository.getCacheEntity(String.valueOf(id));
                System.out.println("reading from cache " + user);
                return objectMapper.readValue(user, Registered_users.class);
            }

            Registered_users result = userRepository.getUserById(id);

            if (cache_on) {
                cacheRepository.createCacheEntity(String.valueOf(id), objectMapper.writeValueAsString(result));
            }
            return result;

        } catch (JsonProcessingException e) {
            System.out.println(e);
            throw new IllegalStateException("cannot write json of user");
        }
    }

    @Override
    public List<Integer> getAllIds() {
        return userRepository.getAllIds();
    }


}
