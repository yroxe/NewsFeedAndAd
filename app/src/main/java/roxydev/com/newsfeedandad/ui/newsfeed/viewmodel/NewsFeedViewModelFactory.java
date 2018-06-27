package roxydev.com.newsfeedandad.ui.newsfeed.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import roxydev.com.newsfeedandad.Injector;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetAfterInteractor;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetTopInteractor;

public class NewsFeedViewModelFactory implements ViewModelProvider.Factory {
    private RedditGetTopInteractor getTopInteractor;
    private RedditGetAfterInteractor getAfterInteractor;

    public NewsFeedViewModelFactory(RedditGetTopInteractor redditGetTopInteractor, RedditGetAfterInteractor redditGetAfterInteractor) {
    this.getAfterInteractor = redditGetAfterInteractor;
    this.getTopInteractor = redditGetTopInteractor;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsFeedViewModel(getTopInteractor,getAfterInteractor);
    }
}
