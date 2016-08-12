package ceasar.com.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Model for the content associated with item_list_content.xml
 */
public class ContentModel {
    private String title;
    private Drawable icon;

    public ContentModel(String title, Drawable icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public Drawable getIcon(){
        return icon;
    }

}
