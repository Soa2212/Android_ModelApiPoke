package com.example.pokemonapirecycler.Api;
public class Pokemon {
    private String name;
    private String url;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        String[] urlParts = url.split("/");
        return urlParts[urlParts.length - 1];
    }

}
