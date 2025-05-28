package spring.web.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.web.api.model.Client;
import spring.web.api.model.ClientFaultException;
import spring.web.api.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ObjectMapper objectMapper;


    @GetMapping
    public List<Client> get()
    {
        return clientService.getAllClients();
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity getById(@PathVariable Integer id)
    {
        Client result = clientService.getClientById(id);
        if (result != null ) {
            return new ResponseEntity<Client>(result, HttpStatus.OK);
        }
        return new ResponseEntity<String>("{ \"Warning\": \"not found client with Id " + id + "\" }",
                HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody Client client) throws JsonProcessingException, ClientFaultException {
        try {
            String json = objectMapper.writeValueAsString(client);
            System.out.println(client);
            System.out.println("client");

           
            Client resultClient = clientService.createClient(client);

            return new ResponseEntity<Client>(resultClient, HttpStatus.CREATED);
        }
        catch (ClientFaultException e) {
            System.out.println(e);

            return new ResponseEntity<String>(String.format("{ Error: '%s' }", e.toString()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<String>(String.format("{ Error: '%s' }", e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
    }

    @PutMapping(value = "/{id}")
    public Client put(@PathVariable Integer id, @RequestBody Client client) {
        clientService.updateClient(client, id);
        return client;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id)
    {
        clientService.deleteClient(id);
    }


   


}