package com.example.team7_app.item;

public class Item {

    private int iconId;
    private String name;
    private String info;

    public Item(int iconId, String name, String info) {
        this.iconId = iconId;
        this.name = name;
        this.info = info;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
