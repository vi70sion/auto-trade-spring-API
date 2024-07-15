package com.example.myapi.demo.controller;

import com.example.myapi.demo.JwtGenerator;
import com.example.myapi.demo.model.Client;
import com.example.myapi.demo.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.SQLException;

@RestController
public class ClientController {
    ClientService clientService = new ClientService();
    public ClientController() throws SQLException {
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:8080","http://127.0.0.1:5500/") // Adjust this to your frontend origin
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                            .allowedHeaders("*")
                            .allowCredentials(true);
                }
            };
        }
    }

    @CrossOrigin
    @PostMapping("/ad/client/register")
    public ResponseEntity <Client> registerClient(@RequestBody Client client) throws SQLException {
        return (clientService.addClient(client) != null) ?
                new ResponseEntity<>(client, HttpStatus.OK):
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/ad/client/login")
    public ResponseEntity <String> checkUser(@RequestBody Client client) throws SQLException {
        int userId = clientService.checkUser(client);
        return (userId == -1) ?
                new ResponseEntity<>("null", HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(JwtGenerator.generateJwt(client.getEmail(), client.getPassword(), userId), HttpStatus.OK);
    }



}
