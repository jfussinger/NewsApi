package com.example.android.newsapi.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.R;
import com.example.android.newsapi.apiservice.APIService;
import com.example.android.newsapi.apiservice.Retrofit2;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.ArticleResponse;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ArticlesRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
class ArticlesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private AppWidgetManager mAppWidgetManager;
    private ComponentName appWidget;

    private static final String TAG = ArticlesWidgetService.class.getSimpleName();
    public static final String apiKey = BuildConfig.NEWS_API_KEY;

    private List<Article> articles;
    private int mCurrentWidgetId;
    private Context mContext;

    private int mAppWidgetId;
    public ArticlesRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data articleSource. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.

        onLoadArticles();

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the StackWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onLoadArticles() {

        //https://github.com/ashishbhandari/Homescreen-Widgets-Retrofit/blob/master/app/src/main/java/com/retrofitwithwidgets/provider/MyWidgetProvider.java

        APIService apiService = Retrofit2.retrofit.create(APIService.class);
        Call<ArticleResponse> callNews;

        String country = Locale.getDefault().getCountry().toLowerCase();
        String category = "General";

        callNews = apiService.getTopHeadlines(country, apiKey);
        callNews.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles = response.body().getArticles();

                }
            }
            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                try {
                    Thread.sleep(3000);
                    mAppWidgetManager = AppWidgetManager.getInstance(mContext);
                    appWidget = new ComponentName(mContext.getPackageName(), ArticlesWidgetService.class.getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, t.toString());
            }

        });
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data articleSource,
        // eg. cursors, connections, etc.
        articles.clear();;
    }
    public int getCount() {
        if (articles != null) {
            return articles.size();
        } else {
            return 0;
        }
    }
    public RemoteViews getViewAt(final int position) {
        // position will always range from 0 to getCount() - 1.
        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        //https://stackoverflow.com/questions/47993270/widget-load-image-from-url-into-remote-view

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(articles.get(position).getUrlToImage())
                    .apply(RequestOptions.fitCenterTransform())
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .submit(250, 180)
                    .get();

            rv.setImageViewBitmap(R.id.urlToImage, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rv.setTextViewText(R.id.title, articles.get(position).getTitle());
        //rv.setTextViewText(R.id.name, articles.get(position).getArticleSource().getName());
        //rv.setTextViewText(R.id.description, articlesList.get(position).getDescription());
        rv.setTextViewText(R.id.publishedAt, articles.get(position).getPublishedAt());

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        extras.putInt(ArticlesWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        //rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Return the remote views object.
        return rv;
    }
    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }
    public int getViewTypeCount() {
        return 1;
    }
    public long getItemId(int position) {
        return position;
    }
    public boolean hasStableIds() {
        return true;
    }
    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}
