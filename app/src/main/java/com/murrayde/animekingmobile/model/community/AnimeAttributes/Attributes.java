
package com.murrayde.animekingmobile.model.community.AnimeAttributes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animekingmobile.model.community.AnimeAttributes.AnimeImage.CoverImage;
import com.murrayde.animekingmobile.model.community.AnimeAttributes.AnimeImage.PosterImage;

import java.util.List;

public class Attributes implements Parcelable {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("coverImageTopOffset")
    @Expose
    private int coverImageTopOffset;
    @SerializedName("titles")
    @Expose
    private Titles titles;
    @SerializedName("canonicalTitle")
    @Expose
    private String canonicalTitle;
    @SerializedName("abbreviatedTitles")
    @Expose
    private List<String> abbreviatedTitles = null;
    @SerializedName("averageRating")
    @Expose
    private String averageRating;
    @SerializedName("ratingFrequencies")
    @Expose
    private RatingFrequencies ratingFrequencies;
    @SerializedName("userCount")
    @Expose
    private int userCount;
    @SerializedName("favoritesCount")
    @Expose
    private int favoritesCount;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("nextRelease")
    @Expose
    private Object nextRelease;
    @SerializedName("popularityRank")
    @Expose
    private int popularityRank;
    @SerializedName("ratingRank")
    @Expose
    private int ratingRank;
    @SerializedName("ageRating")
    @Expose
    private String ageRating;
    @SerializedName("ageRatingGuide")
    @Expose
    private String ageRatingGuide;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tba")
    @Expose
    private String tba;
    @SerializedName("posterImage")
    @Expose
    private PosterImage posterImage;
    @SerializedName("coverImage")
    @Expose
    private CoverImage coverImage;
    @SerializedName("episodeCount")
    @Expose
    private int episodeCount;
    @SerializedName("episodeLength")
    @Expose
    private int episodeLength;
    @SerializedName("totalLength")
    @Expose
    private int totalLength;
    @SerializedName("youtubeVideoId")
    @Expose
    private String youtubeVideoId;
    @SerializedName("showType")
    @Expose
    private String showType;
    @SerializedName("nsfw")
    @Expose
    private boolean nsfw;

    protected Attributes(Parcel in) {
        createdAt = in.readString();
        updatedAt = in.readString();
        slug = in.readString();
        synopsis = in.readString();
        coverImageTopOffset = in.readInt();
        canonicalTitle = in.readString();
        abbreviatedTitles = in.createStringArrayList();
        averageRating = in.readString();
        userCount = in.readInt();
        favoritesCount = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        popularityRank = in.readInt();
        ratingRank = in.readInt();
        ageRating = in.readString();
        ageRatingGuide = in.readString();
        subtype = in.readString();
        status = in.readString();
        tba = in.readString();
        posterImage = in.readParcelable(PosterImage.class.getClassLoader());
        episodeCount = in.readInt();
        episodeLength = in.readInt();
        totalLength = in.readInt();
        youtubeVideoId = in.readString();
        showType = in.readString();
        nsfw = in.readByte() != 0;
    }

    public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
        @Override
        public Attributes createFromParcel(Parcel in) {
            return new Attributes(in);
        }

        @Override
        public Attributes[] newArray(int size) {
            return new Attributes[size];
        }
    };

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public int getCoverImageTopOffset() {
        return coverImageTopOffset;
    }

    public void setCoverImageTopOffset(int coverImageTopOffset) {
        this.coverImageTopOffset = coverImageTopOffset;
    }

    public Titles getTitles() {
        return titles;
    }

    public void setTitles(Titles titles) {
        this.titles = titles;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public List<String> getAbbreviatedTitles() {
        return abbreviatedTitles;
    }

    public void setAbbreviatedTitles(List<String> abbreviatedTitles) {
        this.abbreviatedTitles = abbreviatedTitles;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public RatingFrequencies getRatingFrequencies() {
        return ratingFrequencies;
    }

    public void setRatingFrequencies(RatingFrequencies ratingFrequencies) {
        this.ratingFrequencies = ratingFrequencies;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Object getNextRelease() {
        return nextRelease;
    }

    public int getPopularityRank() {
        return popularityRank;
    }

    public int getRatingRank() {
        return ratingRank;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getAgeRatingGuide() {
        return ageRatingGuide;
    }

    public PosterImage getPosterImage() {
        return posterImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(slug);
        parcel.writeParcelable(posterImage, i);
        parcel.writeString(synopsis);
    }
}


