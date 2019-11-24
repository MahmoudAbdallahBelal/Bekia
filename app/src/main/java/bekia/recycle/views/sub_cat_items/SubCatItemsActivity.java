package bekia.recycle.views.sub_cat_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.SubCategoriesAdapter;
import bekia.recycle.adapters.SubCategoriesItemsAdapter;
import bekia.recycle.helper.Constants;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.GetItemsResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.views.after_click_one_category.CategoryItemsActivity;
import bekia.recycle.views.item_details.ItemDetailsActivity;
import bekia.recycle.views.sub_categories.SubCategoriesActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SubCatItemsActivity extends AppCompatActivity {


    int categoryId;
    RecyclerView recyclerSubCategoriesItems;
    ProgressBar progressBarSubCategoriesItems;

    TextView txTNoItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_items);

        recyclerSubCategoriesItems = findViewById(R.id.recycle_sub_categories_items);
        progressBarSubCategoriesItems = findViewById(R.id.progress_get_sub_categories_items);

        txTNoItems = findViewById(R.id.text_no_items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerSubCategoriesItems.setLayoutManager(mLayoutManager);
        recyclerSubCategoriesItems.setItemAnimator(new DefaultItemAnimator());

        if(getIntent().getIntExtra("sub_category_id" , 0)!= 0)
        {
            //TODO get sub-categories
            categoryId = getIntent().getIntExtra("sub_category_id" , 0);
            getSubCategoriesItems();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getIntExtra("sub_category_id" , 0)!= 0)
        {
            //TODO get sub-categories
            categoryId = getIntent().getIntExtra("sub_category_id" , 0);
            getSubCategoriesItems();

        }
    }

    SubCategoriesItemsAdapter subCategoriesAdapter ;
    List<ItemDetailsReponse> subCategoriesList;
    private void getSubCategoriesItems()
    {

        progressBarSubCategoriesItems.setVisibility(View.VISIBLE);
        recyclerSubCategoriesItems.setVisibility(View.GONE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemsResponse> call = apiService.getAllItemsApi(categoryId ,"en");
        call.enqueue(new Callback<GetItemsResponse>() {
            @Override
            public void onResponse(Call<GetItemsResponse>call, Response<GetItemsResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarSubCategoriesItems.setVisibility(View.GONE);
                recyclerSubCategoriesItems.setVisibility(View.VISIBLE);

                if(response.raw().code() == 200)
                {
                    subCategoriesList = response.body().getItems();
                    subCategoriesAdapter = new SubCategoriesItemsAdapter(subCategoriesList, SubCatItemsActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {


                        }

                        @Override
                        public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                            try {
                                 Constants.itemDetails = new ItemDetailsReponse();
                                Constants.itemDetails = item;
                                Intent intent = new Intent(SubCatItemsActivity.this, ItemDetailsActivity.class);
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

                    if(subCategoriesList.size() == 0)
                    {
                        txTNoItems.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txTNoItems.setVisibility(View.GONE);
                    }
                    recyclerSubCategoriesItems.setAdapter(subCategoriesAdapter);

                }


            }

            @Override
            public void onFailure(Call<GetItemsResponse>call, Throwable t) {
                progressBarSubCategoriesItems.setVisibility(View.GONE);
                recyclerSubCategoriesItems.setVisibility(View.VISIBLE);
                Toast.makeText(SubCatItemsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
