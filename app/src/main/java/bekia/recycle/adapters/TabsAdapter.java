package bekia.recycle.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import bekia.recycle.views.fragments.CategoriesFragment;
import bekia.recycle.views.fragments.MostPopularFragment;

public class TabsAdapter  extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public TabsAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CategoriesFragment categoriesFragment = new CategoriesFragment();
                return categoriesFragment;
            case 1:
                MostPopularFragment mostPopularFragment = new MostPopularFragment();
                return mostPopularFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}

