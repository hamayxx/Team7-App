package com.example.team7_app.category;

public class Category {

    private String title;
    private int iconId;
    private String status;
    private int colorId;



    public Category(String title, int iconId, String status, int colorId) {
        this.title = title;
        this.iconId = iconId;
        this.status = status;
        this.colorId = colorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }


}