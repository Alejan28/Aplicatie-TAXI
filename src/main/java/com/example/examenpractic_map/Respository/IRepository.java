package com.example.examenpractic_map.Respository;

import com.example.examenpractic_map.Domain.Entity;

public interface IRepository<ID,E extends Entity<ID>> {
    Iterable<E> findAll();
}
