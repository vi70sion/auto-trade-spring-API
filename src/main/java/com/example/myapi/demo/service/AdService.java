package com.example.myapi.demo.service;

import com.example.myapi.demo.model.CarAd;
import com.example.myapi.demo.repository.AdRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

public class AdService {
    AdRepository adRepository = new AdRepository();
    public AdService() throws SQLException {
    }

    public CarAd addAd(CarAd carAd) {
        return adRepository.addAd(carAd);
    }

    public  List<String> allModelsByMakeList(String make) throws SQLException {
        return adRepository.allModelsByMakeList(make);
    }


    public List<String> allMakeList() throws SQLException {
        return adRepository.allMakeList();
    }

}
