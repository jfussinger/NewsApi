package com.example.android.newsapi.model;

import androidx.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ArticleSource implements Parcelable {

    @ColumnInfo(name = "sourceId")
    @SerializedName("sourceId")
    public String sourceId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;

    public ArticleSource(@NonNull String sourceId, String name) {
        this.sourceId = sourceId;
        this.name = name;
    }

    public ArticleSource(){

    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sourceId);
        dest.writeString(this.name);
    }

    public ArticleSource(Parcel in) {
        this.sourceId = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ArticleSource> CREATOR = new Parcelable.Creator<ArticleSource>() {
        @Override
        public ArticleSource createFromParcel(Parcel source) {
            return new ArticleSource(source);
        }

        @Override
        public ArticleSource[] newArray(int size) {
            return new ArticleSource[size];
        }
    };
}


