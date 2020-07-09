package com.murrayde.animekingmobile.network.community.api_models;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
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
