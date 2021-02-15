package com.example.android.newsapi.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.R;
import com.example.android.newsapi.utils.Utils;
import com.example.android.newsapi.adapter.ArticleAdapter;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.roomdatabase.NewsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    //https://firebase.google.com/docs/analytics/android/start
    private FirebaseAnalytics mFirebaseAnalytics;

    public static String query = "";
    public static final String sortBy = "publishedAt";
    public static final String apiKey = BuildConfig.NEWS_API_KEY;

    private static final String TAG = "searchfragment";
    private RecyclerView recyclerView;
    private ArrayList<Article> articles = new ArrayList<>();
    //private List<Article> articlesList = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private SearchView searchView;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private String mSearchQuery;
    //private SwipeRefreshLayout mySwipeRefreshLayout;
    private Parcelable savedArticlesRecyclerLayoutState;
    private static final String BUNDLE_ARTICLES_RECYCLER_VIEW = "bundleArticlesRecyclerView";

    private CardView newsCardView;

    private int position;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    //https://github.com/udacity/android-custom-arrayadapter/blob/parcelable/app/src/main/java/demo/example/com/customarrayadapter/MainActivityFragment.java

    public void onLoadViewModel(String keyword) {
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        query = keyword;
        Utils utils = new Utils();
        utils.setKeyword(keyword);

        viewModel.getEverything(utils).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    mAdapter.setArticles(articles);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(new ArticleAdapter(articles, R.layout.articles_row_item, getActivity()));

                    Log.d(TAG, "SearchFragment - ViewModel");
                    Log.d(TAG, "Number of articles received: " + articles.size());
                    Log.d(TAG, "GetArticles: " + articles);
                    Log.d(TAG, "keyword: " + keyword);
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //https://firebase.google.com/docs/analytics/android/start
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        if(savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getString("searchQuery");
            articles = savedInstanceState.getParcelableArrayList("articles");
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        if(savedInstanceState != null) {
            Log.v(TAG, "RecyclerView not found!");
            articles = savedInstanceState.getParcelableArrayList("articles");
        } else {

            if (!isNetworkAvailable(getContext())) {
                Toast.makeText(getContext(), "Your device is not online, " +
                                "check wifi and try again!",
                        Toast.LENGTH_LONG).show();
            }
        }

        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        mAdapter = new ArticleAdapter(articles, R.layout.articles_row_item, getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setRetainInstance(true);

        // Inflate the layout for this fragment
        return view;

    }

    //https://androidpedia.net/en/tutorial/4786/searchview

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        //searchView.setMaxWidth(Integer.MAX_VALUE);

        //https://stackoverflow.com/questions/22582201/restore-state-of-androids-search-view-widget/42875927

        if(mSearchQuery != null){
            searchView.setIconified(true);
            searchView.onActionViewExpanded();
            searchView.setQuery(mSearchQuery, false);
            searchView.setFocusable(true);
        }

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                //keyword = query;

                if (query.length()>0){
                    onLoadingSwipeRefresh(query);

                    //https://stackoverflow.com/questions/34603157/how-to-get-a-text-from-searchview
                    Toast.makeText(getActivity(), "Searching for " + query, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Type to search!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchQuery = newText;
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    //@Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    //https://developer.android.com/training/swipe/add-swipe-interface
    //https://developer.android.com/training/swipe/respond-refresh-request

    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            //return;
        }
        //super.onBackPressed();
    }

    public void onRefresh() {

        //onLoadArticles("");
        onLoadViewModel("");
    }

    private void onLoadingSwipeRefresh(final String keyword){

        mySwipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        //onLoadArticles(keyword);
                        onLoadViewModel(keyword);
                    }
                }
        );

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("articles", articles);
        savedInstanceState.putInt("position", position);
        savedInstanceState.putParcelable(BUNDLE_ARTICLES_RECYCLER_VIEW, recyclerView.getLayoutManager().onSaveInstanceState());

        //https://stackoverflow.com/questions/22498344/is-there-a-better-way-to-restore-searchview-state
        mSearchQuery = searchView.getQuery().toString();
        savedInstanceState.putString("searchQuery", mSearchQuery);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList("articles");
            position = savedInstanceState.getInt("position");

            //https://stackoverflow.com/questions/22582201/restore-state-of-androids-search-view-widget/42875927

            mSearchQuery = savedInstanceState.getString("searchQuery");
            savedArticlesRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_ARTICLES_RECYCLER_VIEW);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(mSearchQuery != null) {
            onLoadingSwipeRefresh(mSearchQuery);
        } else {
            onLoadingSwipeRefresh("");
        }
    }

    //https://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack

    @Override
    public void onResume() {
        super.onResume();

        //Set title blank
        //getActivity().setTitle();
        getActivity().setTitle(R.string.search_hint);

    }

    //https://stackoverflow.com/questions/9570237/android-check-internet-connection

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}

