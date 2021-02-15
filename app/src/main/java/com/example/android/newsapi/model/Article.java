package com.example.android.newsapi.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

@Entity(tableName = "articles", indices = {@Index(value = "title", unique = true)})
public class Article implements Parcelable {

    //https://stackoverflow.com/questions/46112893/android-room-autogenerate-primary-key

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @Expose(serialize = false, deserialize = false)
    public int articleKey;

    @Embedded
    @SerializedName("articleSource")
    private ArticleSource articleSource;

    @ColumnInfo(name = "author")
    @SerializedName("author")
    public String author;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    public String title;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    public String description;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    public String url;

    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    public String urlToImage;

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    public String publishedAt;

    @ColumnInfo(name = "content")
    @SerializedName("content")
    public String content;

    @ColumnInfo(name = "country")
    @Expose(serialize = false, deserialize = false)
    private String country;

    @ColumnInfo(name = "category")
    @Expose(serialize = false, deserialize = false)
    private String category;

    @ColumnInfo(name = "save_date")
    @Expose(serialize = false, deserialize = false)
    private Timestamp saveDate = new Timestamp(System.currentTimeMillis());

    @ColumnInfo(name = "keyword")
    @Expose(serialize = false, deserialize = false)
    private String keyword;

    public Article(String author, String title, String description, String url,
                   String urlToImage, String publishedAt, String content) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Article(){

    }

    public ArticleSource getArticleSource() {
        return articleSource;
    }

    public void setArticleSource(ArticleSource articleSource) {
        this.articleSource = articleSource;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public int getArticleKey() {
        return articleKey;
    }

    public Timestamp getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Timestamp saveDate) {
        this.saveDate = saveDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent () {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleKey" + articleKey +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt=" + publishedAt +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", articleSource=" + articleSource +
                ", content='" + content + '\'' +
                ", country='" + country + '\'' +
                ", category='" + category + '\'' +
                ", saveDate=" + saveDate +
                ", keyword=" + keyword +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.articleKey);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.urlToImage);
        dest.writeString(this.publishedAt);
        dest.writeString(this.content);
        dest.writeString(this.country);
        dest.writeString(this.category);
        dest.writeSerializable(this.saveDate);
        dest.writeString(this.keyword);
    }

    public Article(Parcel in) {
        this.articleKey = in.readInt();
        this.author = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        this.publishedAt = in.readString();
        this.content = in.readString();
        this.country = in.readString();
        this.category = in.readString();
        this.saveDate = (Timestamp) in.readSerializable();
        this.keyword = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

}