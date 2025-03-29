package com.example.examenpractic_map.Domain;

public class Driver extends Entity<Integer>{
    String name;
    public Driver(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }
}
