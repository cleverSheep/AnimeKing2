
package com.murrayde.animekingtrivia.model.community.AnimeAttributes.AnimeImage.Meta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.murrayde.animekingtrivia.model.community.AnimeAttributes.AnimeImage.Size.Large_;
import com.murrayde.animekingtrivia.model.community.AnimeAttributes.AnimeImage.Size.Small_;
import com.murrayde.animekingtrivia.model.community.AnimeAttributes.AnimeImage.Size.Tiny_;

public class Dimensions_ {

    @SerializedName("tiny")
    @Expose
    private Tiny_ tiny;
    @SerializedName("small")
    @Expose
    private Small_ small;
    @SerializedName("large")
    @Expose
    private Large_ large;

    public Tiny_ getTiny() {
        return tiny;
    }

    public void setTiny(Tiny_ tiny) {
        this.tiny = tiny;
    }

    public Small_ getSmall() {
        return small;
    }

    public void setSmall(Small_ small) {
        this.small = small;
    }

    public Large_ getLarge() {
        return large;
    }

    public void setLarge(Large_ large) {
        this.large = large;
    }

}
