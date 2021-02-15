package com.example.android.newsapi.activity;

//https://developer.android.com/guide/topics/ui/layout/gridview.html
//https://github.com/Mayur-007/iSearch
//https://www.androidhive.info/2016/01/android-working-with-recycler-view/

//https://stackoverflow.com/questions/47393110/how-to-add-onclicklistener-for-grid-view-which-opens-new-activity

//https://www.androidhive.info/2015/04/android-getting-started-with-material-design/

import com.example.android.newsapi.ui.categories.CategoryFragment;
import com.example.android.newsapi.ui.saved.SavedFragment;
import com.example.android.newsapi.ui.search.SearchFragment;
import com.example.android.newsapi.ui.sources.SourcesFragment;
import com.example.android.newsapi.ui.topheadlines.TopHeadlinesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MenuItem;

import com.example.android.newsapi.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainactivity";

    FragmentManager fragmentManager = getSupportFragmentManager();

    TopHeadlinesFragment topHeadlinesFragment;
    CategoryFragment categoryFragment;
    SearchFragment searchFragment;
    SourcesFragment sourcesFragment;
    SavedFragment savedFragment;

    //https://firebase.google.com/docs/analytics/get-started?platform=android

    private FirebaseAnalytics mFirebaseAnalytics;

    //https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation View
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            //Default fragment
            if (topHeadlinesFragment == null) {
                topHeadlinesFragment = TopHeadlinesFragment.newInstance();
            }
            topHeadlinesFragment = new TopHeadlinesFragment();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_containter, topHeadlinesFragment).commit();
        }

        //https://stackoverflow.com/questions/22557780/first-fragment-to-be-added-to-the-main-activity-when-application-starts-up
        //https://github.com/codepath/android-fragment-basics/blob/master/app/src/main/java/com/codepath/mypizza/MainActivity.java
        //https://stackoverflow.com/questions/13305861/fool-proof-way-to-handle-fragment-on-orientation-change

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    //https://stackoverflow.com/questions/37043812/navigation-drawer-changes-in-rotation
    //https://guides.codepath.com/android/Handling-Configuration-Changes#saving-and-restoring-fragment-state
    //https://guides.codepath.com/android/fragment-navigation-drawer

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_topHeadlines:
                    Log.i(TAG, "topHeadlines selected");
                    if (topHeadlinesFragment == null) {
                        topHeadlinesFragment = TopHeadlinesFragment.newInstance();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_containter, topHeadlinesFragment)
                            .commit();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.topHeadlines));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return true;
                case R.id.navigation_categories:
                    Log.i(TAG, "categories selected");
                    if (categoryFragment == null) {
                        categoryFragment = CategoryFragment.newInstance();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_containter, categoryFragment)
                            .commit();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.categories));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return true;
                case R.id.navigation_search:
                    Log.i(TAG, "search selected");
                    if (searchFragment == null) {
                        searchFragment = SearchFragment.newInstance();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_containter, searchFragment)
                            .commit();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.action_search));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return true;
                case R.id.navigation_saved:
                    Log.i(TAG, "saved selected");
                    if (savedFragment == null) {
                        savedFragment = SavedFragment.newInstance();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_containter, savedFragment)
                            .commit();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.saved));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return true;
                case R.id.navigation_sources:
                    Log.i(TAG, "sources selected");
                    if (sourcesFragment == null) {
                        sourcesFragment = SourcesFragment.newInstance();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_containter, sourcesFragment)
                            .commit();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.sources));
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return true;
            }
            return true;
        }
    };

}

