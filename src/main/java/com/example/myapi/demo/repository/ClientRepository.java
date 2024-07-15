package com.example.myapi.demo.repository;

import com.example.myapi.demo.model.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static com.example.myapi.demo.Constants.*;

public class ClientRepository {
    private List<Client> clientList;
    private Connection _connection;

    public ClientRepository() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public Client addClient(Client client) throws SQLException {
        String sql = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, client.getName());
        statement.setString(2, client.getEmail());
        statement.setString(3, client.getPassword());
        return (statement.executeUpdate() > 0) ? client : null;
    }

}
