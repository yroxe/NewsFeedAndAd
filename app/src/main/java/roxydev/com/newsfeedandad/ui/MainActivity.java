package roxydev.com.newsfeedandad.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import roxydev.com.newsfeedandad.R;
import roxydev.com.newsfeedandad.ui.adapter.ViewPagerAdapter;
import roxydev.com.newsfeedandad.ui.newsfeed.fragment.NewsFeedFragment;
import roxydev.com.newsfeedandad.ui.views.CustomViewPager;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private static String ANDROID_FEED="androiddev";
    private static String FACEBOOK_FEED="facebook";
    private static String SURF_FEED="surfing";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_android:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_facebook:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_surfing:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportActionBar();

        viewPager = findViewById(R.id.viewpager);
         viewPager.setOffscreenPageLimit(3);

        setupViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(NewsFeedFragment.newInstance(ANDROID_FEED));
        adapter.addFragment(NewsFeedFragment.newInstance(FACEBOOK_FEED));
        adapter.addFragment(NewsFeedFragment.newInstance(SURF_FEED));
        viewPager.setAdapter(adapter);

    }

}
