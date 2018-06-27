package roxydev.com.newsfeedandad.ui.newsfeed.viewmodel;


import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetAfterInteractor;
import roxydev.com.newsfeedandad.domain.newsfeed.RedditGetTopInteractor;
import roxydev.com.newsfeedandad.data.model.ListingResponse;


public class NewsFeedViewModel  extends ViewModel {

    private String redditName = null;
    private MutableLiveData<String> afterTerm = new MutableLiveData<>();

    private RedditGetTopInteractor redditGetTopInteractor;
    private RedditGetAfterInteractor redditGetAfterInteractor;

    public MediatorLiveData<List<Object>> posts;
    public MutableLiveData<Integer> itemNumber;

    public MutableLiveData<Boolean> loadingStateData = new MutableLiveData<>();
    public MutableLiveData<String> errorData = new MutableLiveData<>();

    private static int PAGE_SIZE =30;


    public NewsFeedViewModel( RedditGetTopInteractor redditGetTopInteractor, RedditGetAfterInteractor redditGetAfterInteractor){
        this.redditGetTopInteractor = redditGetTopInteractor;
        this.redditGetAfterInteractor = redditGetAfterInteractor;
    }


    public void setRedditName(String name) {
        redditName = name;
    }

    public void loadInitial(){

        if(posts==null){
            posts = new MediatorLiveData<>();
            itemNumber = new MediatorLiveData<>();
            addSourcesToPosts();
            redditGetTopInteractor.execute(redditName,PAGE_SIZE, Schedulers.io());
        } else {
            redditGetTopInteractor.execute(redditName,itemNumber.getValue(), Schedulers.io());
        }
    }

    public void loadAfterItems(){
        redditGetAfterInteractor.execute(redditName,afterTerm.getValue(),PAGE_SIZE,Schedulers.io());
    }

    private void addSourcesToPosts(){
        posts.addSource(redditGetTopInteractor.state, dataResult -> {
            if(dataResult!=null)
                switch (dataResult.getLoadingState()){
                    case SUCCESS:
                        ListingResponse payload = (ListingResponse) dataResult.getPayload();
                        posts.postValue(getPostObjectListFromPayload(payload));
                        afterTerm.postValue(payload.data.after);
                        Log.d("ViewModel","after value is:"+payload.data.after);
                        break;
                    case LOADING:
                        loadingStateData.postValue(true);
                        break;
                    case ERROR:
                        errorData.postValue(dataResult.getError());
                        break;
                }
        });

        posts.addSource(redditGetAfterInteractor.state,dataResult -> {
            if(dataResult!=null)
                switch (dataResult.getLoadingState()){
                    case SUCCESS:
                        ListingResponse payload = (ListingResponse) dataResult.getPayload();
                        posts.postValue(getPostObjectListFromPayload(payload));
                        afterTerm.postValue(payload.data.after);
                        break;
                    case LOADING:
                       //loading is dealt with differently in this case,no need to post
                        break;
                    case ERROR:
                        errorData.postValue(dataResult.getError());
                        break;
                }
        });
    }

    private List<Object> getPostObjectListFromPayload(ListingResponse payload){
        List<ListingResponse.ListingData.RedditChildrenResponse> list
                = payload.data.children;
        List<Object> modelList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            modelList.add(list.get(i).data);
        }
        return modelList;
    }

}
