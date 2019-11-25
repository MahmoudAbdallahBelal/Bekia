package bekia.recycle.views.login;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import bekia.recycle.R;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.login.LoginRequest;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.requests.profile.ProfileResponse;
import bekia.recycle.requests.register.RegisterRequest;
import bekia.recycle.requests.register.RegisterResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.forget_password.ForgetPasswordActivity;
import bekia.recycle.views.home.HomeActivity;
import bekia.recycle.views.profile.UserProfileActivity;
import bekia.recycle.views.register.RegisterActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView userNameEdit, passwordEdit;
    Button loginBtn, registerBtn;
    TextView forgetPasswordTxt;
    ProgressBar progressBarLogin;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utils.retrieveUserInfo(this)!= null)
        {
            startActivity(new Intent(LoginActivity.this , HomeActivity.class));
        }
        setContentView(R.layout.activity_login);


        progressBarLogin = findViewById(R.id.progress_login);
        userNameEdit = findViewById(R.id.username_login_edit);
        passwordEdit = findViewById(R.id.password_login_edit);
        loginBtn = findViewById(R.id.login_button);
        registerBtn = findViewById(R.id.signUp_button);
        forgetPasswordTxt = findViewById(R.id.forget_password_txt);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        forgetPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgetPassword();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(userNameEdit.getText().toString() , passwordEdit.getText().toString());
            }
        });
    }


    private void doLogin(String phone , String password)
    {

        progressBarLogin.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setPhone(phone);
        Call<LoginResponse> call = apiService.loginApi(retrieveUserLanguage(getApplicationContext()) ,loginRequest );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse>call, Response<LoginResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarLogin.setVisibility(View.INVISIBLE);

                if(response.raw().code() == 200)
                {
                    Utils.saveUserInfo(LoginActivity.this , response.body());
                    getUserInfo();
                    //Toast.makeText(LoginActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                    //goToHome();

                }
                else
                {
                    Toast.makeText(LoginActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse>call, Throwable t) {
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBarLogin.setVisibility(View.INVISIBLE);

            }
        });

    }
    String imageString= null;
    private  void getUserInfo() {
        LoginResponse loginResponse =  Utils.retrieveUserInfo(LoginActivity.this);
        String token =  loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.getUserInfoApi(token,"en");
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                if (response.raw().code() == 200) {
                    // Toast.makeText(UserProfileActivity.this, ""+response.body().getUserInfo().getPhone(), Toast.LENGTH_SHORT).show();

                    Utils.saveUserInfoForId(LoginActivity.this , response.body());
                    //Toast.makeText(LoginActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                    goToHome();

                }


            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.invalidData) , Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void goToRegister()
    {
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
    }

    private void goToForgetPassword()
    {
        Intent intent = new Intent(LoginActivity.this , ForgetPasswordActivity.class);
        startActivity(intent);
    }



    private void goToHome()
    {
        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
        startActivity(intent);
    }





}
