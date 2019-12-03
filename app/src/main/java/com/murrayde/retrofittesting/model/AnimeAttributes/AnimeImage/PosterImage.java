
package com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.Meta.Meta;

public class PosterImage {

    @SerializedName("tiny")
    @Expose
    private String tiny;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("large")
    @Expose
    private String large;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("meta")
    @Expose
    private Meta meta;

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

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
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

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
