package com.example.myapi.demo.repository;

import com.example.myapi.demo.model.CarAd;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapi.demo.Constants.*;

public class AdRepository {
    private List<CarAd> adsList;
    private List<String> adsStringList;
    private static Connection _connection;
    public AdRepository() throws SQLException {
        //_connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public CarAd addAd(CarAd carAd) {


        return null;
    }


    public List<String> allMakeList(){
        try {
            sqlConnection();
            adsStringList = new ArrayList<>();
            String sql = "SELECT DISTINCT make AS make FROM car_ads ORDER BY make ASC";
            PreparedStatement statement = _connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String adMake = resultSet.getString("make");
                adsStringList.add(resultSet.getString("make"));
            }
            return adsStringList;
        } catch (SQLException e) {
            // SQL išimtis
            return null;
        } finally {
            if (_connection != null) {
                try {
                    _connection.close();
                } catch (SQLException e) {
                    //
                }
            }
        }


    }

    public List<String> allModelsByMakeList(String make) throws SQLException {
        sqlConnection();
        adsStringList = new ArrayList<>();
        String sql = "SELECT DISTINCT model AS model FROM car_ads WHERE make = ? ORDER BY model ASC";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, make);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            adsStringList.add(resultSet.getString("model"));
        }
        return adsStringList;
    }

    public List<CarAd> adsByMakeModelPriceList(String make, String model, BigDecimal price_from, BigDecimal price_to) throws SQLException {
        sqlConnection();
        adsList = new ArrayList<>();
        String sql = "SELECT * FROM car_ads WHERE make = ? AND model = ? AND price BETWEEN ? AND ? ORDER BY price ASC;";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, make);
        statement.setString(2, model);
        statement.setBigDecimal(3, price_from);
        statement.setBigDecimal(4, price_to);
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
            adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, readJpgFromFile()));
        }
        return adsList;


    }





    public List<CarAd> allAdsList(String make, String model) throws SQLException {
        sqlConnection();
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
            adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, readJpgFromFile()));
        }
        return adsList;


    }

    public byte[] readJpgFromFile(){
        String imagePath = "C:/JavaTest/API/auto-trade/toyota-corolla.jpg";

        try {
            // Step 1: Read the image from the file
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            // Step 2: Convert the image to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            // Now imageInBytes contains the JPG image as a byte array
            System.out.println("Image successfully converted to byte array!");
            // (Optional) If you want to verify, you can print the byte array length
            System.out.println("Byte array length: " + imageInBytes.length);
            return imageInBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sqlConnection() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


}
