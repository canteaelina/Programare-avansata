package org.example.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Item implements Serializable {
    private String id;
    private String title;
    private String location;
    private String year;
    private String author;
    private String description;

    private Map<String, Object> tags = new HashMap<>();

    public Item(String id, String title, String location, String year, String author, String description) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.year = year;
        this.author = author;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public void addTag(String key, Object obj) {
        tags.put(key, obj);
    }

}
