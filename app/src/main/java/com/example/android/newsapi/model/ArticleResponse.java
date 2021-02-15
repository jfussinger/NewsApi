package com.example.android.newsapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse implements Parcelable {

    @SerializedName("status")
    public String status;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("articles")
    public List<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.totalResults);
        dest.writeTypedList(this.articles);
    }

    public ArticleResponse() {
    }

    public ArticleResponse(Parcel in) {
        this.status = in.readString();
        this.totalResults = in.readInt();
        this.articles = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Parcelable.Creator<ArticleResponse> CREATOR = new Parcelable.Creator<ArticleResponse>() {
        @Override
        public ArticleResponse createFromParcel(Parcel source) {
            return new ArticleResponse(source);
        }

        @Override
        public ArticleResponse[] newArray(int size) {
            return new ArticleResponse[size];
        }
    };
}
