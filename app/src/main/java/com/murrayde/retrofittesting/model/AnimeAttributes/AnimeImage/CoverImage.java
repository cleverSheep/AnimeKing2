
package com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.Meta.Meta_;

public class CoverImage {

    @SerializedName("tiny")
    @Expose
    private String tiny;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("meta")
    @Expose
    private Meta_ meta;

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Meta_ getMeta() {
        return meta;
    }

    public void setMeta(Meta_ meta) {
        this.meta = meta;
    }

}
