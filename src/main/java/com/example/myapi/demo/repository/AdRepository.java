package com.example.myapi.demo.repository;

import com.example.myapi.demo.model.CarAd;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapi.demo.Constants.*;

public class AdRepository {
    private List<CarAd> adsList;
    private List<String> adsStringList;
    private Connection _connection;
    public AdRepository() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public CarAd addAd(CarAd carAd) {


        return null;
    }


    public List<String> allMakeList() throws SQLException {
        adsStringList = new ArrayList<>();
        String sql = "SELECT DISTINCT make AS make FROM car_ads ORDER BY make ASC";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String adMake = resultSet.getString("make");
            adsStringList.add(resultSet.getString("make"));
        }
        return adsStringList;
    }

    public List<String> allModelsByMakeList(String make) throws SQLException {
        adsStringList = new ArrayList<>();
        String sql = "SELECT model AS model FROM car_ads WHERE make = ? ORDER BY model ASC";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, make);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            adsStringList.add(resultSet.getString("model"));
        }
        return adsStringList;
    }


    public List<CarAd> allAdsList(String make, String model) throws SQLException {

        adsList = new ArrayList<>();
        String sql = "SELECT * FROM car_ads ORDER BY make";
        PreparedStatement statement = _connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int adId = resultSet.getInt("ad_id");
            int clientId = resultSet.getInt("client_id");
            String adName = resultSet.getString("name");
            String adMake = resultSet.getString("make");
            String adModel = resultSet.getString("model");
            int adYear = resultSet.getInt("year");
            BigDecimal adPrice = resultSet.getBigDecimal("price");
            int adMileage = resultSet.getInt("mileage");
            String adDescr = resultSet.getString("discription");
            adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr));
        }
        return adsList;


    }


}
