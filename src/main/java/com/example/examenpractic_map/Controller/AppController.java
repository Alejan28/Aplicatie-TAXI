package com.example.examenpractic_map.Controller;

import com.example.examenpractic_map.Domain.Order;
import com.example.examenpractic_map.Domain.status;
import com.example.examenpractic_map.Service.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class AppController {
    @FXML
    private TextField plecare;
    @FXML
    private TextField destinatie;
    @FXML
    private TextField clientName;
    @FXML
    private Button addButton;
    @FXML
    private  ImageView imageView;
    private OrderService orderService;
    public void setController(OrderService orderService){
        this.orderService=orderService;
        addButton.setOnAction(event -> {
            if(plecare.getText()!=null && destinatie.getText()!=null && clientName.getText()!=null){
                Order order = new Order(null, status.PENDING, LocalDateTime.now(), plecare.getText(),destinatie.getText(),clientName.getText());
                try{
                    orderService.saveOrder(order);
                    openMessageWindow("SUCCESS", "S-A SALVAT");
                }catch(Exception e){
                    openMessageWindow("WARNING", "NU S-A PUTUT SALVA");
                }
            }
            else{
            openMessageWindow("WARNING", "INSUFICIENTE DATE");}
        });
    }
    public void setImageView(String image){
        Circle clip = new Circle(100, 100, 100);
        imageView.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/examenpractic_map/"+image)));
        imageView.setOnMouseClicked(event -> {
            openMessageWindow("Mesaj","Ai deschis o noua fereastra");
        });
        imageView.setClip(clip);
    }
    public void openMessageWindow(String headText, String message){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/examenpractic_map/hello-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(headText);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            MessageController messageController = loader.getController();
            messageController.setMessage(message);
            dialogStage.show();

        } catch ( IOException e) {
            e.printStackTrace();
        }

    }
}
