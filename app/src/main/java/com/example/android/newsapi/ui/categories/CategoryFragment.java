package com.example.android.newsapi.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.android.newsapi.R;
import com.example.android.newsapi.apiservice.APIService;
import com.google.android.material.tabs.TabLayout;

public class CategoryFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    private int position;

    public APIService.Category tabCategory;

    //private final String[] categories = {"general", "business", "entertainment", "health", "science", "sports", "technology",
    //};

    private final String[] categories = {
            APIService.Category.business.name(),
            APIService.Category.entertainment.name(),
            APIService.Category.health.name(),
            APIService.Category.science.name(),
            APIService.Category.sports.name(),
            APIService.Category.technology.name()
    };

    //private final int[] TAB_ICONS = {
    //R.drawable.baseline_work_black_24dp,
    //R.drawable.baseline_people_black_24dp,
    //R.drawable.baseline_local_hospital_black_24dp,
    //R.drawable.baseline_biotech_black_24dp,
    //R.drawable.baseline_people_black_24dp,
    //R.drawable.baseline_phonelink_black_24dp,
    //};

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category, container, false);

        if (getActivity() != null) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), categories);
            viewPager = view.findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout = view.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            setupTabs();
        }
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return view;

    }

    //Set text of tabs

    private void setupTabs() {
        TabLayout.Tab tab;
        for (int i = 0; i < categories.length; i++) {
            tab = tabLayout.getTabAt(i);
            if (tab != null) {
                //tab.setIcon(TAB_ICONS[i]).setText(categories[i]);
                tab.setText(categories[i]);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("position", position);
        //savedInstanceState.putString("argsectionnumber", ARG_SECTION_NUMBER);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            //ARG_SECTION_NUMBER = savedInstanceState.getString("argsectionnumber");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.title_categories);

    }

}
