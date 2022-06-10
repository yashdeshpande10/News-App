package com.example.android.newsapp;

public class Articles {

    private String title;
    private String description;
    private String urlToImage;
    private String content;
    private String url;


    public Articles(String title, String description, String urlToImage, String content, String url) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.content = content;
        this.url = url;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
