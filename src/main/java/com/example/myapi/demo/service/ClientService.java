package com.example.myapi.demo.service;

import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.repository.ClientRepository;
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

    public int updateClient(Client client) throws SQLException {
        return clientRepository.updateClient(client);
    }

}
