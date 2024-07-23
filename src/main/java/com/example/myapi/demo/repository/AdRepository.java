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

    public static void sqlConnection() throws SQLException {
        _connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public String addAd(CarAd carAd) {
        try {
            sqlConnection();
            String sql = "INSERT INTO car_ads ( client_id, name, make, model, year, price, mileage, description) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = _connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, carAd.getClientId());
            statement.setString(2, carAd.getName());
            statement.setString(3, carAd.getMake());
            statement.setString(4, carAd.getModel());
            statement.setInt(5, carAd.getYear());
            statement.setBigDecimal(6, carAd.getPrice());
            statement.setInt(7, carAd.getMileage());
            statement.setString(8, carAd.getDescription());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // įterptos eilutės ID
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    sql = "INSERT INTO ad_photo ( ad_id, photo) VALUES (?,?)";
                    statement = _connection.prepareStatement(sql);
                    statement.setInt(1, id);
                    statement.setBytes(2, carAd.getPhoto());
                    rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) return "success";
                }
            }
           return "failed";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateAd(CarAd carAd) {
        try {
            sqlConnection();
            String sql = "UPDATE car_ads SET client_id = ?, name = ?, make = ?, model = ?, year = ?, price = ?, mileage = ?, description = ? " +
                            "WHERE ad_id = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setInt(1, carAd.getClientId());
            statement.setString(2, carAd.getName());
            statement.setString(3, carAd.getMake());
            statement.setString(4, carAd.getModel());
            statement.setInt(5, carAd.getYear());
            statement.setBigDecimal(6, carAd.getPrice());
            statement.setInt(7, carAd.getMileage());
            statement.setString(8, carAd.getDescription());
            statement.setInt(9, carAd.getAdId());
            if (statement.executeUpdate() > 0) {
                sql = "UPDATE ad_photo SET photo = ? WHERE ad_id = ?";
                statement = _connection.prepareStatement(sql);
                statement.setBytes(1, carAd.getPhoto());
                statement.setInt(2, carAd.getAdId());
                if (statement.executeUpdate() > 0) return "success";
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
        return "failed";
    }

    public CarAd getAdById(int id) {
        try {
            sqlConnection();
            String sql = "SELECT a.*, p.photo FROM car_ads a JOIN ad_photo p ON a.ad_id = p.ad_id WHERE a.ad_id = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            boolean hasResults = resultSet.next();
            if(!hasResults) return null;
            int adId = resultSet.getInt("ad_id");
            int clientId = resultSet.getInt("client_id");
            String adName = resultSet.getString("name");
            String adMake = resultSet.getString("make");
            String adModel = resultSet.getString("model");
            int adYear = resultSet.getInt("year");
            BigDecimal adPrice = resultSet.getBigDecimal("price");
            int adMileage = resultSet.getInt("mileage");
            String adDescr = resultSet.getString("description");
            byte[] photo = resultSet.getBytes("photo");
            return new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, photo);
        } catch (SQLException e) {
            // sql klaida
        }
        return null;
    }

    public List<CarAd> getAdsByClientId(int clId) {
        try {
            sqlConnection();
            adsList = new ArrayList<>();
            String sql = "SELECT a.*, p.photo FROM car_ads a JOIN ad_photo p ON a.ad_id = p.ad_id WHERE a.client_id = ?";
            PreparedStatement statement = _connection.prepareStatement(sql);
            statement.setInt(1, clId);
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
                String adDescr = resultSet.getString("description");
                byte[] photo = resultSet.getBytes("photo");
                adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, photo));
            }
            return adsList;
        } catch (SQLException e) {
            // sql klaida
        }
        return new ArrayList<>();

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
        String sql;
        if(make.equalsIgnoreCase("empty")) {
            make = null;
        }
        if(model.equalsIgnoreCase("empty")) {
            model = null;
        }
        sql = "SELECT a.*, p.photo FROM car_ads a JOIN ad_photo p ON a.ad_id = p.ad_id " +
                "WHERE (make = ? OR ? IS NULL) " +
                "AND (model = ? OR ? IS NULL) " +
                "AND price BETWEEN ? AND ? " +
                "ORDER BY price ASC;";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setString(1, make);
        statement.setString(2, make);
        statement.setString(3, model);
        statement.setString(4, model);
        statement.setBigDecimal(5, price_from);
        statement.setBigDecimal(6, price_to);
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
            String adDescr = resultSet.getString("description");
            byte[] photo = resultSet.getBytes("photo");
            adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, photo));
        }
        return adsList;
    }

    public String deleteAdByAdId(int id) {
        try {
            sqlConnection();
            String deleteSql = "DELETE FROM ad_photo WHERE ad_id = ?";
            PreparedStatement statement = _connection.prepareStatement(deleteSql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0){
                deleteSql = "DELETE FROM car_ads WHERE ad_id = ?";
                statement = _connection.prepareStatement(deleteSql);
                statement.setInt(1, id);
                rowsDeleted = statement.executeUpdate();
                return (rowsDeleted > 0) ? "success" : "failed";
            }
        } catch (SQLException e) {
            // throw new RuntimeException(e);
        }
        return "failed";
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
            String adDescr = resultSet.getString("description");
            adsList.add(new CarAd(adId, clientId, adName, adMake, adModel, adYear, adPrice, adMileage, adDescr, null));
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

    public String addImage(byte[] adPhoto) throws SQLException {
        sqlConnection();
        String sql = "INSERT INTO ad_photo ( ad_id, photo ) VALUES (?, ?)";
        PreparedStatement statement = _connection.prepareStatement(sql);
        statement.setInt(1, 1);
        statement.setBytes(2, adPhoto);
        int rowsInserted = statement.executeUpdate();
        return "ideta";
    }




}
