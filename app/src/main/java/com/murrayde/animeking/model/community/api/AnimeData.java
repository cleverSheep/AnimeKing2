
package com.murrayde.animeking.model.community.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animeking.model.community.AnimeAttributes.Attributes;

public class AnimeData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}

