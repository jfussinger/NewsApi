package com.example.android.newsapi.ui.categories;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.newsapi.R;
import com.example.android.newsapi.apiservice.APIService;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.business,
            R.string.entertainment,
            R.string.health,
            R.string.science,
            R.string.sports,
            R.string.technology};

    private final CategoryDetailsFragment[] categoryDetailsFragment;

    //private final String[] categories = {"general", "business", "entertainment", "health", "science", "sports", "technology",
    //};

    //private final String[] categories = {APIService.Category.business.name(), APIService.Category.entertainment.name(), APIService.Category.health.name(), APIService.Category.science.name(), APIService.Category.sports.name(), APIService.Category.technology.name(),
    //};

    private final int[] TAB_ICONS = {
            R.drawable.baseline_public_black_24dp,
            R.drawable.baseline_work_black_24dp,
            R.drawable.baseline_people_black_24dp,
            R.drawable.baseline_local_hospital_black_24dp,
            R.drawable.baseline_biotech_black_24dp,
            R.drawable.baseline_people_black_24dp,
            R.drawable.baseline_phonelink_black_24dp,
    };

    public SectionsPagerAdapter(FragmentManager fm, String[] categories) {
        super(fm);
        categoryDetailsFragment = new CategoryDetailsFragment[categories.length];
        for (int i = 0; i < categories.length; i++) {
            categoryDetailsFragment[i] = CategoryDetailsFragment.newInstance(APIService.Category.valueOf(categories[i]));
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return TopHeadlinesFragment.newInstance(position + 1);
        return categoryDetailsFragment[i];
    }

    //@Nullable
    //@Override
    //public CharSequence getPageTitle(int position) {
    //return mContext.getResources().getString(TAB_TITLES[position]);
    //return mContext.getResources().getString(Utils.CATEGORY[position]);
    //}

    @Override
    public int getCount() {

        //return TAB_TITLES.length;
        //return categoryDetailsFragment.length;

        return categoryDetailsFragment == null ? 0 : categoryDetailsFragment.length;
    }
}