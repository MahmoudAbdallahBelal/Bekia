package bekia.recycle.views.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import bekia.recycle.R;
import bekia.recycle.adapters.TabsAdapter;
import bekia.recycle.views.LocaleManager;

import android.content.Context;
import android.os.Bundle;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.android.material.tabs.TabLayout;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class TestActivity extends LocalizationActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.categories)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.mostPopular)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
