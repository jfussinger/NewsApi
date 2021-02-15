package com.example.android.newsapi.ui.sources;

import android.content.Context;
import android.net.ConnectivityManager;
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
import com.example.android.newsapi.adapter.SourcesAdapter;
import com.example.android.newsapi.model.Sources;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.example.android.newsapi.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SourcesFragment extends Fragment {

    SourcesViewModel sourcesViewModel;

    public SourcesFragment() {
        // Required empty public constructor
    }

    public static SourcesFragment newInstance() {
        return new SourcesFragment();
    }

    //https://firebase.google.com/docs/analytics/android/start
    private FirebaseAnalytics mFirebaseAnalytics;

    public static final String apiKey = BuildConfig.NEWS_API_KEY;

    private static final String TAG = "sourcesfragment";
    private RecyclerView recyclerView;
    private ArrayList<Sources> sources = new ArrayList<>();
    private SourcesAdapter mAdapter;
    private Parcelable savedSourcesRecyclerLayoutState;
    private static final String BUNDLE_Sources_RECYCLER_VIEW = "bundleSourcesRecyclerView";

    private CardView newsCardView;

    private int position;

    public void onLoadViewModel() {
        sourcesViewModel = ViewModelProviders.of(this).get(SourcesViewModel.class);
        Utils utils = new Utils();
        utils.setLanguage(Locale.getDefault().getLanguage());
        utils.setCountry(null);
        sourcesViewModel.getSource().observe(getViewLifecycleOwner(), new Observer<List<Sources>>() {
            @Override
            public void onChanged(@Nullable List<Sources> sources) {
                if (sources != null) {
                    mAdapter.setSources(sources);

                    recyclerView.setAdapter(new SourcesAdapter(sources, R.layout.sources_row_item, getActivity()));

                    Log.d(TAG, "SourcesFragment - ViewModel");
                    Log.d(TAG, "Number of articles received: " + sources.size());
                    Log.d(TAG, "GetSources: " + sources);


                    if (sources.size()>0) {
                        Log.d(TAG, "SourcesFragment");
                        Log.d(TAG, "Get Sources: " + sources);
                        Log.d(TAG, "Number of sources received: " + sources.size());
                        Log.d(TAG, "Name:" + sources.get(0).getName());
                        Log.d(TAG, "Id:" + sources.get(0).getId());
                        Log.d(TAG, "Description:" + sources.get(0).getDescription());
                        Log.d(TAG, "Url:" + sources.get(0).getUrl());
                        Log.d(TAG, "Category:" + sources.get(0).getCategory());
                        Log.d(TAG, "Language:" + sources.get(0).getLanguage());
                        Log.d(TAG, "Country:" + sources.get(0).getCountry());

                    }

                    else {
                        Toast.makeText(getActivity(), "No sources found", Toast.LENGTH_SHORT).show();
                        //mySwipeRefreshLayout.setRefreshing(false);
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
            sources = savedInstanceState.getParcelableArrayList("sourcesList");
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sources, container, false);

        if(savedInstanceState != null) {
            Log.v(TAG, "RecyclerView not found!");
            sources = savedInstanceState.getParcelableArrayList("sources");
        }

        mAdapter = new SourcesAdapter(sources, R.layout.sources_row_item, getActivity());

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
        savedInstanceState.putParcelableArrayList("sources", sources);
        savedInstanceState.putInt("position", position);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            sources = savedInstanceState.getParcelableArrayList("sources");
            position = savedInstanceState.getInt("position");
        }
    }


    //https://stackoverflow.com/questions/13472258/handling-actionbar-title-with-the-fragment-back-stack

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.sources);

    }

    //https://stackoverflow.com/questions/9570237/android-check-internet-connection

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
