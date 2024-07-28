package com.example.myapi.demo.controller;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.service.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdController {
    AdService adService = new AdService();
    public AdController() {
    }

    //Skelbimo pridėjimas: Endpointas naujo skelbimo sukūrimui,
    // kuris priima duomenis (pavadinimas, markė, modelis, metai, kaina, rida, aprašymas, nuotrauka).
    // ID gauname iš JWT
    @CrossOrigin
    @PostMapping("/ad/add")
    public ResponseEntity<String> addAd( @RequestPart("car") String carJson, @RequestParam("image") MultipartFile image,
                                         @RequestHeader("Authorization") String authorizationHeader ) {
        if(!adService.isTokenCorrect(authorizationHeader)) return ResponseEntity
                                                                        .status(HttpStatus.BAD_REQUEST)
                                                                        .body("bad request");
        if(!adService.authorize(authorizationHeader)) return ResponseEntity
                                                                        .status(HttpStatus.UNAUTHORIZED)
                                                                        .body("unauthorized");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CarAd carAd = objectMapper.readValue(carJson, CarAd.class);
            byte[] adPhoto = image.getBytes();   // konvertuojame paveikslėlį į byte array
            carAd.setPhoto(adPhoto);
            carAd.setClientId(JwtDecoder.decodeJwt(authorizationHeader).get("UserId", Integer.class)); // user ID from JWT
            if (adService.addAd(carAd)) return ResponseEntity
                                                .status(HttpStatus.OK)
                                                .body("success");
        } catch (IOException e) {
            // klaida;
        }
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("failed");
    }

    // Esamo skelbimo redagavimas
    @CrossOrigin
    @PutMapping("/ad/update")
    public ResponseEntity<String> updateAd( @RequestPart("car") String carJson, @RequestParam("image") MultipartFile image,
                                         @RequestHeader("Authorization") String authorizationHeader ) {
        if(!adService.isTokenCorrect(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("bad request");
        if(!adService.authorize(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("unauthorized");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CarAd carAd = objectMapper.readValue(carJson, CarAd.class);
            byte[] adPhoto = image.getBytes();   // convert the image to a byte array
            carAd.setPhoto(adPhoto);
            carAd.setClientId(JwtDecoder.decodeJwt(authorizationHeader).get("UserId", Integer.class)); // user ID from JWT
            if (adService.updateAd(carAd)) return ResponseEntity
                                                    .status(HttpStatus.OK)
                                                    .body("success");
        } catch (IOException e) {
            // if error;
        }
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("failed");
    }


    //Skelbimas pagal skelbimo ID
    @CrossOrigin
    @GetMapping("/ad/{id}")
    public ResponseEntity<CarAd> getAdById( @PathVariable int id) {
        CarAd carAd = adService.getAdById(id);
        return  (carAd != null ) ?  ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(carAd) :
                                    ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(null);
    }

    //Visi skelbimai pagal vartotojo ID (gaunamas iš JWT)
    @CrossOrigin
    @GetMapping("/ad/client")   // reikėtų pasitaisyti į /ad/client/ads
    public ResponseEntity<List<CarAd>> getAdsByClientId(@RequestHeader("Authorization") String authorizationHeader ) {
        if(!adService.isTokenCorrect(authorizationHeader))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ArrayList<>());
        if(!adService.authorize(authorizationHeader))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ArrayList<>());
        System.out.println();
        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(adService.getAdsByClientId(JwtDecoder.decodeJwt(authorizationHeader).get("UserId", Integer.class)));

    }

    //Visų gamintojų sąrašas, gamintojai nesikartoja
    @CrossOrigin
    @GetMapping("/ad/makes")
    public ResponseEntity<List<String>> allMakeList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allMakeList());
    }

    //Skelbimų sąrašas pagal gamintojus
    @CrossOrigin
    @GetMapping("/ad/models/{make}")
    public ResponseEntity<List<String>> allModelsByMakeList(@PathVariable String make) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allModelsByMakeList(make));
    }

    //Skelbimų sąrašas: grąžina visus skelbimus su galimybe filtruoti pagal gamintoją, markę, modelį, kainą
    @CrossOrigin
    @GetMapping("/ad/{make}/{model}/{price_from}/{price_to}")
    public ResponseEntity<List<CarAd>> AdsByMakeModelPriceList(@PathVariable String make, @PathVariable String model,
                                 @PathVariable BigDecimal price_from, @PathVariable BigDecimal price_to ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.adsByMakeModelPriceList(make, model, price_from, price_to));
    }

    //Skelbimo ištrynimas: skelbimo ištrynimas pagal ID.
    @CrossOrigin
    @DeleteMapping("/ad/{id}")
    public ResponseEntity<String> deleteAdById(@PathVariable int id) {
        return (adService.deleteAdByAdId(id)) ?
            ResponseEntity
                .status(HttpStatus.OK)
                .body("success") :
            ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("failed");
    }

}
