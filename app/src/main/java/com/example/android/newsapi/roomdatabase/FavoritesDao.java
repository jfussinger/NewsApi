package com.example.android.newsapi.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.Favorite;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    //@Query("DELETE FROM saved")
    //void deleteAll();

    //https://stackoverflow.com/questions/52234985/android-check-if-object-is-present-in-database-using-room-and-rxjava

    @Query("SELECT COUNT (*) FROM saved WHERE favoriteKey = :articleKey")
        //@Query("SELECT COUNT (*) FROM saved WHERE favoriteKey = :articleKey Limit 1")
    LiveData<Boolean> isFavorite(int articleKey);

    @Query("DELETE FROM saved WHERE favoriteKey=:articleKey")
    void deleteFavorite(int articleKey);

    @Query("SELECT articles.* FROM articles, saved " +
            "WHERE articles.articleKey == saved.favoriteKey " +
            "ORDER BY saved.time_saved")
    LiveData<List<Article>> getAllSaved();

}
