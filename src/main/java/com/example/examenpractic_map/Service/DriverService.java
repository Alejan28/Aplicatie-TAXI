package com.example.examenpractic_map.Service;

import com.example.examenpractic_map.Domain.Driver;
import com.example.examenpractic_map.Respository.DriverRepo;

public class DriverService {
    private DriverRepo driverRepo;
    public DriverService(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    public Iterable<Driver> findAll(){
        return driverRepo.findAll();
    }
}
