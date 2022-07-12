package com.example.overmindtest.entities;

public class Comment {

    private String title;
    private String author;
    private String rate;
    private String content;

    public Comment(){

    }

    public Comment(String title, String author, String rate, String content) {
        this.title = title;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
