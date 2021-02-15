package com.example.android.newsapi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(foreignKeys = @ForeignKey(
        entity = Article.class,
        parentColumns = "articleKey",
        childColumns = "favoriteKey"),
        indices = {@Index(value = "favoriteKey", unique = true)},
        tableName = "saved"
)
public class Favorite {
    @ColumnInfo(name = "favoriteKey")
    private final int favoriteKey;
    @PrimaryKey
    @ColumnInfo(name = "time_saved")
    private Timestamp timestamp;

    public Favorite(int favoriteKey) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.favoriteKey = favoriteKey;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getFavoriteKey() {
        return favoriteKey;
    }
}

