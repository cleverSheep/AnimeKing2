
package com.murrayde.retrofittesting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeComplete {

    @SerializedName("data")
    @Expose
    private List<AnimeData> data;

    public List<AnimeData> getData() {
        return data;
    }

    public void setData(List<AnimeData> data) {
        this.data = data;
    }

}
