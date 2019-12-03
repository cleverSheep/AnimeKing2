
package com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Small {

    @SerializedName("width")
    @Expose
    private Object width;
    @SerializedName("height")
    @Expose
    private Object height;

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

}
