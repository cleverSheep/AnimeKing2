package com.murrayde.animeking.model;

import java.util.ArrayList;

public class Question {
    private String question;
    private String image_url;
    private String question_id;
    private ArrayList<String> multiple_choice;
    private int issue_count;
    private String anime_title;

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

    public Question() {
    }

    public Question(String question, String image_url, String question_id, ArrayList<String> multiple_choice, int issue_count, String anime_title) {
        this.question = question;
        this.image_url = image_url;
        this.question_id = question_id;
        this.multiple_choice = multiple_choice;
        this.issue_count = issue_count;
        this.anime_title = anime_title;
    }
}
