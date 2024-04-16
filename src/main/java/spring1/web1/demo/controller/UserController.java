package spring1.web1.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Registered_users;
import spring1.web1.demo.model.TVMazeShowResponse;
import spring1.web1.demo.repository.UserRepository;
import spring1.web1.demo.services.UserService;
import spring1.web1.demo.services.UserServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserServiceClient apiClient;

    @GetMapping
    public List<Registered_users> get()
    {
        return userService.getAllUsers();
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity getById(@PathVariable Integer id)
    {
        Registered_users result = userService.getUserById(id);
        if (result != null ) {
            return new ResponseEntity<Registered_users>(result, HttpStatus.OK);
        }
        return new ResponseEntity<String>("{ \"Warning\": \"not found customer with Id " + id + "\" }",
                HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody Registered_users user) throws JsonProcessingException, ClientFaultException {
        try {
            String json = objectMapper.writeValueAsString(user);
            System.out.println(json);
            Registered_users c = objectMapper.readValue(json, Registered_users.class);
            //throw new Ex1
            System.out.println(c);
            Registered_users resultUser = userService.createUser(user);
            return new ResponseEntity<Registered_users>(resultUser, HttpStatus.CREATED);
        }
        catch (ClientFaultException e) {
            return new ResponseEntity<String>(String.format("{ Error: '%s' }", e.toString()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(String.format("{ Error: '%s' }", e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/{id}")
    public Registered_users put(@PathVariable Integer id, @RequestBody Registered_users user) {
        userService.updateUser(user, id);
        return user;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id)
    {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/ids")
    public List<Integer> getIds()
    {
        return userService.getAllIds();
    }

    @GetMapping(value = "/shows/{id}")
    public TVMazeShowResponse getIds(@PathVariable Integer id)
    {
        return apiClient.getShow(id);
    }


}
