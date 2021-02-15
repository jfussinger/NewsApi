package com.example.android.newsapi.apiservice;

import com.example.android.newsapi.model.ArticleResponse;
import com.example.android.newsapi.model.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("top-headlines")
    Call<ArticleResponse> getTopHeadlines(@Query("country") String country,
                                          @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ArticleResponse> getTopHeadlinesbyCategory(@Query("country") String country,@Query("category") String category,
                                                    @Query("apiKey") String apiKey);
    //Call<Article> getTopHeadlines(@Query("country") String country,
    // @Query("apiKey") String api_key);

    @GET("everything")
    Call<ArticleResponse> getEverything(@Query("q") String keyword,
                                        //@Query("language") String language,
                                        @Query("sortBy") String sortBy,
                                        @Query("apiKey") String apiKey);

    @GET("sources")
    Call<SourcesResponse> getSources(@Query("apiKey") String apiKey);

    //https://newsapi.org/docs/endpoints/top-headlines
    //https://newsapi.org/docs/endpoints/everything
    //https://newsapi.org/docs/endpoints/sources

    //Category example
    //https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=APIKEY

    //Can be use with out without General (exclude category)

    //https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY
    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=API_KEY
    //https://newsapi.org/v2/sources?apiKey=API_KEY

    enum Category {

        business("business"),
        entertainment("entertainment"),
        health("health"),
        science("science"),
        sports("sports"),
        technology("technology");

        public final String title;

        Category(String title) {
            this.title = title;
        }
    }

}
