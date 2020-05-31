
package com.murrayde.animekingmobile.network.community.api;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class AnimeComplete {

    @SerializedName("data")
    @Expose
    private List<AnimeData> data;
    @SerializedName("links")
    @Expose
    private Links links;

    private Links getLinks() {
        return links;
    }

    public List<AnimeData> getData() {
        return data;
    }

    public String getFirstLink() {
        return getLinks().getFirst();
    }

    public String getNextLink() {
        return getLinks().getNext();
    }

    public String getLastLink() {
        return getLinks().getLast();
    }
}
