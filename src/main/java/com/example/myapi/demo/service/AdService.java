package com.example.myapi.demo.service;

import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.repository.AdRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AdService {
    AdRepository adRepository = new AdRepository();
    public AdService() throws SQLException {
    }

    public String addAd(CarAd carAd) {
        return adRepository.addAd(carAd);
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

    public String addImage(byte[] adPhoto) throws SQLException {
        return adRepository.addImage(adPhoto);
    }
}
