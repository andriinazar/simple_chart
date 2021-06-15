package com.andrii.nazar.simplechart.models;

/**
 * Created by Andrii on 14.11.2015.
 */
public class DrawerMainItem {
    private String mIcon;
    private String mName;

    public DrawerMainItem(String mIcon, String mName) {
        this.mIcon = mIcon;
        this.mName = mName;
    }



    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }
}
