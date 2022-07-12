package com.example.overmindtest.entities;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private Integer id;
    private String name;
    private Double rate;
    private String director;
    private String url;

    private List<Comment> comment;

    private List<String> cast = new ArrayList<>();

    public Movie(){
    }

    public Movie(Integer id, String name, Double rate, String director, String url) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.director = director;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<String> getCast() {
        return cast;
    }
    public void setCast(List<String> cast) {
        this.cast = cast;
    }

}
