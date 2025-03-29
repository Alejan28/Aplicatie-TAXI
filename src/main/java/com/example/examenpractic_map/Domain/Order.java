package com.example.examenpractic_map.Domain;

import java.time.LocalDateTime;

public class Order extends Entity<Integer>{
    private Integer driverId;
    private status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String pickupAddress;
    private String destinationAddress;
    private String clientName;
    public Order(Integer driverId, status status, LocalDateTime startDate, String pickupAddress, String destinationAddress, String clientName){
        this.driverId = driverId;
        this.status = status;
        this.startDate = startDate;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.clientName = clientName;
    }
    public Integer getDriverId(){
        return driverId;
    }
    public status getStatus(){
        return status;
    }
    public LocalDateTime getStartDate(){
        return startDate;
    }
    public void setDriverId(Integer newDriverId){
        driverId = newDriverId;
    }
    public void setStatus(status newStatus){
        status = newStatus;
    }
    public void setStartDate(LocalDateTime newStartDate){
        startDate = newStartDate;
    }
    public String getPickupAddress(){
        return pickupAddress;
    }
    public String getDestinationAddress(){
        return destinationAddress;
    }
    public String getClientName(){
        return clientName;
    }
    public void setEndDate(LocalDateTime newEndDate){
        endDate = newEndDate;
    }
    public LocalDateTime getEndDate(){
        return endDate;
    }
    public void setPickupAddress(String newPickupAddress){
        pickupAddress = newPickupAddress;
    }
    public void setDestinationAddress(String newDestinationAddress){
        destinationAddress = newDestinationAddress;
    }
    public void setClientName(String newClientName){
        clientName = newClientName;
    }

}
