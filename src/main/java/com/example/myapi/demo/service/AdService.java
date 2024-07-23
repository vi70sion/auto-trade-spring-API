package com.example.myapi.demo.service;

import com.example.myapi.demo.JwtDecoder;
import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.repository.AdRepository;
import io.jsonwebtoken.JwtException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AdService {
    AdRepository adRepository = new AdRepository();
    public AdService() throws SQLException {
    }

    public boolean unautorizedCheck(String authorizationHeader){
        try {
            JwtDecoder.decodeJwt(authorizationHeader);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
    public boolean badRequestCheck(String authorizationHeader){
        return (authorizationHeader.length() < 20 || authorizationHeader == null || authorizationHeader.isEmpty()) ? false : true;
    }

    public String updateAd(CarAd carAd) {
        return adRepository.updateAd(carAd);
    }

    public String addAd(CarAd carAd) {
        return adRepository.addAd(carAd);
    }

    public CarAd getAdById(int id) {
        return adRepository.getAdById(id);
    }

    public List<CarAd> getAdsByClientId(int id) {
        return adRepository.getAdsByClientId(id);
    }

    public List<String> allMakeList() throws SQLException {
        adRepository = new AdRepository();
        return adRepository.allMakeList();
    }

    public  List<String> allModelsByMakeList(String make) throws SQLException {
        return adRepository.allModelsByMakeList(make);
    }

    public List<CarAd> adsByMakeModelPriceList(String make, String model, BigDecimal price_from, BigDecimal price_to) throws SQLException {
        return adRepository.adsByMakeModelPriceList(make, model, price_from, price_to);
    }

    public String deleteAdByAdId(int id){
        return adRepository.deleteAdByAdId(id);
    }


    public String addImage(byte[] adPhoto) throws SQLException {
        return adRepository.addImage(adPhoto);
    }
}
