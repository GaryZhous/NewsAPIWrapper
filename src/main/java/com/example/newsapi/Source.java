package com.example.newsapi;

public class Source {
    private String id;
    private String name;

    // Getters and Setters
    // Define getters and setters for all fields
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(String new_id){
        this.id = new_id;
    }

    public void setName(String new_name){
        this.name = new_name;
    }
}