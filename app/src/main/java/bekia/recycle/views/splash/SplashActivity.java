package bekia.recycle.views.splash;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import bekia.recycle.R;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.login.LoginActivity;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class SplashActivity extends LocalizationActivity {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base, lan));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);



       /* ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<RegisterResponse> call = apiService.getTopRatedMovies("sjsjs");
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse>call, Response<RegisterResponse> response) {
               // List<String> movies = response.body();
               // Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<RegisterResponse>call, Throwable t) {
                // Log error here since request failed
                //Log.e(TAG, t.toString());
            }
        });
*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        }, 3000);


    }

}
