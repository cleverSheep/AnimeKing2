
package com.murrayde.animeking.model.AnimeAttributes.AnimeImage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Meta.Meta;

public class PosterImage implements Parcelable {

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

    protected PosterImage(Parcel in) {
        tiny = in.readString();
        small = in.readString();
        medium = in.readString();
        large = in.readString();
        original = in.readString();
    }

    public static final Creator<PosterImage> CREATOR = new Creator<PosterImage>() {
        @Override
        public PosterImage createFromParcel(Parcel in) {
            return new PosterImage(in);
        }

        @Override
        public PosterImage[] newArray(int size) {
            return new PosterImage[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(original);
    }
}
