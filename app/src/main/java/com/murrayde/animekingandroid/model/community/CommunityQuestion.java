package com.murrayde.animekingandroid.model.community;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

import com.murrayde.animekingandroid.model.question.Reviewable;

import java.util.ArrayList;

@Keep
public class CommunityQuestion extends Reviewable implements Parcelable {
    private String question;
    private String image_url;
    private String question_id;
    private Boolean user_correct_response;
    private ArrayList<String> multiple_choice;
    private int issue_count;
    private String anime_title;
    private String correct_response;

    protected CommunityQuestion(Parcel in) {
        question = in.readString();
        image_url = in.readString();
        question_id = in.readString();
        multiple_choice = in.createStringArrayList();
        issue_count = in.readInt();
        anime_title = in.readString();
        correct_response = in.readString();
    }

    public static final Creator<CommunityQuestion> CREATOR = new Creator<CommunityQuestion>() {
        @Override
        public CommunityQuestion createFromParcel(Parcel in) {
            return new CommunityQuestion(in);
        }

        @Override
        public CommunityQuestion[] newArray(int size) {
            return new CommunityQuestion[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public ArrayList<String> getMultiple_choice() {
        return multiple_choice;
    }

    public void setMultiple_choice(ArrayList<String> multiple_choice) {
        this.multiple_choice = multiple_choice;
    }

    public int getIssue_count() {
        return issue_count;
    }

    public void setIssue_count(int issue_count) {
        this.issue_count = issue_count;
    }

    public String getAnime_title() {
        return anime_title;
    }

    public void setAnime_title(String anime_title) {
        this.anime_title = anime_title;
    }

    public String getCorrectResponse() {
        return multiple_choice.get(0);
    }

    public Boolean getUser_correct_response() {
        return user_correct_response;
    }

    public void setUserCorrectResponse(Boolean status) {
        user_correct_response = status;
    }

    public CommunityQuestion() {
    }

    public CommunityQuestion(String question, String image_url, String question_id, ArrayList<String> multiple_choice, int issue_count, String anime_title, Boolean user_correct_response) {
        this.question = question;
        this.image_url = image_url;
        this.question_id = question_id;
        this.multiple_choice = multiple_choice;
        this.issue_count = issue_count;
        this.anime_title = anime_title;
        this.user_correct_response = user_correct_response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(question_id);
        parcel.writeString(correct_response);
        parcel.writeStringList(multiple_choice);
        parcel.writeBoolean(user_correct_response);
    }
}
