package bekia.recycle.views.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.SubCategoriesItemsAdapter;
import bekia.recycle.helper.Constants;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.GetItemsResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.item_details.ItemDetailsActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEdit;
    private ImageButton searchBtn;
    private RecyclerView recyclerViewSearchItems;
    private TextView notFoundTxt;
    private ProgressBar progressBarSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBtn = findViewById(R.id.button_search);
        searchEdit = findViewById(R.id.edit_search_name);
        recyclerViewSearchItems = findViewById(R.id.recycle_search_items);
        notFoundTxt = findViewById(R.id.text_not_found);
        progressBarSearch = findViewById(R.id.progress_search);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewSearchItems.setLayoutManager(mLayoutManager);
        recyclerViewSearchItems.setItemAnimator(new DefaultItemAnimator());

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItems();
            }
        });

    }

    SubCategoriesItemsAdapter userItemsAdapter ;
    List<ItemDetailsReponse> userItemsList;
    private void searchItems()
    {

        progressBarSearch.setVisibility(View.VISIBLE);
        recyclerViewSearchItems.setVisibility(View.GONE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token =   loginResponse.getToken_type() +" "+loginResponse.getAccess_token();
        Call<GetItemsResponse> call = apiService.searchItemApi(searchEdit.getText().toString(),"0" , "0");
        call.enqueue(new Callback<GetItemsResponse>() {
            @Override
            public void onResponse(Call<GetItemsResponse>call, Response<GetItemsResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarSearch.setVisibility(View.GONE);
                progressBarSearch.setVisibility(View.VISIBLE);

                if(response.raw().code() == 200)
                {
                    userItemsList = response.body().getItems();
                    userItemsAdapter = new SubCategoriesItemsAdapter(userItemsList, SearchActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {

                        }

                        @Override
                        public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                            try {
                                Constants.itemDetails = new ItemDetailsReponse();
                                Constants.itemDetails = item;
                                Intent intent = new Intent(SearchActivity.this, ItemDetailsActivity.class);
                                startActivity(intent);
                            }catch (Exception e)
                            {
                                e.getMessage();
                            }
                        }

                        @Override
                        public void onItemDelete(ItemDetailsReponse item) {

                        }
                    });

                    if(userItemsList.size() == 0)
                    {
                        notFoundTxt.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        notFoundTxt.setVisibility(View.GONE);
                    }
                    recyclerViewSearchItems.setAdapter(userItemsAdapter);

                }


            }

            @Override
            public void onFailure(Call<GetItemsResponse>call, Throwable t) {
                progressBarSearch.setVisibility(View.GONE);
                recyclerViewSearchItems.setVisibility(View.VISIBLE);
                Toast.makeText(SearchActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
