package com.example.myapi.demo.repository;

import com.example.myapi.demo.model.Client;

import java.sql.*;
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

    public int checkUser(Client client) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, client.getEmail());
        statement.setString(2, client.getPassword());
        ResultSet resultSet = statement.executeQuery();
        return (resultSet.next()) ? resultSet.getInt("client_id"): -1;
    }

}
