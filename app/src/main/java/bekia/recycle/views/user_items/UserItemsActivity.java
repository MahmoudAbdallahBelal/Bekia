package bekia.recycle.views.user_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.SubCategoriesItemsAdapter;
import bekia.recycle.adapters.UserItemsAdapter;
import bekia.recycle.helper.Constants;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.delete_item.DeleteItemResponse;
import bekia.recycle.requests.delete_item.DeleteItemRquest;
import bekia.recycle.requests.items_response.GetItemsResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.item_details.ItemDetailsActivity;
import bekia.recycle.views.profile.UserProfileActivity;
import bekia.recycle.views.sub_cat_items.SubCatItemsActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class UserItemsActivity extends AppCompatActivity {

    RecyclerView recyclerViewUserItems;
    ProgressBar progressBarUserItems;

    TextView txTNoItems;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_items);

        recyclerViewUserItems = findViewById(R.id.recycle_user_items);
        progressBarUserItems = findViewById(R.id.progress_get_user_items);

        txTNoItems = findViewById(R.id.text_no_items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUserItems.setLayoutManager(mLayoutManager);
        recyclerViewUserItems.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserItems();

    }

    UserItemsAdapter userItemsAdapter ;
    List<ItemDetailsReponse> userItemsList;
    private void getUserItems()
    {

        progressBarUserItems.setVisibility(View.VISIBLE);
        recyclerViewUserItems.setVisibility(View.GONE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
      String token =   loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
        Call<GetItemsResponse> call = apiService.getUserItemsApi( token,retrieveUserLanguage(getApplicationContext()));
        call.enqueue(new Callback<GetItemsResponse>() {
            @Override
            public void onResponse(Call<GetItemsResponse>call, Response<GetItemsResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarUserItems.setVisibility(View.GONE);
                recyclerViewUserItems.setVisibility(View.VISIBLE);

                if(response.raw().code() == 200)
                {
                    userItemsList = response.body().getItems();
                    userItemsAdapter = new UserItemsAdapter(userItemsList, UserItemsActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {


                        }

                        @Override
                        public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                            try {
                                Constants.itemDetails = new ItemDetailsReponse();
                                Constants.itemDetails = item;
                                Intent intent = new Intent(UserItemsActivity.this, ItemDetailsActivity.class);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.getMessage();
                            }
                        }

                        @Override
                        public void onItemDelete(ItemDetailsReponse item) {
                            deleteItem(item.getItemId());
                        }
                    });

                    if(userItemsList.size() == 0)
                    {
                        txTNoItems.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txTNoItems.setVisibility(View.GONE);
                    }
                    recyclerViewUserItems.setAdapter(userItemsAdapter);

                }


            }

            @Override
            public void onFailure(Call<GetItemsResponse>call, Throwable t) {
                progressBarUserItems.setVisibility(View.GONE);
                recyclerViewUserItems.setVisibility(View.VISIBLE);
                Toast.makeText(UserItemsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    DeleteItemRquest deleteItemRquest;
    private void deleteItem(int itemId)
    {
        deleteItemRquest = new DeleteItemRquest();
        deleteItemRquest.setItemId(itemId);

        progressBarUserItems.setVisibility(View.VISIBLE);
        recyclerViewUserItems.setVisibility(View.GONE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token =   loginResponse.getToken_type() +" "+loginResponse.getAccess_token();

        Call<DeleteItemResponse> call = apiService.deleteItemApi( token,
                retrieveUserLanguage(getApplicationContext())
                ,deleteItemRquest);
        call.enqueue(new Callback<DeleteItemResponse>() {
            @Override
            public void onResponse(Call<DeleteItemResponse>call, Response<DeleteItemResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarUserItems.setVisibility(View.GONE);
                recyclerViewUserItems.setVisibility(View.VISIBLE);

                if(response.raw().code() == 200)
                {
                  getUserItems();
                }


            }

            @Override
            public void onFailure(Call<DeleteItemResponse>call, Throwable t) {
                progressBarUserItems.setVisibility(View.GONE);
                recyclerViewUserItems.setVisibility(View.VISIBLE);
                Toast.makeText(UserItemsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
