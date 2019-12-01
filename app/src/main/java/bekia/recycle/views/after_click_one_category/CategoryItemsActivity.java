package bekia.recycle.views.after_click_one_category;

import androidx.appcompat.app.AppCompatActivity;
import bekia.recycle.R;
import bekia.recycle.views.LocaleManager;

import android.content.Context;
import android.os.Bundle;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class CategoryItemsActivity extends LocalizationActivity {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

    }
}
