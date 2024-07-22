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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdController {
    AdService adService = new AdService();
    public AdController() throws SQLException {
    }


    //Skelbimo pridėjimas: Endpointas naujo skelbimo sukūrimui,
    // kuris priima duomenis (pavadinimas, markė, modelis, metai, kaina, rida, aprašymas, nuotrauka).
    @CrossOrigin
    @PostMapping("/ad/add")
    public ResponseEntity<String> addAd( @RequestPart("car") String carJson, @RequestParam("image") MultipartFile image,
                                         @RequestHeader("Authorization") String authorizationHeader ) {
        if(!adService.badRequestCheck(authorizationHeader)) return ResponseEntity
                                                                        .status(HttpStatus.BAD_REQUEST)
                                                                        .body("bad request");
        if(!adService.unautorizedCheck(authorizationHeader)) return ResponseEntity
                                                                        .status(HttpStatus.UNAUTHORIZED)
                                                                        .body("unauthorized");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CarAd carAd = objectMapper.readValue(carJson, CarAd.class);
            byte[] adPhoto = image.getBytes();   // convert the image to a byte array
            carAd.setPhoto(adPhoto);
            carAd.setClientId(JwtDecoder.decodeJwt(authorizationHeader).get("UserId", Integer.class));
            if (adService.addAd(carAd).equals("success"))
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("success");
        } catch (IOException e) {
            // if error;
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
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


    //Visi skelbimai pagal vartotojo ID
    @CrossOrigin
    @GetMapping("/ad/client/{id}")
    public ResponseEntity<List<CarAd>> getAdsByClientId( @PathVariable int id, @RequestHeader("Authorization") String authorizationHeader ) {
        if(!adService.badRequestCheck(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ArrayList<>());
        if(!adService.unautorizedCheck(authorizationHeader)) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ArrayList<>());
        List<CarAd> adList = adService.getAdsByClientId(id);
        return (!adList.isEmpty()) ? ResponseEntity
                                        .status(HttpStatus.OK)
                                        .body(adList) :
                                     ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(new ArrayList<>());
    }

    //Visų gamintojų sąrašas
    @CrossOrigin
    @GetMapping("/ad/makes")
    public ResponseEntity<List<String>> allMakeList() throws SQLException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allMakeList());
    }

    //Skelbimų sąrašas pagal gamintojus
    @CrossOrigin
    @GetMapping("/ad/models/{make}")
    public ResponseEntity<List<String>> allModelsByMakeList(@PathVariable String make) throws SQLException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.allModelsByMakeList(make));
    }

    //Skelbimų sąrašas: Endpointas, kuris grąžina visus skelbimus su galimybe filtruoti pagal markę, modelį, kainą
    @CrossOrigin
    @GetMapping("/ad/{make}/{model}/{price_from}/{price_to}")
    public ResponseEntity<List<CarAd>> AdsByMakeModelPriceList(@PathVariable String make, @PathVariable String model,
                                 @PathVariable BigDecimal price_from, @PathVariable BigDecimal price_to ) throws SQLException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adService.adsByMakeModelPriceList(make, model, price_from, price_to));
    }

    //Skelbimo ištrynimas: Endpointas skelbimo ištrynimui pagal ID.
    @CrossOrigin
    @DeleteMapping("/ad/{id}")
    public ResponseEntity<String> deleteAdById(@PathVariable int id) throws SQLException {
        return (adService.deleteAdByAdId(id).equals("success")) ?
            ResponseEntity
                .status(HttpStatus.OK)
                .body("success") :
            ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("failed");
    }




}
