
package com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Meta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Size.Large;
import com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Size.Medium;
import com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Size.Small;
import com.murrayde.animeking.model.AnimeAttributes.AnimeImage.Size.Tiny;

public class Dimensions {

    @SerializedName("tiny")
    @Expose
    private Tiny tiny;
    @SerializedName("small")
    @Expose
    private Small small;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("large")
    @Expose
    private Large large;

    public Tiny getTiny() {
        return tiny;
    }

    public void setTiny(Tiny tiny) {
        this.tiny = tiny;
    }

    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

}
