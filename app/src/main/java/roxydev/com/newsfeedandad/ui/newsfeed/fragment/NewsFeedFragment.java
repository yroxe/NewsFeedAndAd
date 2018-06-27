package roxydev.com.newsfeedandad.ui.newsfeed.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import java.util.ArrayList;
import roxydev.com.newsfeedandad.Injector;
import roxydev.com.newsfeedandad.R;
import roxydev.com.newsfeedandad.ui.newsfeed.adapter.NewsAdapter;
import roxydev.com.newsfeedandad.ui.newsfeed.viewmodel.NewsFeedViewModel;

public class NewsFeedFragment  extends Fragment {
    private static String SUBREDDIT="Subreddit";
    private static String FALLBACK_FEED="androiddev";
    private NewsFeedViewModel viewModel;
    private View rootView;
    //small loading layout showing at the end of the recyclerview
    private LinearLayout loading;
    private boolean adLoaded=false;
    private NewsAdapter adapter = new NewsAdapter();
    private RecyclerView recyclerView;
    private LinearLayout errorLayout;
    //full screen loading layout showing before the first load
    private LinearLayout fullLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this, Injector.getViewModelFactory())
                .get(NewsFeedViewModel.class);
    }

    public static NewsFeedFragment newInstance(String subreddit){
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUBREDDIT,subreddit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_newsfeed,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= rootView.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        loading = rootView.findViewById(R.id.loadingView);
        errorLayout = rootView.findViewById(R.id.errorLayout);
        fullLoading = rootView.findViewById(R.id.fullLoading);

        if (getArguments() != null) {
            viewModel.setRedditName(getArguments().getString(SUBREDDIT));
        } else {
            viewModel.setRedditName(FALLBACK_FEED);
        }

        viewModel.loadInitial();

        viewModel.posts.observe(this, redditModels -> {
            manageLayoutPosts();
            adapter.addToList(redditModels);
            adapter.notifyDataSetChanged();
            loading.setVisibility(View.GONE);
            //maybe in the first load the ad is not loading for some reason so try again on the next posts batch load
            if(!adLoaded){
                 loadFacebookAd();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                viewModel.itemNumber.postValue(linearLayoutManager.getItemCount());
                if (linearLayoutManager.getItemCount() - 2 <=
                        linearLayoutManager.getChildCount() +
                                linearLayoutManager.findFirstVisibleItemPosition()) {
                    loading.setVisibility(View.VISIBLE);
                    viewModel.loadAfterItems();
                } else {
                    loading.setVisibility(View.GONE);
                }
            }
        });

        viewModel.errorData.observe(this, s -> manageLayoutError());
        viewModel.loadingStateData.observe(this, aBoolean -> manageLayoutLoading());

    }

    private void manageLayoutError(){
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        fullLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
    }

    private void manageLayoutLoading(){
        fullLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
    }

    private void manageLayoutPosts(){
        fullLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    private void loadFacebookAd(){
        AdSettings.addTestDevice("08ab4c1370e5c8685f6b1cb3a1bcc105");
        NativeAd nativeAd = new NativeAd(this.getContext(), "YOUR_PLACEMENT_ID");

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) { }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("AdError",adError.getErrorMessage());
               // Toast.makeText(getContext(),adError.getErrorMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(ad);
                adapter.addToList(list);
                adapter.notifyDataSetChanged();
                adLoaded = true;
            }

            @Override
            public void onAdClicked(Ad ad) { }

            @Override
            public void onLoggingImpression(Ad ad) { }
        });
        nativeAd.loadAd();
    }


}
