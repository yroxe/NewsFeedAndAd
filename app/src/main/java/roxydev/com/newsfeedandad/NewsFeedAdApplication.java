package roxydev.com.newsfeedandad;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class NewsFeedAdApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

}


