package com.example.myapi.demo.repository;

import com.example.myapi.demo.model.Client;
import java.sql.*;
import static com.example.myapi.demo.Constants.*;

public class ClientRepository {
    //private List<Client> clientList;
    private static Connection _connection;

    public ClientRepository() {
    }

    public static void sqlConnection() {
        try {
            _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // connection issues
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // handle any other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Client addClient(Client client) {
        try {
            sqlConnection();
            String sql = "INSERT INTO users (name, email, password, phone) VALUES (?,?,?,?)";
            PreparedStatement statement = _connection.prepareStatement(sql);
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

    public int checkClient(Client client) {
        try {
            sqlConnection();
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPassword());
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next()) ? resultSet.getInt("client_id"): -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public String getClientInfo(int id) {
        try {
            String sql = "SELECT * FROM users WHERE client_id = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            boolean hasResults = resultSet.next();
            if(!hasResults) return "";
            String result = resultSet.getString("phone");
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateClient(Client client) {
        try {
            sqlConnection();
            String sql = "UPDATE users SET name = ?, email = ?, password = ?, phone = ? WHERE client_id = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPassword());
            statement.setString(4, client.getPhone());
            statement.setInt(5, client.getClientId());
            return (statement.executeUpdate() > 0) ? true : false;
        } catch (SQLException e) {
            //
        }
        return false;
    }


}
