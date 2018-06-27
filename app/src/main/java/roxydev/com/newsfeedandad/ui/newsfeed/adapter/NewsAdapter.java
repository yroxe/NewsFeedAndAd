package roxydev.com.newsfeedandad.ui.newsfeed.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

import roxydev.com.newsfeedandad.R;
import roxydev.com.newsfeedandad.data.model.RedditModel;
import roxydev.com.newsfeedandad.ui.newsfeed.viewholder.FacebookAdViewHolder;
import roxydev.com.newsfeedandad.ui.newsfeed.viewholder.NewsViewHolder;

public class NewsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> newsList = new ArrayList<>();

    public NewsAdapter(){}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case R.layout.view_holder_reddit_item:
                return NewsViewHolder.create(parent);
            case R.layout.view_holder_facebook_ad:
                return FacebookAdViewHolder.create(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case R.layout.view_holder_reddit_item :
                ((NewsViewHolder) holder).bind((RedditModel) newsList.get(position));
                break;
            case R.layout.view_holder_facebook_ad:
              ((FacebookAdViewHolder) holder).bind((NativeAd) newsList.get(position));
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        if(newsList.get(position) instanceof RedditModel){
            return  R.layout.view_holder_reddit_item;
        } else if(newsList.get(position) instanceof NativeAd){
            return  R.layout.view_holder_facebook_ad;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void addToList(List<Object> itemList){
        newsList.addAll(itemList);
    }



}
