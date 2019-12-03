
package com.murrayde.retrofittesting.model.AnimeAttributes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.CoverImage;
import com.murrayde.retrofittesting.model.AnimeAttributes.AnimeImage.PosterImage;

public class Attributes {

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getNextRelease() {
        return nextRelease;
    }

    public void setNextRelease(Object nextRelease) {
        this.nextRelease = nextRelease;
    }

    public int getPopularityRank() {
        return popularityRank;
    }

    public void setPopularityRank(int popularityRank) {
        this.popularityRank = popularityRank;
    }

    public int getRatingRank() {
        return ratingRank;
    }

    public void setRatingRank(int ratingRank) {
        this.ratingRank = ratingRank;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getAgeRatingGuide() {
        return ageRatingGuide;
    }

    public void setAgeRatingGuide(String ageRatingGuide) {
        this.ageRatingGuide = ageRatingGuide;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTba() {
        return tba;
    }

    public void setTba(String tba) {
        this.tba = tba;
    }

    public PosterImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(PosterImage posterImage) {
        this.posterImage = posterImage;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

}
