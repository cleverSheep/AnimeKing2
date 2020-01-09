
package com.murrayde.animeking.model.AnimeAttributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Titles {

    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("en_jp")
    @Expose
    private String enJp;
    @SerializedName("ja_jp")
    @Expose
    private String jaJp;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getEnJp() {
        return enJp;
    }

    public void setEnJp(String enJp) {
        this.enJp = enJp;
    }

    public String getJaJp() {
        return jaJp;
    }

    public void setJaJp(String jaJp) {
        this.jaJp = jaJp;
    }

}
