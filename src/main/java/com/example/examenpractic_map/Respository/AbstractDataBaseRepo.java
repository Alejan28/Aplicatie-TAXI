package com.example.examenpractic_map.Respository;

import com.example.examenpractic_map.Domain.Entity;

public abstract class AbstractDataBaseRepo <ID, E extends Entity<ID>> implements IRepository<ID,E>{
    protected String url;
    protected String username;
    protected String password;
    public AbstractDataBaseRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
