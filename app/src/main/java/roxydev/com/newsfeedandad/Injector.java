package roxydev.com.newsfeedandad;

import android.arch.lifecycle.ViewModelProvider;
import roxydev.com.newsfeedandad.data.api.RedditClient;
import roxydev.com.newsfeedandad.data.api.RedditService;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetAfterInteractor;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetTopInteractor;
import roxydev.com.newsfeedandad.ui.newsfeed.viewmodel.NewsFeedViewModelFactory;

public class Injector {

    private static RedditService getRedditService(){
        return RedditClient.getApi();
    }

    public static ViewModelProvider.Factory getViewModelFactory(){
        return new NewsFeedViewModelFactory(getTopInteractor(), getAfterInteractor());
    }

    private static RedditGetTopInteractor getTopInteractor(){
        return  new RedditGetTopInteractor(getRedditService());
    }

    private static RedditGetAfterInteractor getAfterInteractor(){
        return  new RedditGetAfterInteractor(getRedditService());
    }

}
