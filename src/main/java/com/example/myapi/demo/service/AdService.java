package com.example.myapi.demo.service;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.repository.AdRepository;
import io.jsonwebtoken.JwtException;
import java.math.BigDecimal;
import java.util.List;

public class AdService {
    AdRepository adRepository = new AdRepository();
    public AdService() {
    }

    public boolean authorize(String authorizationHeader){
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public boolean isTokenCorrect(String authorizationHeader){
        return (authorizationHeader.length() < 20 || authorizationHeader == null || authorizationHeader.isEmpty()) ? false : true;
    }

    public boolean updateAd(CarAd carAd) {
        return adRepository.updateAd(carAd);
    }

    public boolean addAd(CarAd carAd) {
        return adRepository.addAd(carAd);
    }

    public CarAd getAdById(int id) {
        return adRepository.getAdById(id);
    }

    public List<CarAd> getAdsByClientId(int id) {
        return adRepository.getAdsByClientId(id);
    }

    public List<String> allMakeList() {
        return adRepository.allMakeList();
    }

    public  List<String> allModelsByMakeList(String make) {
        return adRepository.allModelsByMakeList(make);
    }

    public List<CarAd> adsByMakeModelPriceList(String make, String model, BigDecimal price_from, BigDecimal price_to) {
        return adRepository.adsByMakeModelPriceList(make, model, price_from, price_to);
    }

    public boolean deleteAdByAdId(int id){
        return adRepository.deleteAdByAdId(id);
    }


    public boolean addImage(byte[] adPhoto) {
        return adRepository.addImage(adPhoto);
    }
}
