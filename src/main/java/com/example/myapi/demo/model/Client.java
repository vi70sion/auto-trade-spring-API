package com.example.myapi.demo.model;

public class Client {
    private int clientId;
    private String name;
    private String email;
    private String password;
    public Client(int clientId, String name, String email, String password) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getClientId() { return clientId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }




}
