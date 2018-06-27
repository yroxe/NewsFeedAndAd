package roxydev.com.newsfeedandad.ui.newsfeed.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import roxydev.com.newsfeedandad.R;
import roxydev.com.newsfeedandad.data.model.RedditModel;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public static NewsViewHolder create(ViewGroup parent){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_reddit_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    private NewsViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }

    public void bind(RedditModel model){
        title.setText(model.title);
    }

}
