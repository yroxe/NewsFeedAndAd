package roxydev.com.newsfeedandad.domain.newsfeed;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import roxydev.com.newsfeedandad.data.api.RedditService;
import roxydev.com.newsfeedandad.domain.base.BaseDisposableInteractor;
import roxydev.com.newsfeedandad.domain.model.DataResult;
import roxydev.com.newsfeedandad.data.model.ListingResponse;
import roxydev.com.newsfeedandad.domain.model.LoadingState;

public class RedditGetTopInteractor extends BaseDisposableInteractor<ListingResponse>{

    private RedditService redditService;

    public RedditGetTopInteractor(RedditService redditService){
        this.redditService = redditService;
    }

    public LiveData<DataResult> execute(String subreddit,
                                        Integer pageSize, Scheduler scheduler){
        subscriptions.add(redditService.getTop(subreddit,pageSize)
                .subscribeOn(scheduler)
                .doOnSubscribe(disposable -> state.postValue(
                        new DataResult(null,null, LoadingState.LOADING)))

                .onErrorReturn(throwable -> new ListingResponse(null))
                .doOnError(throwable -> state.postValue(
                        new DataResult(null,"Error",LoadingState.ERROR)))
                .subscribe(listingResponse -> {
                    if(listingResponse.data!=null){
                        state.postValue(
                                new DataResult(listingResponse,null,LoadingState.SUCCESS));
                    } else {
                        state.postValue(
                                new DataResult(null,"Error",LoadingState.ERROR));
                    }
                })
        );


        return state;
    }


}
