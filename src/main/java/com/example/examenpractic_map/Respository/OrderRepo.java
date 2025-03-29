package com.example.examenpractic_map.Respository;

import com.example.examenpractic_map.Domain.Driver;
import com.example.examenpractic_map.Domain.Order;
import com.example.examenpractic_map.Domain.status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class OrderRepo extends AbstractDataBaseRepo<Integer, Order>{
    public OrderRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    public Iterable<Order> findAllInProgressForDriver(Integer driverId) {
        Set<Order> orders = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             ResultSet resultSet = connection.createStatement().executeQuery(String.format("select * from orders O where O.driver_id = '%d' and O.status='%s'", driverId, "IN_PROGRESS"))) {
            while (resultSet.next()) {
                Integer idf = resultSet.getInt("id");
                String status = resultSet.getString("status");
                LocalDateTime start = resultSet.getTimestamp("start_date").toLocalDateTime();
                String pickUp = resultSet.getString("pickup");
                String destination = resultSet.getString("destination");
                String clientName = resultSet.getString("client_name");
                Order order = new Order(driverId, com.example.examenpractic_map.Domain.status.valueOf(status), start, pickUp, destination, clientName);
                order.setId(idf);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    public void updateOrder(Order order){
        String sql = "update orders set end_date = ?, status = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, order.getStatus().toString());
            ps.setLong(3, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDriver(Order order){
        String sql = "update orders set driver_id=?, status = ? where start_date = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,order.getDriverId());
            ps.setString(2,status.IN_PROGRESS.toString());
            ps.setTimestamp(3, Timestamp.valueOf(order.getStartDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save(Order order){
        String sql = "insert into orders (status, start_date, pickup,destination, client_name) values (?, ?,?,?,?)";
        //validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, order.getStatus().toString());
            ps.setTimestamp(2, Timestamp.valueOf(order.getStartDate()));
            ps.setString(3, order.getPickupAddress());
            ps.setString(4, order.getDestinationAddress());
            ps.setString(5, order.getClientName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Order> findLatestForDriver(Integer id){
        Order order = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("select  * from orders O where O.id = '%d' and status='%s' order by end_date DESC limit 1", id, "FINISHED"))) {
            if(resultSet.next()){
                Integer idf = resultSet.getInt("id");
                String status = resultSet.getString("status");
                LocalDateTime start = resultSet.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end_date").toLocalDateTime();
                String pickUp = resultSet.getString("pickup");
                String destination = resultSet.getString("destination");
                String clientName = resultSet.getString("client_name");
                order = new Order(id, com.example.examenpractic_map.Domain.status.valueOf(status), start, pickUp, destination, clientName);
                order.setId(idf);
                order.setEndDate(end);
                return(Optional.ofNullable(order));
            }
            return(Optional.ofNullable(order));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
