package com.example.examenpractic_map.Service;

import com.example.examenpractic_map.Domain.Order;
import com.example.examenpractic_map.Respository.OrderRepo;
import com.example.examenpractic_map.Utilis.AddOrderEvent;
import com.example.examenpractic_map.Utilis.Observable;
import com.example.examenpractic_map.Utilis.Observer;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderService implements Observable<AddOrderEvent> {
    List<Observer> observers = new ArrayList<>();
    private OrderRepo orderRepo;
    private Integer isAccepted;
    private Integer wait;
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public Iterable<Order> findAllForDriver(Integer id){
        return orderRepo.findAllInProgressForDriver(id);
    }
    public void updateOrder(Order order){
        orderRepo.updateOrder(order);
    }
    public void saveOrder(Order order){
        orderRepo.save(order);
        notifyObservers(new AddOrderEvent(order));
    }

    @Override
    public void addObserver(Observer<AddOrderEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<AddOrderEvent> e) {
        observers.remove(e);
        isAccepted=0;
    }
    public Optional<Order> findLatest(Integer id){
        return orderRepo.findLatestForDriver(id);
    }

    @Override
    public void notifyObservers(AddOrderEvent t) {
        isAccepted=1;
        //Integer index=0;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        final int[] index = {0}; // Maintain the current observer index

        scheduler.scheduleAtFixedRate(() -> {
            if (index[0] >= observers.size() || isAccepted != 1) {
                scheduler.shutdown();
                return;
            }

            Observer currentObserver = observers.get(index[0]);
            Platform.runLater(() -> {
                currentObserver.update(t); // Ensure UI updates happen on JavaFX thread
            });

            index[0]++;
        }, 0, 5, TimeUnit.SECONDS);
    }
    private CompletableFuture<Void> delay(int seconds) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    public void addDriver(Order order){
        orderRepo.addDriver(order);
    }
}
