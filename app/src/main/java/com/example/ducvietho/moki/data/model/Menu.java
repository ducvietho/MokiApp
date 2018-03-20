package com.example.ducvietho.moki.data.model;

/**
 * Created by ducvietho.
 */

public class Menu {

    private boolean isSelect;
    private String title;
    private int icon;

    public boolean isSelect() {
        return isSelect;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public void setSelect(boolean select){
        this.isSelect = select;
    }

    public Menu( String title, int icon) {
        this.title = title;
        this.icon = icon;
    }
}
