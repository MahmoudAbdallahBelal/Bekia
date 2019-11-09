package bekia.recycle.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import bekia.recycle.R;
import bekia.recycle.views.forget_password.ForgetPasswordActivity;
import bekia.recycle.views.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView userNameEdit, passwordEdit;
    Button loginBtn, registerBtn;
    TextView forgetPasswordTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEdit = findViewById(R.id.username_login_edit);
        passwordEdit = findViewById(R.id.password_login_edit);
        loginBtn = findViewById(R.id.login_button);
        registerBtn = findViewById(R.id.signUp_button);
        forgetPasswordTxt = findViewById(R.id.forget_password_txt);


    }

    private void doLogin(String email , String password)
    {

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





}
