package bekia.recycle.views.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import bekia.recycle.R;
import bekia.recycle.adapters.CategoriesAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utility;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.city.CityDetails;
import bekia.recycle.requests.city.CityResponse;
import bekia.recycle.requests.edit_profile.EditProfileReq;
import bekia.recycle.requests.edit_profile.EditProfileRes;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.requests.profile.ProfileResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.home.HomeActivity;
import bekia.recycle.views.login.LoginActivity;
import bekia.recycle.views.register.RegisterActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private EditText nameEdit,usernameEdit,phoneEdit,emailEdit,cityEdit;
    Button button_enable_edit_profile , editBtn;
    ProgressBar progressBarEditProfile;


    private final int PICK_FROM_GALLERY = 2019;
    private final int PICK_FROM_GALLERY_COVER = 2020;
    Bitmap bitmap;
    private String userChoosenTask = null;
    EditProfileReq editProfileReq = new EditProfileReq();

    private boolean result = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        progressBarEditProfile = findViewById(R.id.progress_edit_profile);
        editBtn = findViewById(R.id.button_edit_profile);
        nameEdit = findViewById(R.id.name_profile_edit);
        phoneEdit = findViewById(R.id.phone_profile_edit);
        emailEdit = findViewById(R.id.email_profile_edit);
        cityEdit = findViewById(R.id.city_profile);
        imgProfile = findViewById(R.id.imageView_user_image_profile);
        button_enable_edit_profile = findViewById(R.id.button_enable_edit_profile);
        button_enable_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditProfile();
            }
        });

        getUserInfo();
      editBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              editProfile();
          }
      });
      
      
      cityEdit.setOnTouchListener(new View.OnTouchListener() {
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

      imgProfile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              result = Utility.checkPermission(UserProfileActivity.this);

              if(result)
                  selectImage();
              else
                  Toast.makeText(UserProfileActivity.this, getString(R.string.allowCameraPermission), Toast.LENGTH_SHORT).show();
              }
      });
    }

    

    private void editProfile()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        editProfileReq.setEmail(emailEdit.getText().toString());
        editProfileReq.setName(nameEdit.getText().toString());
        editProfileReq.setPhone(phoneEdit.getText().toString());

        LoginResponse loginResponse =Utils.retrieveUserInfo(this);
       String token =  loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
        Call<EditProfileRes> call = apiService.editProfileApi(token ,editProfileReq);
        call.enqueue(new Callback<EditProfileRes>() {
            @Override
            public void onResponse(Call<EditProfileRes> call, Response<EditProfileRes> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                if (response.raw().code() == 200) {

                    Toast.makeText(UserProfileActivity.this, getString(R.string.successEdit), Toast.LENGTH_SHORT).show();

                    getUserInfo();
                }
                else
                {
                    Toast.makeText(UserProfileActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<EditProfileRes> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void enableEditProfile()
    {

        nameEdit.setEnabled(true);
        emailEdit.setEnabled(true);
        phoneEdit.setEnabled(true);
        cityEdit.setEnabled(true);

    }


    String imageString= null;
    private  void getUserInfo() {
       LoginResponse loginResponse =  Utils.retrieveUserInfo(UserProfileActivity.this);

       String token =  loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiService.getUserInfoApi(token,
                retrieveUserLanguage(getApplicationContext()));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                if (response.raw().code() == 200) {
                   // Toast.makeText(UserProfileActivity.this, ""+response.body().getUserInfo().getPhone(), Toast.LENGTH_SHORT).show();

                    nameEdit.setText(response.body().getUserInfo().getName());
                    phoneEdit.setText(response.body().getUserInfo().getPhone());
                    emailEdit.setText(response.body().getUserInfo().getEmail());
                   // if(response.body().getUserInfo().getCity()!= null)
                    cityEdit.setText(response.body().getUserInfo().getCity().getNameEn());
                    imageString = response.body().getUserInfo().getProfileImage();
                     Bitmap bitmap = StringToBitMap(imageString);
                    imgProfile.setImageBitmap(bitmap);


                }


            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
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

                cityEdit.setText(stringCities[which]);
                editProfileReq.setCityId(cities.get(which).getId());

                if(cityEdit.getText().toString().equals(null))
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


        if(cityEdit.getText().toString().equals(null))
        {
            dialogA.dismiss();
            dialogA.cancel();
        }

    }




    String[] stringCities;
    private void getCity()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
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
                Toast.makeText(UserProfileActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

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

        picUri=getImageUri(UserProfileActivity.this,bm);
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
                    imgProfile.setImageBitmap(bitmap);
                    String imageString = encodeImage(bitmap);
                    editProfileReq.setProfile_image(imageString);

                }
                catch (Exception e){


                }
            }
            else if (requestCode == REQUEST_CAMERA) {
                try {

                    picUri = onCaptureImageResult(data);
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    imgProfile.setImageBitmap(bitmap);
                    String imageString = encodeImage(bitmap);
                    editProfileReq.setProfile_image(imageString);
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

        picUri =getImageUri(UserProfileActivity.this,thumbnail);
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
