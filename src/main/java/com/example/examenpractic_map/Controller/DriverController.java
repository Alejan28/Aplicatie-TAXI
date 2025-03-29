package com.example.examenpractic_map.Controller;

import com.example.examenpractic_map.Domain.Driver;
import com.example.examenpractic_map.Domain.Order;
import com.example.examenpractic_map.Service.OrderService;
import com.example.examenpractic_map.Utilis.AddOrderEvent;
import com.example.examenpractic_map.Utilis.Observer;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DriverController implements Observer<AddOrderEvent> {
    ObservableList<Order> model=javafx.collections.FXCollections.observableArrayList();
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, LocalDateTime> start;
    @FXML
    private TableColumn<Order, String > pickUp;
    @FXML
    private TableColumn<Order, String > destination;
    @FXML
    private TableColumn<Order, String > clientName;

    private Driver driver;
    private OrderService orderService;

    public void setController(Driver driver, OrderService orderService){
        this.driver=driver;
        this.orderService=orderService;
        initModel();
        tableView.setItems(model);
    }
    @FXML
    public void initialize() {
        start.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("startDate"));
        pickUp.setCellValueFactory(new PropertyValueFactory<Order, String>("pickupAddress"));
        destination.setCellValueFactory(new PropertyValueFactory<Order, String>("destinationAddress"));
        clientName.setCellValueFactory(new PropertyValueFactory<Order, String>("clientName"));
        TableColumn<Order, Void> actionColumn = new TableColumn<>("Finished");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Finished");

            {
                // Set button action
                button.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    order.setStatus(com.example.examenpractic_map.Domain.status.FINISHED);
                    orderService.updateOrder(order);
                    initModel();
                    getTableView().refresh();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        tableView.getColumns().add(actionColumn);
    }
    private void initModel() {
        Iterable<Order> messages = orderService.findAllForDriver(driver.getId());
        List<Order> orders = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());

        if(orders.isEmpty()){
            orderService.addObserver(this);
        }
        model.setAll(orders);

    }

    @Override
    public void update(AddOrderEvent addOrderEvent) {
            openNotifyController(addOrderEvent.getData());
    }

    @Override
    public Driver getDriver() {
        return this.driver;
    }
    public void openNotifyController(Order order){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/examenpractic_map/notify-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(driver.getName()+" NOTIFICARE");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            NotifyController notifyController = loader.getController();
            notifyController.setDriver(driver);
            notifyController.setOrder(order);
            notifyController.setOrderService(orderService);
            notifyController.setDriverController(this);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));

            dialogStage.show();
            pause.setOnFinished(event -> {
                // Close the window after 5 seconds
                //orderService.stopWaiting();
                dialogStage.close();
                System.out.println("Window closed!");
            });
            pause.play();

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
    public void updateModel(){
        initModel();
        tableView.refresh();
    }
}
