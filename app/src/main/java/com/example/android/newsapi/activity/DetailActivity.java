package com.example.android.newsapi.activity;

import androidx.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.newsapi.R;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.roomdatabase.NewsRepository;
import com.example.android.newsapi.roomdatabase.NewsViewModel;
import com.example.android.newsapi.utils.TruncateString;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    //https://firebase.google.com/docs/admob/android/quick-start?utm_campaign=admob_series_firebaseadmob_062217&utm_source=gdev&utm_medium=yt-desc
    //https://support.google.com/admob/answer/7356219?visit_id=636980404741681145-862799554&rd=1

    //https://github.com/googleads/googleads-mobile-android-examples/blob/master/java/admob/InterstitialExample/app/src/main/java/com/google/android/gms/example/interstitialexample/MyActivity.java
    //Interstitial Ad

    private Menu menu;

    private ImageView favoriteClick;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544~3347511713";;
    private InterstitialAd interstitialAd;

    private static final String TAG = "detailactivity";

    private Article Articles;

    private List<Article> articles = new ArrayList<Article>();

    private int position;

    private int articleKey;

    String articleUrlToImage, articleTitle, articleAuthor, articleId, articleName, articleDescription, articlePublishedAt,
            articleUrl, articleContent;

    ImageView urlToImage;
    TextView title;
    TextView author;
    TextView description;
    TextView publishedAt;
    TextView content;

    NewsViewModel viewModel;

    private NewsRepository newsRepository;

    private static final String PARAM_SAVED = "param-saved";

    private Boolean hasFavorite = false;

    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsRepository = NewsRepository.getInstance(this);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, AD_UNIT_ID);

        // Create the InterstitialAd and set the adUnitId.
        interstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        //https://developers.google.com/admob/android/interstitial?hl=en-US

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(DetailActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(DetailActivity.this,
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {

            }
        });

        //showInterstitial();

        newsRepository = NewsRepository.getInstance(this);

        Log.d(TAG, "onCreate Lifecycle invoked");

        setContentView(R.layout.activity_detail);

        mScrollView = (ScrollView) findViewById(R.id.scrollview_detail);

        urlToImage = (ImageView) findViewById(R.id.urlToImage);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        description = (TextView) findViewById(R.id.description);
        publishedAt = (TextView) findViewById(R.id.publishedAt);
        content = (TextView) findViewById(R.id.content);

        //http://www.developerphil.com/parcelable-vs-serializable/
        //https://guides.codepath.com/android/using-parcelable

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            Articles = getIntent().getParcelableExtra("articles");
            articleKey = bundle.getInt("articleKey");
            //position = bundle.getInt("position");
            articleUrlToImage = bundle.getString("urlToImage");
            articleTitle = bundle.getString("title");
            articleAuthor = bundle.getString("author");
            articleId = bundle.getString("id");
            articleName = bundle.getString("name");
            articleDescription = bundle.getString("description");
            articlePublishedAt = bundle.getString("publishedAt");
            articleUrl = bundle.getString("url");
            articleContent = bundle.getString("content");

            Log.d(TAG, "DetailActivity");
            Log.d(TAG, "ArticleKey:" + articleKey);
            //Log.d(TAG, "Position:" + position);
            Log.d(TAG, "UrlToImage:" + articleUrlToImage);
            Log.d(TAG, "Title:" + articleTitle);
            Log.d(TAG, "Author:" + articleAuthor);
            Log.d(TAG, "Id:" + articleId);
            Log.d(TAG, "Name:" + articleName);
            Log.d(TAG, "Description:" + articleDescription);
            Log.d(TAG, "PublishedAt:" + articlePublishedAt);
            Log.d(TAG, "Url:" + articleUrl);
            Log.d(TAG, "Content:" + articleContent);

            if(articleUrlToImage==null){
                urlToImage.setVisibility(View.GONE);
            }else{
                Glide.with(this)
                        .load(articleUrlToImage)
                        .into(urlToImage);
            }
            if(articleTitle==null){
                title.setVisibility(View.GONE);
            }else {
                title.setText(articleTitle);
            }
            if(articleAuthor==null){
                author.setVisibility(View.GONE);
            }else{
                author.setText(articleAuthor);
            }
            if(articleDescription==null){
                description.setVisibility(View.GONE);
            }else{
                description.setText(articleDescription);
            }
            if(articlePublishedAt==null){
                publishedAt.setVisibility(View.GONE);
            }else{
                publishedAt.setText((articlePublishedAt));
            }
            if(articleContent==null){
                content.setVisibility(View.GONE);
            }else{
                content.setText(TruncateString.truncateExtra(articleContent));
            }

            //https://stackoverflow.com/questions/18816418/how-do-i-change-the-color-of-actionbars-up-arrow
            //https://stackoverflow.com/questions/48720614/show-hide-back-button-in-toolbar

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
            }

        }
        //isSaved = getArguments().getBoolean(PARAM_SAVED);

        //https://stackoverflow.com/questions/4193167/change-source-image-for-image-view-when-pressed?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        //https://stackoverflow.com/questions/6083641/android-imageviews-onclicklistener-does-not-work?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

        //https://stackoverflow.com/questions/4193167/change-source-image-for-image-view-when-pressed?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        //https://stackoverflow.com/questions/6083641/android-imageviews-onclicklistener-does-not-work?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

        favoriteClick = (ImageView) findViewById(R.id.favorite);

        favoriteClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasFavorite) {
                    newsRepository.deleteFavorite(articleKey);
                    favoriteClick.setImageResource(R.drawable.baseline_turned_in_not_black_24dp);
                    hasFavorite = false;
                    Toast.makeText(getApplicationContext(), "Removed from Favorites:" + " " + articleTitle, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Removed from Favorites - Article Id:" + articleKey);
                    Log.d(TAG, "ArticleKey:" + articleKey);
                    Log.d(TAG, "UrlToImage:" + articleUrlToImage);
                    Log.d(TAG, "Title:" + articleTitle);
                    Log.d(TAG, "Author:" + articleAuthor);
                    Log.d(TAG, "Description:" + articleDescription);
                    Log.d(TAG, "PublishedAt:" + articlePublishedAt);
                    Log.d(TAG, "Content:" + articleContent);
                    Log.d(TAG, "Url:" + articleUrl);
                } else {
                    newsRepository.saveFavorite(articleKey);
                    favoriteClick.setImageResource(R.drawable.baseline_turned_in_black_24dp);
                    hasFavorite = true;
                    Toast.makeText(getApplicationContext(), "Added to Favorites:" + " " + articleTitle, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Added to Favorites - Article Id:" + articleKey);
                    Log.d(TAG, "ArticleKey:" + articleKey);
                    Log.d(TAG, "UrlToImage:" + articleUrlToImage);
                    Log.d(TAG, "Title:" + articleTitle);
                    Log.d(TAG, "Author:" + articleAuthor);
                    Log.d(TAG, "Description:" + articleDescription);
                    Log.d(TAG, "PublishedAt:" + articlePublishedAt);
                    Log.d(TAG, "Content:" + articleContent);
                    Log.d(TAG, "Url:" + articleUrl);
                }
            }
        });

        if(isSaved()){
            favoriteClick.setImageResource(R.drawable.baseline_turned_in_black_24dp);
        }else {
            favoriteClick.setImageResource(R.drawable.baseline_turned_in_not_black_24dp);
        }

        //https://github.com/udacity/ud851-Sunshine/blob/student/S12.04-Solution-ResourceQualifiers/app/src/main/java/com/example/android/sunshine/DetailActivity.java
        //https://github.com/codepath/android_guides/wiki/Sharing-Content-with-Intents
        //https://developer.android.com/reference/android/support/v7/widget/ShareActionProvider
        //https://discussions.udacity.com/t/share-icon-provider-icon-not-displaying-in-action-bar/34777/9
        //https://stackoverflow.com/questions/37734887/how-can-i-add-a-share-button-on-my-android-app
        //https://developer.android.com/training/sharing/send

        final ImageView shareIntent = (ImageView) findViewById(R.id.action_share);

        shareIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Share:" + articleUrl);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(Intent.createChooser(shareIntent, "Share using"));

                //then set the sharingIntent
                //mShareActionProvider.setShareIntent(shareIntent);
            }
        });

        final ImageView openInBrowserIntent = (ImageView) findViewById(R.id.action_open_in_browser);

        openInBrowserIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(articleUrl);

                // Create a new intent to view the news URI
                Intent openInBrowserIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(openInBrowserIntent);
            }
        });

    }

    public boolean isSaved() {
        if (articleTitle != null) {
            newsRepository.isSaved(articleKey).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean favoriteBoolean) {
                    if (favoriteBoolean != null) {
                        hasFavorite = favoriteBoolean;
                        if(hasFavorite){
                            favoriteClick.setImageResource(R.drawable.baseline_turned_in_black_24dp);
                            hasFavorite = true;
                        }
                    }
                }
            });
        }return hasFavorite;
    }

    @Override
    public void onBackPressed() {
        showInterstitial();
        super.onBackPressed();
    }

    private void showInterstitial() {
        // Show the ad if it's ready.
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "The interstitial wasn't loaded yet.");
        }
    }

    //https://developer.android.com/guide/components/activities/activity-lifecycle

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart Lifecycle invoked");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume Lifecycle invoked");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause Lifecycle invoked");
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        Log.d(TAG, "onStop Lifecycle invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy Lifecycle invoked");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("articles", Articles);
        //https://stackoverflow.com/questions/29208086/save-the-position-of-scrollview-when-the-orientation-changes

        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{ mScrollView.getScrollX(), mScrollView.getScrollY()});

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state

        if (savedInstanceState != null) {
            Articles = savedInstanceState.getParcelable("articles");

            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if(position != null)
                mScrollView.postDelayed(new Runnable() {
                    public void run() {
                        mScrollView.scrollTo(position[0], position[1]);}
                }, 100);
        }
    }

    //https://stackoverflow.com/questions/44428389/livedata-getvalue-returns-null-with-room/44471378#44471378
    //https://developer.android.com/topic/libraries/architecture/livedata
    //https://developer.android.com/topic/libraries/architecture/viewmodel
    //https://knowledge.udacity.com/questions/17743

}
