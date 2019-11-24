package bekia.recycle.views.item_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bekia.recycle.R;
import bekia.recycle.adapters.ItemImagesAdapterAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.chat.create_chat.CreateChatResponse;
import bekia.recycle.requests.favorite_like_unlike.LikeUnlikeResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.chat.create_chat_message.CreateChatMessageActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bekia.recycle.helper.Constants.itemDetails;


public class ItemDetailsActivity extends AppCompatActivity {


    RecyclerView recyclerViewImagesItem;
    private TextView txtName, txtPhone, txtDesc, txtIsNegotiable, txtPrice, txtNoImages;
    private ImageView imgCall, imgLikeAndDisLike, imgChat;

    boolean isFavourite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        recyclerViewImagesItem = findViewById(R.id.recycle_item_images);
        imgCall = findViewById(R.id.image_call_item_phone);
        txtDesc = findViewById(R.id.text_desc_item_details);
        txtIsNegotiable = findViewById(R.id.text_is_negotiable_item_details);
        txtPrice = findViewById(R.id.text_price_item_details);
        txtPhone = findViewById(R.id.text_phone_item_details);
        txtName = findViewById(R.id.text_name_item_details);
        imgLikeAndDisLike = findViewById(R.id.image_like_item);
        txtNoImages = findViewById(R.id.text_no_images);
        imgChat = findViewById(R.id.image_chat_item);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewImagesItem.setLayoutManager(layoutManager);
        recyclerViewImagesItem.setItemAnimator(new DefaultItemAnimator());

        handleImagesAdapter();

        if (itemDetails.getLikes() == 0) {
            imgLikeAndDisLike.setBackground(getResources().getDrawable(R.drawable.ic_unfavourite));
            isFavourite = false;
        } else {
            imgLikeAndDisLike.setBackground(getResources().getDrawable(R.drawable.ic_favourite_));
            isFavourite = true;
        }
        txtName.setText(itemDetails.getName());
        txtDesc.setText(itemDetails.getDesc());
        txtPhone.setText(itemDetails.getDisplayed_phone());
        txtPrice.setText(itemDetails.getPrice());
        if (itemDetails.isIs_negotiable() == 1)
            txtIsNegotiable.setText(getString(R.string.negtiable));
        else
            txtIsNegotiable.setText(getString(R.string.notNegtiable));

        final String phoneNumber = itemDetails.getDisplayed_phone();

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

            }
        });


        imgLikeAndDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setLikeAndDisLike();

            }
        });


        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChat();
            }
        });
    }

    private void createChat() {
        // call create chat api (done)
        //open chat screen  ()
        //make sure it is not your item to set chat visibility gone

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token = loginResponse.getToken_type() + " " + loginResponse.getAccess_token();
        Call<CreateChatResponse> call = apiService.createChatApi(token, "en", itemDetails.getItemId());
        call.enqueue(new Callback<CreateChatResponse>() {
            @Override
            public void onResponse(Call<CreateChatResponse> call, Response<CreateChatResponse> response) {
                if (response.raw().code() == 200) {

                    Utils.saveCreateChatResponse(ItemDetailsActivity.this, response.body());
                    int currentId = Utils.retrieveUserInfoForId(ItemDetailsActivity.this).
                            getUserInfo().getId();

                    if (currentId == response.body().getChat().getItemUserId()) {
                        imgChat.setVisibility(View.GONE);
                        Toast.makeText(ItemDetailsActivity.this, getString(R.string.thisYourItem),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(ItemDetailsActivity.this,
                                CreateChatMessageActivity.class);
                        intent.putExtra("chat_id", response.body().getChat().getChatId());
                        startActivity(intent);
                    }
                }


            }

            @Override
            public void onFailure(Call<CreateChatResponse> call, Throwable t) {

                Toast.makeText(ItemDetailsActivity.this, "" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean isItemOwner() {


        return false;
    }


    private void setLikeAndDisLike() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token = loginResponse.getToken_type() + " " + loginResponse.getAccess_token();
        Call<LikeUnlikeResponse> call = apiService.likeAndUnlikeApi(token, itemDetails.getItemId(), "en");
        call.enqueue(new Callback<LikeUnlikeResponse>() {
            @Override
            public void onResponse(Call<LikeUnlikeResponse> call, Response<LikeUnlikeResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());

                if (response.raw().code() == 200) {
                    if (isFavourite) {
                        isFavourite = false;
                        imgLikeAndDisLike.setBackground(getResources().getDrawable(R.drawable.ic_unfavourite));
                    } else {
                        isFavourite = true;
                        imgLikeAndDisLike.setBackground(getResources().getDrawable(R.drawable.ic_favourite_));

                    }
                }


            }

            @Override
            public void onFailure(Call<LikeUnlikeResponse> call, Throwable t) {

                Toast.makeText(ItemDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    List<Bitmap> listBitmapImages = new ArrayList<>();

    private void handleImagesAdapter() {


//       if( itemDetails.getImages()!= null)
//       {
//         txtNoImages.setVisibility(View.GONE);
//       }
//       else
//       {
//           txtNoImages.setVisibility(View.VISIBLE);
//
//       }

        for (int i = 0; i < itemDetails.getImages().size(); i++) {
            Bitmap bitmap = StringToBitMap(itemDetails.getImages().get(i).getImage());
            listBitmapImages.add(bitmap);
        }

        ItemImagesAdapterAdapter itemImagesAdapterAdapter = new ItemImagesAdapterAdapter(listBitmapImages,
                ItemDetailsActivity.this, new OnItemClickListener() {
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

        recyclerViewImagesItem.setAdapter(itemImagesAdapterAdapter);


    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}
