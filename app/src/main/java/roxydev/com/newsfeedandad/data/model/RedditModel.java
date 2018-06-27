package roxydev.com.newsfeedandad.data.model;


import com.google.gson.annotations.SerializedName;

public class RedditModel {

    @SerializedName("name")
    public String name;
    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("thumbnail")
    public String thumbnail;
    @SerializedName("num_comments")
    public String comments;

}
