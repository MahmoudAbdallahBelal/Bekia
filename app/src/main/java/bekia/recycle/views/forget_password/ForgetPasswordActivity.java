package bekia.recycle.views.forget_password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import bekia.recycle.R;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.forget_password.ForgetPasswordRes;
import bekia.recycle.requests.forget_password.ForgetPasswordRq;
import bekia.recycle.requests.login.LoginRequest;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.login.LoginActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bekia.recycle.views.settings.SettingsActivity.lan;

public class ForgetPasswordActivity extends LocalizationActivity {

    EditText forgetPasswordEdit;
    Button restorePasswordBtn;
    ProgressBar progressBarResetPassword;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressBarResetPassword = findViewById(R.id.progress_reset_password);
        forgetPasswordEdit = findViewById(R.id.email_forget_password_edit);
        restorePasswordBtn = findViewById(R.id.restore_password_button);


        restorePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

    }

    private void forgetPassword()
    {

        progressBarResetPassword.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ForgetPasswordRq forgetPasswordRq = new ForgetPasswordRq();
        forgetPasswordRq.setEmail(forgetPasswordEdit.getText().toString());
        Call<ForgetPasswordRes> call = apiService.forgetPasswordApi( forgetPasswordRq );
        call.enqueue(new Callback<ForgetPasswordRes>() {
            @Override
            public void onResponse(Call<ForgetPasswordRes>call, Response<ForgetPasswordRes> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarResetPassword.setVisibility(View.INVISIBLE);

                if(response.raw().code() == 200)
                {
                    gotToResetPassword();

                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ForgetPasswordRes>call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBarResetPassword.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void gotToResetPassword()
    {
        Intent intent = new Intent(ForgetPasswordActivity.this , ResetPasswordActivity.class);
        intent.putExtra("email",forgetPasswordEdit.getText().toString());
        startActivity(intent);
    }
}
