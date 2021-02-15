package com.example.android.newsapi.roomdatabase;

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0
//https://github.com/googlecodelabs/android-room-with-a-view
//https://android.jlelse.eu/android-architecture-components-room-livedata-and-viewmodel-fca5da39e26b

//Android Developer Training - Room MVVM (Model View ViewModel - LiveData Repository)
//https://google-developer-training.github.io/android-developer-advanced-course-practicals/unit-6-working-with-architecture-components/lesson-14-room,-livedata,-viewmodel/14-1-a-room-livedata-viewmodel/14-1-a-room-livedata-viewmodel.html#task7intro

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.newsapi.model.Article;

import java.util.List;

@Dao
public interface NewsDao {

    //CRUD Method - Insert (create), query (read), update, delete

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<Article> articles);

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM articles WHERE category=:category ORDER BY publishedAt DESC")
    LiveData<List<Article>> getArticleByCategory(String category);

    @Query("SELECT * FROM articles WHERE keyword=:keyword ORDER by publishedAt DESC")
    LiveData<List<Article>> getEverything(String keyword);

}
