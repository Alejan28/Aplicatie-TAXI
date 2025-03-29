package com.example.examenpractic_map.Utilis;

import com.example.examenpractic_map.Domain.Order;

public class AddOrderEvent implements Event{
    Order data;
    public AddOrderEvent(Order data){
        this.data = data;
    }
    public Order getData() {
        return data;
    }
}
