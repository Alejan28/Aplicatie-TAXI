package com.example.examenpractic_map.Utilis;

import com.example.examenpractic_map.Domain.Driver;

public interface Observer<E extends Event> {
    void update(E e);
    Driver getDriver();
}
