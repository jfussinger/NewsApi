package com.example.android.newsapi.ui.sources;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.newsapi.model.Sources;
import com.example.android.newsapi.roomdatabase.NewsRepository;

import java.util.List;

public class SourcesViewModel extends AndroidViewModel {
    public final NewsRepository newsRepository;

    public SourcesViewModel(@NonNull Application application) {
        super(application);
        this.newsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Sources>> getSource() {
        return newsRepository.getSources();
    }
}
