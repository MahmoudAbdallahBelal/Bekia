package bekia.recycle.views.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import bekia.recycle.R;
import bekia.recycle.helper.Utility;
import bekia.recycle.requests.city.CityDetails;
import bekia.recycle.requests.city.CityResponse;
import bekia.recycle.requests.register.RegisterRequest;
import bekia.recycle.requests.register.RegisterResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.login.LoginActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class RegisterActivity extends AppCompatActivity {

    RadioButton radioUser, radioCompany;
    EditText nameEdit,userNameEdit,phoneEdit,emailEdit,passwordEdit,confirmPasswordEdit;
    Button registerBtn, cityBtn;
    ImageView imageViewProfileImage;
    ApiInterface apiService;
    ProgressBar progressBarRegister;

    int userCheck = 0;

    private final int PICK_FROM_GALLERY = 2019;
    private final int PICK_FROM_GALLERY_COVER = 2020;
    Bitmap bitmap;
    private String userChoosenTask = null;

    private boolean result = false;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBarRegister = findViewById(R.id.progress_register);
        radioUser = findViewById(R.id.radio_user);
        radioCompany = findViewById(R.id.radio_company);
        nameEdit = findViewById(R.id.name_register_edit);
        userNameEdit = findViewById(R.id.username_register_edit);
        phoneEdit = findViewById(R.id.phone_register_edit);
        emailEdit = findViewById(R.id.email_register_edit);
        passwordEdit = findViewById(R.id.password_register_edit);
        confirmPasswordEdit = findViewById(R.id.confirm_password_edit_register);
        cityBtn = findViewById(R.id.city_register);
        registerBtn = findViewById(R.id.register_register_button);
        imageViewProfileImage = findViewById(R.id.imageView_user_image);

        imageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 result = Utility.checkPermission(RegisterActivity.this);

                 if(result)
                   selectImage();
                 else
                     Toast.makeText(RegisterActivity.this, getString(R.string.allowCameraPermission), Toast.LENGTH_SHORT).show();
            }
        });
         apiService =
                ApiClient.getClient().create(ApiInterface.class);
        registerRequest = new RegisterRequest();


        getCity();

        cityBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(cities.size() == 0)
                {
                    getCity();
                }
                else {
                    handleCities();
                }
                return false;

            }
        });

        radioUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked)
                userCheck = 2;
            }
        });

        radioCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                userCheck = 1;
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();

            }
        });

    }


    AlertDialog dialogA;
     List<CityDetails> cities =new ArrayList<>();
    private void handleCities()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.chooseCity));
        builder.setCancelable(true);


// add a list


       builder.setItems( stringCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(cityEdit.getWindowToken(), 0);

                cityBtn.setText(stringCities[which]);
                registerRequest.setCityId(cities.get(which).getId());

                if(cityBtn.getText().toString().equals(null))
                {
                    dialog.dismiss();
                    dialogA.dismiss();
                    dialogA.cancel();
                }

            }
        });


// create and show the alert dialog
         dialogA = builder.create();
         dialogA.setCancelable(true);
         dialogA.show();


         if(cityBtn.getText().toString().equals(null))
         {
             dialogA.dismiss();
             dialogA.cancel();
         }

    }




    String[] stringCities;
    private void getCity()
    {
        Call<CityResponse> call = apiService.getCitiesApi(retrieveUserLanguage(getApplicationContext()));
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse>call, Response<CityResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                if(response.raw().code() == 200)
                {
                   // Toast.makeText(RegisterActivity.this, ""+response.body().getCities(), Toast.LENGTH_SHORT).show();
                    //goToLogin();
                     cities =   response.body().getCities();
                     stringCities= new String[cities.size()];
                     String name= null;
                    for (int i = 0 ; i < cities.size() ; i++) {
                      name= cities.get(i).getName();
                      stringCities[i]=name;
                     }


                }


            }

            @Override
            public void onFailure(Call<CityResponse>call, Throwable t) {
                Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    RegisterRequest registerRequest ;
    private  void doRegister()
    {
        progressBarRegister.setVisibility(View.VISIBLE);
        registerRequest.setEmail(emailEdit.getText().toString());
        registerRequest.setName(nameEdit.getText().toString());
        registerRequest.setPassword(passwordEdit.getText().toString());
        registerRequest.setUserType(userCheck);
        registerRequest.setPhone(phoneEdit.getText().toString());

        Call<RegisterResponse> call = apiService.registerApi(retrieveUserLanguage(getApplicationContext()) ,registerRequest );
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse>call, Response<RegisterResponse> response) {
               // List<String> movies = response.body();
               // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarRegister.setVisibility(View.INVISIBLE);
                if(response.raw().code() == 201)
                {
                    Toast.makeText(RegisterActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    goToLogin();
                }
                else
                {
                Toast.makeText(RegisterActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RegisterResponse>call, Throwable t) {
                Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBarRegister.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void goToLogin()
    {
        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
        startActivity(intent);
    }



    private void selectImage() {

        final Dialog dialog  = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_select_image);
        TextView pic=dialog.findViewById(R.id.pic);
        ImageButton btnPic = dialog.findViewById(R.id.picImage);
        ImageButton btnChoose=dialog.findViewById(R.id.chooseImage);
        Window window = dialog.getWindow();
        window.setLayout(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);


        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask = "CAMERA";
                if (result) {
                    cameraIntent();
                }
                dialog.cancel();
            }
        });
        dialog.show();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask = "GALLERY";
                if (result) {
                    galleryIntent();
                }
                dialog.cancel();

            }
        });
        dialog.show();
    }

    Uri picUri;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, getString(R.string.selectFile)), SELECT_FILE);
    }
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @SuppressWarnings("deprecation")
    private Uri onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        picUri=getImageUri(RegisterActivity.this,bm);
        return picUri;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
            {
                    try {
                        picUri = onSelectFromGalleryResult(data);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                         imageViewProfileImage.setImageBitmap(bitmap);
                        String imageString = encodeImage(bitmap);
                        registerRequest.setProfileImage(imageString);

                    }
                    catch (Exception e){


                    }
                }
            else if (requestCode == REQUEST_CAMERA) {
                    try {

                        picUri = onCaptureImageResult(data);
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                      imageViewProfileImage.setImageBitmap(bitmap);
                       String imageString = encodeImage(bitmap);
                       registerRequest.setProfileImage(imageString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }

    }


    public static String encodeImage(Bitmap thumbnail) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.NO_WRAP);
        return imageEncoded;
    }

    private Uri onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        picUri =getImageUri(RegisterActivity.this,thumbnail);
        //ivImage.setImage_bitmap(thumbnail);
        return picUri;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", "hhhhh");
        return Uri.parse(path);
    }



}
