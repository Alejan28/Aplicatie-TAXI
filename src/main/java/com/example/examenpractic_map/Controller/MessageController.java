package com.example.examenpractic_map.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.awt.*;

public class MessageController {
    @FXML
    private Label message;
    public void setMessage(String text){
        message.setText(text);
    }
    public void setTextColor(String color){
        message.setStyle("-fx-text-fill: "+color);
    }
    public void setTextFont(String font, Double size){
        if(font==null){
            font="Arial";
        }
        if(size==null){
            size=12.0;
        }
        message.setFont(new Font(font, size));
    }
}
