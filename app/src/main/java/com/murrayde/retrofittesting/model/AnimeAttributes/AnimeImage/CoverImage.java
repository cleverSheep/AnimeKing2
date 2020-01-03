
package com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.Meta.Meta_;

public class CoverImage implements Parcelable {

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

    protected CoverImage(Parcel in) {
        tiny = in.readString();
        small = in.readString();
        large = in.readString();
        original = in.readString();
    }

    public static final Creator<CoverImage> CREATOR = new Creator<CoverImage>() {
        @Override
        public CoverImage createFromParcel(Parcel in) {
            return new CoverImage(in);
        }

        @Override
        public CoverImage[] newArray(int size) {
            return new CoverImage[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(original);
    }
}
