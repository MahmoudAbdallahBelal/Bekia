package bekia.recycle.views.after_click_one_category;

import androidx.appcompat.app.AppCompatActivity;
import bekia.recycle.R;
import bekia.recycle.views.LocaleManager;

import android.content.Context;
import android.os.Bundle;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class CategoryItemsActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

    }
}
