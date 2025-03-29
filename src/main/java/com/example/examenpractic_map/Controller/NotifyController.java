package com.example.examenpractic_map.Controller;

import com.example.examenpractic_map.Domain.Driver;
import com.example.examenpractic_map.Domain.Order;
import com.example.examenpractic_map.Service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NotifyController {
    @FXML
    private Label message;
    @FXML
    private Button Accept;
    private Driver driver;
    private Order order;
    private OrderService orderService;
    private DriverController driverController;
    public void setDriver(Driver driver){
        this.driver=driver;
    }
    public void setOrder(Order order){
        this.order=order;
    }
    public void setOrderService(OrderService orderService){
        this.orderService=orderService;
        message.setText("New order: "+order.getPickupAddress()+ " ->" +order.getDestinationAddress());
        Accept.setOnAction(event -> {
            order.setDriverId(driver.getId());
            orderService.removeObserver(driverController);
            orderService.addDriver(order);
            driverController.updateModel();
            orderService.removeObserver(driverController);
        });
    }
    public void setDriverController(DriverController driverController){
        this.driverController=driverController;
    }
}
