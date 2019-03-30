package com.example.schoolcom.Models.news;

/*
*Created by prakash
 */

public class DataClass {
    private String title;
   private String description;
    private String url ;
    private String urltoImage;
    private String updates;

    public DataClass(String title, String description, String url, String urltoImage, String updates)
    {
        this.title=title;
        this.description=description;
        this.url=url;
        this.urltoImage=urltoImage;
        this.updates=updates;



    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
    public String getUrltoImage()
    {
        return urltoImage;
    }
    public String getUpdates()
    {
        return updates;
    }


}

