package bekia.recycle.views.sell_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.CategoriesAdapter;
import bekia.recycle.adapters.ItemSellImagesAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.add_item.AddNewItemRequest;
import bekia.recycle.requests.add_item.AddNewItemResponse;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.city.CityDetails;
import bekia.recycle.requests.city.CityResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.requests.register.RegisterResponse;
import bekia.recycle.views.home.HomeActivity;
import bekia.recycle.views.profile.UserProfileActivity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ItemSellActivity extends AppCompatActivity {

    RecyclerView recyclerViewSellItem ;
    Button takeItemImagesBtn , sellItemBtn ,categoryBtn , cityBtn ,subCategoryBtn;
    private String userChoosenTask = null;
    private boolean result  =false;
    private Bitmap bitmap;
    ItemSellImagesAdapter itemSellImagesAdapter;
    
    EditText nameEdit, descEdit,phoneEdit;
    int selectedCategory  , getSelectedCity = 0 ;
    ApiInterface apiService;
    String[] stringCities , stringCategories ,stringSubCategories;
    List<CityDetails> cities =new ArrayList<>();
    AddNewItemRequest addNewItemRequest ;
    EditText priceEdit ;
    CheckBox isNegotiableCheck;
    boolean isisNegotiablePrice = false;
    int categoryId = 0;

ProgressBar progressBarAddItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_sell);

        recyclerViewSellItem = findViewById(R.id.recycle_item_sell);
        takeItemImagesBtn = findViewById(R.id.button_upload_item_sell_images);
        nameEdit = findViewById(R.id.edit_item_name);
        descEdit = findViewById(R.id.edit_item_desc);
        phoneEdit = findViewById(R.id.edit_item_phone);
        categoryBtn = findViewById(R.id.edit_item_category);
        cityBtn = findViewById(R.id.edit_item_city);
        sellItemBtn = findViewById(R.id.sell_item_button);
        priceEdit = findViewById(R.id.edit_item_price);
        subCategoryBtn =findViewById(R.id.edit_item_sub_category);
        isNegotiableCheck = findViewById(R.id.check_is_negotiable);

        progressBarAddItem = findViewById(R.id.progress_add_item);


        isNegotiableCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isisNegotiablePrice = true;
                else
                    isisNegotiablePrice =false;
            }
        });

        apiService =
                ApiClient.getClient().create(ApiInterface .class);

         addNewItemRequest = new AddNewItemRequest();

        GridLayoutManager linearLayoutManager = new GridLayoutManager(ItemSellActivity.this,2);
        recyclerViewSellItem.setLayoutManager(linearLayoutManager);



        takeItemImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
                setImagesAdapter();
          }
        });



        cityBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (cities.size() == 0) {
                    getCities();

                } else {
                    handleCities();
                }
                return false;
            }
        });

        categoryBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                subCategoryBtn.setVisibility(View.GONE);
                getCategories();

                if (categoriesList.size() == 0) {
                    getCategories();

                } else {
                    handleCategories();
                }

                return false;
            }
        });

        subCategoryBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                getSubCategories();
                if (subCategoriesList.size() == 0) {
                    getSubCategories();

                } else {
                    handleSubCategories();
                }

                return false;
            }
        });


   sellItemBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           sellItemLogic();
       }
   });

    }


    private List<String> listImages = new ArrayList<>();
    private void convertBitmapToStrings()
    {
          if(sellBitmapImages.size() != 0)
          {
              for (int i = 0 ; i < sellBitmapImages.size() ; i ++)
              {
                  String stringImage = encodeImage(sellBitmapImages.get(i));
                  listImages.add(stringImage);

              }
          }
    }

   private void sellItemLogic()
   {
       progressBarAddItem.setVisibility(View.VISIBLE);
       convertBitmapToStrings();
       if(listImages.size() != 0) {
           addNewItemRequest.setImages(listImages);
       }
       addNewItemRequest .setName(nameEdit.getText().toString());
       addNewItemRequest .setDisplayed_phone(phoneEdit.getText().toString());
       addNewItemRequest .setDesc(descEdit.getText().toString());
       addNewItemRequest.setPrice(priceEdit.getText().toString());
       addNewItemRequest.setIs_negotiable(isisNegotiablePrice);


       // connect to add new Item Api
       LoginResponse loginResponse =  Utils.retrieveUserInfo(ItemSellActivity.this);

       String token =  loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
       Call<AddNewItemResponse> call = apiService.addNewItemApi(token,"en" ,addNewItemRequest );
       call.enqueue(new Callback<AddNewItemResponse>() {
           @Override
           public void onResponse(Call<AddNewItemResponse>call, Response<AddNewItemResponse> response) {
               // List<String> movies = response.body();
               // Log.d(TAG, "Number of movies received: " + movies.size());
             progressBarAddItem.setVisibility(View.INVISIBLE);
               if(response.raw().code() == 201)
               {
                   Toast.makeText(ItemSellActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   
               }
               else
               {
                   Toast.makeText(ItemSellActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

               }

           }

           @Override
           public void onFailure(Call<AddNewItemResponse>call, Throwable t) {
               progressBarAddItem.setVisibility(View.INVISIBLE);

               Toast.makeText(ItemSellActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });


   }
    private void setImagesAdapter()
    {
        if(sellBitmapImages.size()!=0){

            itemSellImagesAdapter = new ItemSellImagesAdapter(sellBitmapImages, ItemSellActivity.this, new OnItemClickListener() {
                @Override
                public void onItemClick(CategoryDetails item) {

                }

                @Override
                public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                }

                @Override
                public void onItemDelete(ItemDetailsReponse item) {

                }
            });
            recyclerViewSellItem.setAdapter(itemSellImagesAdapter);
            itemSellImagesAdapter.notifyDataSetChanged();
        }

    }

    /* ============================================================================*/
    private void getCities()
    {
        Call<CityResponse> call = apiService.getCitiesApi("en");
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
                Toast.makeText(ItemSellActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

//
AlertDialog dialogA;


    private void handleCategories()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your Item Category");
        builder.setCancelable(true);

        builder.setItems( stringCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                categoryBtn.setText(categoriesList.get(which).getName());
                categoryId = categoriesList.get(which).getId();
                //addNewItemRequest.setCategory_id(categoriesList.get(which).getId());

                if(categoryBtn.getText().toString().equals(null))
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


        if(categoryBtn.getText().toString().equals(null))
        {
            dialogA.dismiss();
            dialogA.cancel();
        }

    }

    private void handleSubCategories()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your Item Sub-Category");
        builder.setCancelable(true);

        builder.setItems( stringSubCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                subCategoryBtn.setText(subCategoriesList.get(which).getName());
                addNewItemRequest.setCategory_id(subCategoriesList.get(which).getId());

                if(subCategoryBtn.getText().toString().equals(null))
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


        if(subCategoryBtn.getText().toString().equals(null))
        {
            dialogA.dismiss();
            dialogA.cancel();
        }

    }

    private void handleCities()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your City");
        builder.setCancelable(true);

        builder.setItems( stringCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(cityBtn.getWindowToken(), 0);

                cityBtn.setText(stringCities[which]);
               addNewItemRequest.setCity_id(cities.get(which).getId());

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
    /* =========get categories===================================================================*/

    CategoriesAdapter categoriesAdapter ;
    List<CategoryDetails> categoriesList = new ArrayList<>();
    List<CategoryDetails> subCategoriesList = new ArrayList<>();

    private void getCategories()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesResponse> call = apiService.getCategoriesApi("en");
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse>call, Response<CategoriesResponse> response) {
                if(response.raw().code() == 200)
                {

                    categoriesList = response.body().getCategories();
                    stringCategories= new String[categoriesList.size()];
                    String name= null;
                    for (int i = 0 ; i < categoriesList.size() ; i++) {
                        name= categoriesList.get(i).getName();
                        stringCategories[i]=name;
                    }

                    subCategoryBtn.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Call<CategoriesResponse>call, Throwable t) {
                Toast.makeText(ItemSellActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void getSubCategories()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesResponse> call = apiService.getSubCategoriesApi(categoryId ,"en");
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse>call, Response<CategoriesResponse> response) {
                if(response.raw().code() == 200)
                {
                    subCategoriesList = response.body().getCategories();
                    stringSubCategories= new String[subCategoriesList.size()];
                    String name= null;
                    for (int i = 0 ; i < subCategoriesList.size() ; i++) {
                        name= subCategoriesList.get(i).getName();
                        stringSubCategories[i]=name;
                    }
                }


            }

            @Override
            public void onFailure(Call<CategoriesResponse>call, Throwable t) {
                Toast.makeText(ItemSellActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /* ============================================================================*/
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
//                if (result) {
                    cameraIntent();
               // }
                dialog.cancel();
            }
        });
        dialog.show();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userChoosenTask = "GALLERY";
                //if (result) {
                    galleryIntent();
                //}
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
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    List<Bitmap> sellBitmapImages = new ArrayList<>();
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

        picUri=getImageUri(ItemSellActivity.this,bm);
        return picUri;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
            {
                try {
                    picUri = onSelectFromGalleryResult(data);
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                      sellBitmapImages.add(bitmap);
                   setImagesAdapter();


            }
                catch (Exception e){


                }
            }
            else if (requestCode == REQUEST_CAMERA) {
                try {

                    picUri = onCaptureImageResult(data);
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);

                    sellBitmapImages.add(bitmap);
                   setImagesAdapter();

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
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 5, bytes);

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

        picUri =getImageUri(ItemSellActivity.this,thumbnail);
        //ivImage.setImage_bitmap(thumbnail);
        return picUri;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 5, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", "hhhhh");
        return Uri.parse(path);
    }

}
