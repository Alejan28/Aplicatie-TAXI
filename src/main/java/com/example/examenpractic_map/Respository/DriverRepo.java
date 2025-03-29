package com.example.examenpractic_map.Respository;

import com.example.examenpractic_map.Domain.Driver;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DriverRepo extends AbstractDataBaseRepo<Integer, Driver> {
    public DriverRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Iterable<Driver> findAll() {
        Set<Driver> drivers = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from drivers ");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nume = resultSet.getString("name");
                Driver driver=new Driver(nume);
                driver.setId(id);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
