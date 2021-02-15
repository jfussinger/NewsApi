package com.example.android.newsapi.roomdatabase;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.model.Article;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

//ViewModel in MVVM (Model View ViewModel)

public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository newsRepository;

    // Using LiveData and caching what getAllArticles returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Article>> getTopHeadlines(Utils utils) {
        return newsRepository.getTopHeadlines(utils);
    }

    public LiveData<List<Article>> getTopHeadlinesByCategory(Utils utils) {
        return newsRepository.getTopHeadlinesByCategory(utils);
    }

    public LiveData<List<Article>> getEverything(Utils utils) {
        return newsRepository.getEverything(utils);
    }

    public LiveData<List<Article>> getAllSaved() {
        return newsRepository.getSaved();
    }

    public LiveData<Boolean> isSaved(int articleKey) {
        return newsRepository.isSaved(articleKey);
    }

    public void toggleSave(int articleKey) {
        newsRepository.deleteFavorite(articleKey);
    }
}


