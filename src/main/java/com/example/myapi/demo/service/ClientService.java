package com.example.myapi.demo.service;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.repository.ClientRepository;
import io.jsonwebtoken.JwtException;

public class ClientService {
    ClientRepository clientRepository = new ClientRepository();
    public ClientService() {
    }

    public Client addClient(Client client) {
        return clientRepository.addClient(client);
    }

    public int checkClient(Client client) {
        return clientRepository.checkClient(client);
    }

    public String getClientInfo(int id) {
        return clientRepository.getClientInfo(id);
    }

    public boolean updateClient(Client client) {
        return clientRepository.updateClient(client);
    }

    public boolean authorize(String authorizationHeader){
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
    public boolean isTokenCorrect(String authorizationHeader){
        return (authorizationHeader.length() < 20 || authorizationHeader == null || authorizationHeader.isEmpty()) ? false : true;
    }

}
