package com.example.android.newsapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourcesResponse implements Parcelable {

    @SerializedName("status")
    public String status;

    @SerializedName("sources")
    public List<Sources> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sources> getSources() {
        return sources;
    }

    public void setSources(List<Sources> sources) {
        this.sources = sources;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeTypedList(this.sources);
    }

    public SourcesResponse () {
    }

    public SourcesResponse(Parcel in) {
        this.status = in.readString();
        this.sources = in.createTypedArrayList(Sources.CREATOR);
    }

    public static final Parcelable.Creator<SourcesResponse> CREATOR = new Parcelable.Creator<SourcesResponse>() {
        @Override
        public SourcesResponse createFromParcel(Parcel source) {
            return new SourcesResponse(source);
        }

        @Override
        public SourcesResponse[] newArray(int size) {
            return new SourcesResponse[size];
        }
    };
}