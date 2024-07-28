package com.example.myapi.demo.controller;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.JwtGenerator;
import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    ClientService clientService = new ClientService();
    public ClientController() {
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
    public ResponseEntity <String> checkClient(@RequestBody Client client) {
        int userId = clientService.checkClient(client);
        return (userId == -1) ?
                new ResponseEntity<>("null", HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(JwtGenerator.generateJwt(client.getEmail(), client.getPassword(), userId), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/ad/client/info")
    public ResponseEntity <String> getClientInfo(@RequestHeader("Authorization") String authorizationHeader) {
        if(!clientService.isTokenCorrect(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("bad request");
        if(!clientService.authorize(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("unauthorized");
        String userPhone = clientService.getClientInfo(JwtDecoder.decodeJwt(authorizationHeader).get("UserId", Integer.class));
        return (userPhone.isEmpty()) ?
                new ResponseEntity<>("null", HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(userPhone, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/ad/client/update")
    public ResponseEntity <String> updateClient(@RequestBody Client client, @RequestHeader("Authorization") String authorizationHeader) {
        if(!clientService.isTokenCorrect(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("bad request");
        if(!clientService.authorize(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("unauthorized");
        return (!clientService.updateClient(client)) ?
                new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>("success", HttpStatus.OK);
    }

}
