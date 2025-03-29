package com.example.examenpractic_map;

import com.example.examenpractic_map.Controller.AppController;
import com.example.examenpractic_map.Controller.DriverController;
import com.example.examenpractic_map.Controller.MessageController;
import com.example.examenpractic_map.Domain.Driver;
import com.example.examenpractic_map.Respository.DriverRepo;
import com.example.examenpractic_map.Respository.OrderRepo;
import com.example.examenpractic_map.Service.DriverService;
import com.example.examenpractic_map.Service.OrderService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("app-view.fxml"));
        String username="postgres";
        String pasword="Aurelia%72";
        String url="jdbc:postgresql://localhost:5432/ExamenPractic";
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        AppController appController = fxmlLoader.getController();
        DriverRepo driverRepo = new DriverRepo(url,username,pasword);
        OrderRepo orderRepo = new OrderRepo(url,username,pasword);
        DriverService driverService = new DriverService(driverRepo);
        OrderService orderService = new OrderService(orderRepo);
        appController.setController(orderService);
        for(Driver driver:driverService.findAll()){
            FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("driver-view.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load());
            DriverController driverController= fxmlLoader1.getController();
            driverController.setController(driver,orderService);
            //orderService.addObserver(driverController);
            Stage stage1 = new Stage();
            stage1.setTitle(driver.getName());
            stage1.setScene(scene1);
            stage1.show();
        }
        //appController.setImageView("blue-app-logo-design.jpg");
        stage.setTitle("Dispecerat!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}