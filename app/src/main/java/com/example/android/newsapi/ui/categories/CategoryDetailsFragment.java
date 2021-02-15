package com.example.android.newsapi.ui.categories;

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

import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.R;
import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.adapter.ArticleAdapter;
import com.example.android.newsapi.apiservice.APIService;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.roomdatabase.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailsFragment extends Fragment {

    public static final String apiKey = BuildConfig.NEWS_API_KEY;

    public static final String PARAM_CATEGORY = "param-category";
    public static final String PARAM_LIST_STATE = "param-state";
    private static final String TAG = "categorydetailsfragment";

    private RecyclerView recyclerView;
    //private ArrayList<Article> savedList = new ArrayList<>();
    private ArrayList<Article> articles = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private NewsViewModel newsViewModel;
    //private NewsViewModel savedViewModel;
    //private NewsViewModel newsViewModel;
    private APIService.Category category;
    private boolean showSaved = false;
    private Parcelable listState;

    private int position;

    //private final String[] categories = {"general", "business", "entertainment", "health", "science", "sports", "technology",
    //};

    private final String[] categories = {

            APIService.Category.business.name(),
            APIService.Category.entertainment.name(),
            APIService.Category.health.name(),
            APIService.Category.science.name(),
            APIService.Category.sports.name(),
            APIService.Category.technology.name(),
    };

    public static CategoryDetailsFragment newInstance(APIService.Category category) {

        CategoryDetailsFragment categoryDetailsFragment = new CategoryDetailsFragment();

        if (category == null) {
            return categoryDetailsFragment;
        }

        Bundle args = new Bundle();
        args.putString(PARAM_CATEGORY, category.name());
        categoryDetailsFragment.setArguments(args);
        return categoryDetailsFragment;

    }

    public void onLoadViewModel() {

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        Utils utils = new Utils();
        utils.setCategory(category);
        newsViewModel.getTopHeadlinesByCategory(utils).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    mAdapter.setArticles(articles);

                    recyclerView.setAdapter(new ArticleAdapter(articles, R.layout.articles_row_item, getActivity()));

                    Log.d(TAG, "CategoryDetailsFragment - ViewModel");
                    Log.d(TAG, "Number of articles received: " + articles.size());
                    Log.d(TAG, "GetArticles: " + articles);

                    if (articles.size() > 0) {
                        Log.d(TAG, "CategoryDetailsFragment");
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
                        Toast.makeText(getActivity(), "No category details found", Toast.LENGTH_SHORT).show();
                        //mySwipeRefreshLayout.setRefreshing(false);
                        //mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            category = APIService.Category.valueOf(getArguments().getString(PARAM_CATEGORY));

            Log.d(TAG, "CategoryDetailsFragment - onCreate");
            Log.d(TAG, "CategoryDetailsFragment - getNetworkData");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_categorydetails, container, false);

        //https://stackoverflow.com/questions/28570842/setlayoutmanager-nullpointexception-in-recyclerview

        mAdapter = new ArticleAdapter(articles, R.layout.articles_row_item, getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        //onLoadViewModel();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(PARAM_LIST_STATE, listState);
        savedInstanceState.putParcelableArrayList("articles", articles);
        //savedInstanceState.putParcelableArrayList("savedList", savedList);
    }

    @Override
    public void onViewStateRestored (@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            //savedList = savedInstanceState.getParcelableArrayList("savedList");
            articles = savedInstanceState.getParcelableArrayList("articles");
            savedInstanceState.getParcelable(PARAM_LIST_STATE);
        }
    }

    //https://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.categories);

    }

}
