package com.example.mealmonkey.Data.Model;

public class ProfileImageDownloadUri {

    public String PosterPath;

    public ProfileImageDownloadUri(){

    }
    public ProfileImageDownloadUri(String posterPath) {
        PosterPath = posterPath;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }
}
