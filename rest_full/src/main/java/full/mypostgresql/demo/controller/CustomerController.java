package full.mypostgresql.demo.controller;

import full.mypostgresql.demo.model.Customer;
import full.mypostgresql.demo.model.CustomerStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @GetMapping(value ="/{id}")
    public ResponseEntity getById(@PathVariable Integer id)
    {
        // run with id which does not exist -- 404
        // run with id which exists -- 200
        // change the query to a bad grammer SELECTTTTT -- 500
        try {
            Customer result = new Customer(1, "moshe", "ufnik", "moshe@gmail.com", CustomerStatus.VIP);
            if (result != null) {
                return new ResponseEntity<Customer>(result, HttpStatus.OK);
            }
            return new ResponseEntity<String>("{ \"Warning\": \"not found customer with Id " + id + "\" }",
                    HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<String>("{ \"Error\": \"" + e.toString() + "\" }",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/search")
    public ResponseEntity search(@RequestParam(defaultValue = "") String firstname,
                                 @RequestParam(defaultValue = "") String lastname,
                                 @RequestParam(defaultValue = "") String email)
    {
        // run with id which does not exist -- 404
        // run with id which exists -- 200
        // change the query to a bad grammer SELECTTTTT -- 500
        try {
            Customer result = new Customer(1, firstname, lastname, email, CustomerStatus.VIP);
            return new ResponseEntity<Customer>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<String>("{ \"Error\": \"" + e.toString() + "\" }",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/page")
    public String page() {
        return "<html><body><h1>Hello!</h1></body></html>";}

    @PostMapping(value ="/insert")
    public ResponseEntity createUserPost(@RequestParam(defaultValue = "") String firstname,
                              @RequestParam(defaultValue = "") String lastname,
                              @RequestParam(defaultValue = "") String email) {
        //return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        Customer result = new Customer(1, firstname, lastname, email, CustomerStatus.VIP);
        return new ResponseEntity<Customer>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/insert")
    public ResponseEntity createUserGet(@RequestParam(defaultValue = "") String firstname,
                             @RequestParam(defaultValue = "") String lastname,
                             @RequestParam(defaultValue = "") String email) {
        //return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        Customer result = new Customer(1, firstname, lastname, email, CustomerStatus.VIP);
        return new ResponseEntity<Customer>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/header")
    public String createUser(@RequestHeader("clientToken") String clientToken) {
        return "<h1>you sent in the header clientToken = " + clientToken + "</h1>";
    }

}
