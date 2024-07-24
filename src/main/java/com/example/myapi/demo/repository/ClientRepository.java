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

    public Client addClient(Client client) {
        String sql = "INSERT INTO users (name, email, password, phone) VALUES (?,?,?,?)";
        try (PreparedStatement statement = _connection.prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPassword());
            statement.setString(3, client.getPhone());
            return (statement.executeUpdate() > 0) ? client : null;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return null;    //duplicate entry
            } else {
                return null;    //other errors
            }
        }
    }

    public int checkClient(Client client) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, client.getEmail());
        statement.setString(2, client.getPassword());
        ResultSet resultSet = statement.executeQuery();
        return (resultSet.next()) ? resultSet.getInt("client_id"): -1;
    }

    public String getclientInfo(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE client_id = ?";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        boolean hasResults = resultSet.next();
        if(!hasResults) return "";
        String result = resultSet.getString("phone");
        return result;
    }

    public int updateClient(Client client) throws SQLException {

        return -1;
    }


}
