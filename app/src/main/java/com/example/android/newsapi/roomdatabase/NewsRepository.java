package com.example.android.newsapi.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.apiservice.Retrofit2;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.Favorite;
import com.example.android.newsapi.model.Sources;
import com.example.android.newsapi.utils.AppExecutors;

import java.util.List;

public class NewsRepository {
    private static final Object LOCK = new Object();
    public static NewsRepository sInstance;

    private final Retrofit2 retrofit2;
    private final NewsDao mNewsDao;
    private final FavoritesDao mFavoritesDao;
    private final SourcesDao sourcesDao;
    private final AppExecutors mExecutor;
    private final MutableLiveData<List<Article>> networkArticleLiveData;
    private final MutableLiveData<List<Article>> networkArticleCategoryLiveData;
    private final MutableLiveData<List<Article>> networkArticleSearchLiveData;
    private final MutableLiveData<List<Sources>> networkSourcesLiveData;

    //https://developer.android.com/jetpack/guide

    // required private constructor for Singleton pattern
    public NewsRepository(Context context) {
        retrofit2 = Retrofit2.getInstance(context);
        mNewsDao = NewsRoomDatabase.getInstance(context).newsDao();
        mFavoritesDao = NewsRoomDatabase.getInstance(context).favoritesDao();
        sourcesDao = NewsRoomDatabase.getInstance(context).sourcesDao();
        mExecutor = AppExecutors.getInstance();
        networkArticleLiveData = new MutableLiveData<>();
        networkArticleCategoryLiveData = new MutableLiveData<>();
        networkArticleSearchLiveData = new MutableLiveData<>();
        networkSourcesLiveData = new MutableLiveData<>();

        networkArticleLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                if (articles != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mNewsDao.bulkInsert(articles);
                        }
                    });
                }
            }
        });

        networkArticleCategoryLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                if (articles != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mNewsDao.bulkInsert(articles);
                        }
                    });
                }
            }
        });

        networkArticleSearchLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                if (articles != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mNewsDao.bulkInsert(articles);
                        }
                    });
                }
            }
        });

        networkSourcesLiveData.observeForever(new Observer<List<Sources>>() {
            @Override
            public void onChanged(@Nullable final List<Sources> sources) {
                if (sources != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            sourcesDao.bulkInsert(sources);
                        }
                    });
                }
            }
        });
    }

    public synchronized static NewsRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository(context);
            }
        }
        return sInstance;
    }

    public LiveData<List<Article>> getTopHeadlines(final Utils utils) {
        final LiveData<List<Article>> networkData = retrofit2.getTopHeadlines(utils);
        networkData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    networkArticleLiveData.setValue(articles);
                    networkData.removeObserver(this);
                }
            }
        });
        return mNewsDao.getAllArticles();
    }

    public LiveData<List<Article>> getTopHeadlinesByCategory(final Utils utils) {
        final LiveData<List<Article>> networkData = retrofit2.getTopHeadlinesByCategory(utils);
        networkData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    networkArticleCategoryLiveData.setValue(articles);
                    networkData.removeObserver(this);
                }
            }
        });
        return mNewsDao.getArticleByCategory(utils.getCategory());
    }

    public LiveData<List<Article>> getEverything(final Utils utils) {
        final LiveData<List<Article>> networkData = retrofit2.getEverything(utils);
        networkData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    networkArticleSearchLiveData.setValue(articles);
                    networkData.removeObserver(this);
                }
            }
        });
        return mNewsDao.getEverything(utils.getKeyword());
    }

    public LiveData<List<Sources>> getSources() {
        final LiveData<List<Sources>> networkData = retrofit2.getSources();
        networkData.observeForever(new Observer<List<Sources>>() {
            @Override
            public void onChanged(@Nullable List<Sources> sources) {
                if (sources != null) {
                    networkSourcesLiveData.setValue(sources);
                    networkData.removeObserver(this);
                }
            }
        });
        return sourcesDao.getAllSources();
    }

    public LiveData<List<Article>> getSaved() {
        return mFavoritesDao.getAllSaved();
    }

    public LiveData<Boolean> isSaved(int articleKey) {
        return mFavoritesDao.isFavorite(articleKey);
    }

    //public void deleteFavorite(final int articleKey) {
        //mExecutor.getDiskIO().execute(new Runnable() {
            //@Override
            //public void run() {
                //mFavoritesDao.deleteFavorite(articleKey);
            //}
        //});
    //}

    //public void saveFavorite(final int articleKey) {
        //mExecutor.getDiskIO().execute(new Runnable() {
            //@Override
            //public void run() {
                //Favorite favorite = new Favorite(articleKey);
                //https://stackoverflow.com/questions/55912254/android-database-sqlite-sqliteconstraintexception-foreign-key-constraint-failed
                //mFavoritesDao.insert(favorite);
            //}
        //});
    //}

    //ASYNC Task
    //https://stackoverflow.com/questions/52542673/how-to-use-mvvm-with-asynctask-or-any-other-thing
    //https://medium.com/androiddevelopers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4
    //https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

    @SuppressLint("StaticFieldLeak")
    public void deleteFavorite(final int articleKey) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mFavoritesDao.deleteFavorite(articleKey);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void saveFavorite(final int articleKey) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Favorite favorite = new Favorite(articleKey);
                mFavoritesDao.insert(favorite);
                return null;
            }
        }.execute();
    }

}