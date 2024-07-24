package com.example.myapi.demo.service;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.repository.ClientRepository;
import io.jsonwebtoken.JwtException;

import java.sql.SQLException;

public class ClientService {
    ClientRepository clientRepository = new ClientRepository();
    public ClientService() throws SQLException {
    }

    public Client addClient(Client client) {
        return clientRepository.addClient(client);
    }

    public int checkClient(Client client) throws SQLException {
        return clientRepository.checkClient(client);
    }

    public String getclientInfo(int id) throws SQLException {
        return clientRepository.getclientInfo(id);
    }

    public int updateClient(Client client) throws SQLException {
        return clientRepository.updateClient(client);
    }

    public boolean unautorizedCheck(String authorizationHeader){
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
    public boolean badRequestCheck(String authorizationHeader){
        return (authorizationHeader.length() < 20 || authorizationHeader == null || authorizationHeader.isEmpty()) ? false : true;
    }

}
