package com.example.myapi.demo.controller;


import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.service.AdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
public class AdController {
    AdService adService = new AdService();
    public AdController() throws SQLException {
    }



    @CrossOrigin
    @PostMapping("/ad/add")
    public ResponseEntity<CarAd> addAd(@RequestParam CarAd carAd, @RequestParam("image") MultipartFile image) {
        try {
            //CarAd carAd = new CarAd();
            byte[] adPhoto = image.getBytes();   // convert the image to a byte array
            //carAd.setPhoto(adPhoto);
            //adService.addAd(carAd);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(carAd);
        } catch (IOException e) {
            // if error;

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @CrossOrigin
    @GetMapping("/ad/makes")
    public ResponseEntity<List<String>> allMakeList() throws SQLException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allMakeList());
    }

    @CrossOrigin
    @GetMapping("/ad/models/{make}")
    public ResponseEntity<List<String>> allModelsByMakeList(@PathVariable String make) throws SQLException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allModelsByMakeList(make));
    }

    @CrossOrigin
    @GetMapping("/ad/{make}/{model}/{price_from}/{price_to}")
    public ResponseEntity<List<CarAd>> AdsByMakeModelPriceList(@PathVariable String make, @PathVariable String model,
                                                   @PathVariable BigDecimal price_from, @PathVariable BigDecimal price_to) throws SQLException {
        System.out.println();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.adsByMakeModelPriceList(make, model, price_from, price_to));
    }

//    @CrossOrigin
//    @GetMapping("/ad/models/{make}")
//    public ResponseEntity<List<String>> deleteAdById(@PathVariable int id) throws SQLException {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(adService.deleteAdById(id));
//    }





}
