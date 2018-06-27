package roxydev.com.newsfeedandad.data.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import roxydev.com.newsfeedandad.data.model.ListingResponse;

public interface RedditService {

    @GET("/r/{subreddit}/hot.json")
    public Single<ListingResponse> getTopAfter(
            @Path("subreddit") String subreddit,
            @Query("after") String after,
            @Query("limit") Integer limit);

    @GET("/r/{subreddit}/hot.json")
    public Single<ListingResponse> getTop(
            @Path("subreddit") String subreddit,
            @Query("limit") Integer limit
    );

}
