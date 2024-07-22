package com.example.myapi.demo.controller;

import com.example.myapi.demo.JwtGenerator;
import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
public class ClientController {
    ClientService clientService = new ClientService();
    public ClientController() throws SQLException {
    }



    @CrossOrigin
    @PostMapping("/ad/client/register")
    public ResponseEntity <Client> registerClient(@RequestBody Client client) {
        return (clientService.addClient(client) != null) ?
                new ResponseEntity<>(client, HttpStatus.OK):
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/ad/client/login")
    public ResponseEntity <String> checkClient(@RequestBody Client client) throws SQLException {
        int userId = clientService.checkClient(client);
        return (userId == -1) ?
                new ResponseEntity<>("null", HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(JwtGenerator.generateJwt(client.getEmail(), client.getPassword(), userId), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/ad/client/update")
    public ResponseEntity <String> updateClient(@RequestBody Client client) throws SQLException {

        return new ResponseEntity<>("null", HttpStatus.OK);
    }

}
