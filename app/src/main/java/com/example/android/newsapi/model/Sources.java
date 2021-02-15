package com.example.android.newsapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "sources")
public class Sources implements Parcelable {

    //https://stackoverflow.com/questions/46112893/android-room-autogenerate-primary-key

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    public String description;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    public String url;

    @ColumnInfo(name = "category")
    @SerializedName("category")
    public String category;

    @ColumnInfo(name = "language")
    @SerializedName("language")
    public String language;

    @ColumnInfo(name = "country")
    @Expose(serialize = false, deserialize = false)
    private String country;

    public Sources(String id, String name, String description, String url,
                   String category, String language, String country) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public Sources(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage(){
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Sources{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.category);
        dest.writeString(this.language);
        dest.writeString(this.country);
    }

    public Sources(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.category = in.readString();
        this.language = in.readString();
        this.country = in.readString();
    }

    public static final Parcelable.Creator<Sources> CREATOR = new Parcelable.Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel source) {
            return new Sources(source);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };

}