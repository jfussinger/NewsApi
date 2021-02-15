package com.example.android.newsapi.ui.topheadlines;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.R;
import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.adapter.ArticleAdapter;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.roomdatabase.NewsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TopHeadlinesFragment extends Fragment {

    public TopHeadlinesFragment() {
        // Required empty public constructor
    }

    public static TopHeadlinesFragment newInstance() {
        return new TopHeadlinesFragment();
    }

    //https://firebase.google.com/docs/analytics/android/start
    private FirebaseAnalytics mFirebaseAnalytics;

    public static final String apiKey = BuildConfig.NEWS_API_KEY;

    private static final String TAG = "topheadlinesfragment";
    private RecyclerView recyclerView;
    private ArrayList<Article> articles = new ArrayList<>();
    private ArticleAdapter mAdapter;
    //private SwipeRefreshLayout mySwipeRefreshLayout;
    private Parcelable savedArticlesRecyclerLayoutState;
    private static final String BUNDLE_ARTICLES_RECYCLER_VIEW = "bundleArticlesRecyclerView";

    private CardView newsCardView;

    private int position;

    public void onLoadViewModel() {

        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        Utils utils = new Utils();
        utils.setLanguage(Locale.getDefault().getLanguage());
        utils.setCountry(Locale.getDefault().getCountry());
        viewModel.getTopHeadlines(utils).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    mAdapter.setArticles(articles);

                    recyclerView.setAdapter(new ArticleAdapter(articles, R.layout.articles_row_item, getActivity()));

                    Log.d(TAG, "TopHeadlinesFragment - ViewModel");
                    Log.d(TAG, "Number of articles received: " + articles.size());
                    Log.d(TAG, "GetArticles: " + articles);

                    if (articles.size()>0) {
                        Log.d(TAG, "TopHeadlinesFragment");
                        Log.d(TAG, "Number of articles received: " + articles.size());
                        Log.d(TAG, "ArticleKey:" + articles.get(0).getArticleKey());
                        Log.d(TAG, "UrlToImage:" + articles.get(0).getUrlToImage());
                        Log.d(TAG, "Title:" + articles.get(0).getTitle());
                        Log.d(TAG, "Author:" + articles.get(0).getAuthor());
                        //Log.d(TAG, "Id:" + articlesList.get(0).getArticleSource().getSourceId());
                        //Log.d(TAG, "Name:" + articlesList.get(0).getArticleSource().getName());
                        Log.d(TAG, "Description:" + articles.get(0).getDescription());
                        Log.d(TAG, "PublishedAt:" + articles.get(0).getPublishedAt());
                        Log.d(TAG, "Content:" + articles.get(0).getContent());
                        Log.d(TAG, "Url:" + articles.get(0).getUrl());
                    }
                    else {
                        Toast.makeText(getActivity(), "No news found", Toast.LENGTH_SHORT).show();
                        //mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    //https://github.com/udacity/android-custom-arrayadapter/blob/parcelable/app/src/main/java/demo/example/com/customarrayadapter/MainActivityFragment.java

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //https://firebase.google.com/docs/analytics/android/start
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        if(savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList("articles");
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_topheadlines, container, false);

        if(savedInstanceState != null) {
            Log.v(TAG, "RecyclerView not found!");
            articles = savedInstanceState.getParcelableArrayList("articles");
        }

        mAdapter = new ArticleAdapter(articles, R.layout.articles_row_item, getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        onLoadViewModel();

        setRetainInstance(true);

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //onLoadViewModel();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("articles", articles);
        savedInstanceState.putInt("position", position);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList("articles");
            position = savedInstanceState.getInt("position");
        }
    }

    //https://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.topHeadlines);

    }

}


