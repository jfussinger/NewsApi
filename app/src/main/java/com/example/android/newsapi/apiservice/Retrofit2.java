package com.example.android.newsapi.apiservice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.ArticleResponse;
import com.example.android.newsapi.model.Sources;
import com.example.android.newsapi.model.SourcesResponse;
import com.example.android.newsapi.utils.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2 {

    private static final String TAG = "retrofit2";
    public static final String sortBy = "";
    public static final String apiKey = BuildConfig.NEWS_API_KEY;
    public static final String BASE_URL = "https://newsapi.org/v2/";

    //https://github.com/ashishbhandari/Homescreen-Widgets-Retrofit/blob/master/app/src/main/java/com/retrofitwithwidgets/web/QuoteAPIService.java

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //public static APIService apiService = retrofit.create(APIService.class);
    public static APIService apiService;
    private static final Object LOCK = new Object();
    private static Retrofit2 sInstance;
    private static final long cacheSize = 10 * 1024 * 1024; // 10 MB

    //https://square.github.io/okhttp/caching/
    //https://stackoverflow.com/questions/52916443/retrofit-okhttp-offline-caching-not-working
    //https://medium.com/@bapspatil/caching-with-retrofit-store-responses-offline-71439ed32fda

    public static Retrofit2 getInstance(Context context) {
        if (sInstance == null || apiService == null) {
            synchronized (LOCK) {
                // 10 MB of cache

                //https://stackoverflow.com/questions/49453564/how-to-cache-okhttp-response-from-web-server

                Cache cache = new Cache(context.getApplicationContext().getCacheDir(), cacheSize);

                //https://medium.com/@I_Love_Coding/how-does-okhttp-cache-works-851d37dd29cd

                // Used for cache connection
                Interceptor networkInterceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxAge(1, TimeUnit.HOURS)
                                .maxStale(1, TimeUnit.DAYS)
                                .build();

                        return chain.proceed(chain.request())
                                .newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", cacheControl.toString())
                                .build();
                    }
                };

                // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor

                HttpLoggingInterceptor loggingInterceptor =
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

                // https://square.github.io/okhttp/interceptors/

                //https://stackoverflow.com/questions/49453564/how-to-cache-okhttp-response-from-web-server
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addNetworkInterceptor(networkInterceptor)
                        .addInterceptor(loggingInterceptor)
                        .cache(cache)
                        .build();

                // Configure GSON
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .create();

                // Retrofit Builder
                Retrofit.Builder builder =
                        new Retrofit
                                .Builder()
                                .baseUrl(BASE_URL)
                                .client(okHttpClient)
                                .addConverterFactory(GsonConverterFactory.create(gson));

                apiService = builder.build().create(APIService.class);
                sInstance = new Retrofit2();
            }
        }
        return sInstance;
    }

    //Top Headlines

    public LiveData<List<Article>> getTopHeadlines(final Utils utils) {
        final MutableLiveData<List<Article>> networkArticleLiveData = new MutableLiveData<>();

        Call<ArticleResponse> callNews = apiService.getTopHeadlines(
                utils.getCountry(),
                apiKey
        );

        callNews.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull retrofit2.Response<ArticleResponse> response) {
                if (response.raw().cacheResponse() != null) {
                    Log.d(TAG,"Response from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Log.d(TAG,"Response from server");
                }
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    networkArticleLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {

            }
        });
        return networkArticleLiveData;
    }

    //Categories

    public LiveData<List<Article>> getTopHeadlinesByCategory(final Utils utils) {
        final MutableLiveData<List<Article>> networkArticleCategoryLiveData = new MutableLiveData<>();

        Call<ArticleResponse> callNews = apiService.getTopHeadlinesbyCategory(
                utils.getCountry(),
                utils.getCategory(),
                apiKey
        );

        callNews.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull retrofit2.Response<ArticleResponse> response) {
                if (response.raw().cacheResponse() != null) {
                    Log.d(TAG,"Response from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Log.d(TAG,"Response from server");
                }
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    for (Article article : articles) {
                        article.setCategory(utils.getCategory());
                    }
                    networkArticleCategoryLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {

            }
        });
        return networkArticleCategoryLiveData;
    }


    //Everything (search)

    public LiveData<List<Article>> getEverything(final Utils utils) {
        final MutableLiveData<List<Article>> networkArticleSearchLiveData = new MutableLiveData<>();

        Call<ArticleResponse> callNews = apiService.getEverything(
                utils.getKeyword(),
                //keyword,
                sortBy,
                apiKey
        );

        callNews.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull retrofit2.Response<ArticleResponse> response) {
                if (response.raw().cacheResponse() != null) {
                    Log.d(TAG,"Response from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Log.d(TAG,"Response from server");
                }
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    for (Article article : articles) {
                        article.setKeyword(utils.getKeyword());
                    }
                    networkArticleSearchLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {

            }
        });
        return networkArticleSearchLiveData;
    }

    //Sources

    public LiveData<List<Sources>> getSources() {
        final MutableLiveData<List<Sources>> networkSourcesLiveData = new MutableLiveData<>();

        Call<SourcesResponse> callSources = apiService.getSources(
                apiKey
        );

        callSources.enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SourcesResponse> call, @NonNull retrofit2.Response<SourcesResponse> response) {
                if (response.raw().cacheResponse() != null) {
                    Log.d(TAG,"Response from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Log.d(TAG,"Response from server");
                }
                if (response.body() != null) {
                    List<Sources> sources = response.body().getSources();
                    networkSourcesLiveData.setValue(sources);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SourcesResponse> call, @NonNull Throwable t) {

            }
        });
        return networkSourcesLiveData;
    }


}

