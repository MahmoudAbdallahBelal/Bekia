package bekia.recycle.views.forget_password;

import androidx.appcompat.app.AppCompatActivity;
import bekia.recycle.R;
import bekia.recycle.requests.forget_password.ForgetPasswordRes;
import bekia.recycle.requests.forget_password.ForgetPasswordRq;
import bekia.recycle.requests.forget_password.reset.ResetPasswordRes;
import bekia.recycle.requests.forget_password.reset.ResetPasswordRq;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.login.LoginActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class ResetPasswordActivity extends LocalizationActivity {

    EditText codeEdit , newPasswordEdit;
    ProgressBar progressBarResetNewPassword;
    Button resetPasswordBtn;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPasswordBtn = findViewById(R.id.button_reset_new_pass);
        progressBarResetNewPassword = findViewById(R.id.progress_reset_with_code);
        codeEdit = findViewById(R.id.edit_code);
        newPasswordEdit = findViewById(R.id.edit_new_password);

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetNewPassword();
            }
        });

    }



    private void resetNewPassword()
        {

            progressBarResetNewPassword.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            final ResetPasswordRq resetPasswordRq = new ResetPasswordRq();
            resetPasswordRq.setEmail(getIntent().getStringExtra("email"));
            resetPasswordRq.setCode(codeEdit.getText().toString());
            resetPasswordRq.setPassword(newPasswordEdit.getText().toString());
            Call<ResetPasswordRes> call = apiService.resetPasswordApi( resetPasswordRq );
            call.enqueue(new Callback<ResetPasswordRes>() {
                @Override
                public void onResponse(Call<ResetPasswordRes>call, Response<ResetPasswordRes> response) {
                    // List<String> movies = response.body();
                    // Log.d(TAG, "Number of movies received: " + movies.size());
                    progressBarResetNewPassword.setVisibility(View.INVISIBLE);

                    if(response.raw().code() == 200)
                    {
                          Toast.makeText(ResetPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(ResetPasswordActivity.this , LoginActivity.class));
                    }
                    else
                    {
                        Toast.makeText(ResetPasswordActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<ResetPasswordRes>call, Throwable t) {
                    Toast.makeText(ResetPasswordActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBarResetNewPassword.setVisibility(View.INVISIBLE);

                }
            });


        }


}


