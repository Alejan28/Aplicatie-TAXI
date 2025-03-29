package com.example.examenpractic_map.Domain;

public class Entity <T>{
    T id;
    public void setId(T newId){
        id = newId;
    }
    public T getId(){
        return id;
    }
}
