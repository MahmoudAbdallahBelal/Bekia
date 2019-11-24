package bekia.recycle.views.settings;

import androidx.appcompat.app.AppCompatActivity;
import bekia.recycle.R;
import bekia.recycle.helper.Utils;
import bekia.recycle.views.splash.SplashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button arBtn , enBtn ,frBtn , grBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        arBtn =findViewById(R.id.button_language_ar);
        enBtn =findViewById(R.id.button_language_en);
        frBtn =findViewById(R.id.button_language_fr);
        grBtn =findViewById(R.id.button_language_gr);

        arBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveUserLanguage(SettingsActivity.this, "ar");
                Intent intent = new Intent(SettingsActivity.this , SplashActivity.class);
                startActivity(intent);

            }
        });
        enBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveUserLanguage(SettingsActivity.this, "en");
                Intent intent = new Intent(SettingsActivity.this , SplashActivity.class);
                startActivity(intent);
            }
        });
        frBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveUserLanguage(SettingsActivity.this, "fr");
                Intent intent = new Intent(SettingsActivity.this , SplashActivity.class);
                startActivity(intent);
            }
        });
        grBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveUserLanguage(SettingsActivity.this, "gr");
                Intent intent = new Intent(SettingsActivity.this , SplashActivity.class);
                startActivity(intent);
            }
        });

    }
}
