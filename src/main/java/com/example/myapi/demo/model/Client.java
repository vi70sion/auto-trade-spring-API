package com.example.myapi.demo.model;

public class Client {
    private int clientId;
    private String name;
    private String email;
    private String password;
    private String phone;

    public Client(int clientId, String name, String email, String password, String phone) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public int getClientId() { return clientId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }

}
