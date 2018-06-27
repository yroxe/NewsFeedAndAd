package roxydev.com.newsfeedandad.ui.newsfeed.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

import roxydev.com.newsfeedandad.R;

public class FacebookAdViewHolder extends RecyclerView.ViewHolder{

    private View view;
    private LinearLayout adChoicesContainer;
    private AdIconView nativeAdIcon;
    private TextView nativeAdTitle;
    private MediaView nativeAdMedia;
    private TextView nativeAdSocialContext;
    private TextView nativeAdBody;
    private TextView sponsoredLabel;
    private Button nativeAdCallToAction;
    private Context context;

    public static FacebookAdViewHolder create(ViewGroup parent){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_facebook_ad, parent, false);
        return new FacebookAdViewHolder(itemView);
    }

    private FacebookAdViewHolder(View itemView){
        super(itemView);
        this.context = itemView.getContext();

        view = itemView;
        adChoicesContainer =  view.findViewById(R.id.ad_choices_container);
         nativeAdIcon = view.findViewById(R.id.native_ad_icon);
         nativeAdTitle = view.findViewById(R.id.native_ad_title);
         nativeAdMedia = view.findViewById(R.id.native_ad_media);
         nativeAdSocialContext = view.findViewById(R.id.native_ad_social_context);
         nativeAdBody = view.findViewById(R.id.native_ad_body);
         sponsoredLabel = view.findViewById(R.id.native_ad_sponsored_label);
         nativeAdCallToAction = view.findViewById(R.id.native_ad_call_to_action);

    }


    public void bind(NativeAd model){
        AdChoicesView adChoicesView = new AdChoicesView(context, model, true);
        adChoicesContainer.addView(adChoicesView, 0);

        nativeAdTitle.setText(model.getAdvertiserName());
        nativeAdBody.setText(model.getAdBodyText());
        nativeAdSocialContext.setText(model.getAdSocialContext());
        nativeAdCallToAction.setVisibility(model.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(model.getAdCallToAction());
        sponsoredLabel.setText(model.getSponsoredTranslation());

        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        model.registerViewForInteraction(
                view,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);


    }


}
