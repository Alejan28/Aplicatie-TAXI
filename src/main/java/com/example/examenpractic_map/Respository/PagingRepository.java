package com.example.examenpractic_map.Respository;

import com.example.examenpractic_map.Domain.Entity;
import com.example.examenpractic_map.Paging.Page;
import com.example.examenpractic_map.Paging.Pageable;

public interface PagingRepository<ID , E extends Entity<ID>> extends IRepository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);
}
