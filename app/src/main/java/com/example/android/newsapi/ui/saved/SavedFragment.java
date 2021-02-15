package com.example.android.newsapi.ui.saved;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.newsapi.R;
import com.example.android.newsapi.adapter.ArticleAdapter;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.roomdatabase.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    //Params for saving data

    public static final String PARAM_LIST_STATE = "param-state";
    private static final String TAG = "savedfragment";

    private RecyclerView recyclerView;
    private ArrayList<Article> articles = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private NewsViewModel newsViewModel;
    private boolean showSaved = false;
    private Parcelable listState;

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    public void onLoadViewModel() {

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        if (showSaved) {
            newsViewModel.getAllSaved().observe(getViewLifecycleOwner(),new Observer<List<Article>>() {
                @Override
                public void onChanged(@Nullable List<Article> articles) {
                    if (articles != null) {
                        mAdapter.setArticles(articles);
                        //mAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(new ArticleAdapter(articles, R.layout.articles_row_item, getActivity()));

                        restoreRecyclerViewState();

                        Log.d(TAG, "FavoritesFragment - ViewModel");
                        Log.d(TAG, "Number of articles received: " + articles.size());
                        Log.d(TAG, "GetArticles: " + articles);

                    } else {
                        //mAdapter.notifyDataSetChanged();
                        restoreRecyclerViewState();
                    }

                    if (articles.size()>0) {
                        Log.d(TAG, "FavoritesFragment");
                        Log.d(TAG, "Number of articles received: " + articles.size());
                        Log.d(TAG, "ArticleKey:" + articles.get(0).getArticleKey());
                        Log.d(TAG, "UrlToImage:" + articles.get(0).getUrlToImage());
                        Log.d(TAG, "Title:" + articles.get(0).getTitle());
                        Log.d(TAG, "Author:" + articles.get(0).getAuthor());
                        Log.d(TAG, "Description:" + articles.get(0).getDescription());
                        Log.d(TAG, "PublishedAt:" + articles.get(0).getPublishedAt());
                        Log.d(TAG, "Content:" + articles.get(0).getContent());
                        Log.d(TAG, "Url:" + articles.get(0).getUrl());
                    }
                    else {
                        Toast.makeText(getContext(), "No favorites found", Toast.LENGTH_SHORT).show();
                        //mySwipeRefreshLayout.setRefreshing(false);
                    }

                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSaved = true;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_saved, container, false);

        //https://stackoverflow.com/questions/28570842/setlayoutmanager-nullpointexception-in-recyclerview

        mAdapter = new ArticleAdapter(articles, R.layout.articles_row_item, getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setAdapter(mAdapter);
        //recyclerView.setAdapter(new ArticleAdapter(articlesList, R.layout.articles_row_item, getActivity()));

        onLoadViewModel();

        setRetainInstance(true);

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(PARAM_LIST_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(PARAM_LIST_STATE, listState);
        savedInstanceState.putParcelableArrayList("articles", articles);
    }

    @Override
    public void onViewStateRestored (@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList("articles");
            savedInstanceState.getParcelable(PARAM_LIST_STATE);
        }
    }

    private void restoreRecyclerViewState() {
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    //https://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.saved);

    }

}
