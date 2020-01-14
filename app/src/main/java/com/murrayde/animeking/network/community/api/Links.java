package com.murrayde.animeking.network.community.api;

import com.google.gson.annotations.SerializedName;

public class Links {
    @SerializedName("first")
    private String first;
    @SerializedName("next")
    private String next;
    @SerializedName("last")
    private String last;

    public String getFirst() {
        return first;
    }

    public String getNext() {
        return next;
    }

    public String getLast() {
        return last;
    }
}
