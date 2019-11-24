package bekia.recycle.views.sub_categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.CategoriesAdapter;
import bekia.recycle.adapters.SubCategoriesAdapter;
import bekia.recycle.adapters.SubCategoriesItemsAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.views.home.HomeActivity;
import bekia.recycle.views.sub_cat_items.SubCatItemsActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;

public class SubCategoriesActivity extends AppCompatActivity {

    int categoryId;
    RecyclerView recyclerSubCategories;
    ProgressBar progressBarSubCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        recyclerSubCategories = findViewById(R.id.recycle_sub_categories);
        progressBarSubCategories = findViewById(R.id.progress_get_sub_categories);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerSubCategories.setLayoutManager(mLayoutManager);
        recyclerSubCategories.setItemAnimator(new DefaultItemAnimator());

        if(getIntent().getIntExtra("category_id" , 0)!= 0)
        {
            //TODO get sub-categories
            categoryId = getIntent().getIntExtra("category_id" , 0);
            getSubCategories();

        }



    }

    SubCategoriesAdapter subCategoriesAdapter ;
    List<CategoryDetails> subCategoriesList;
    private void getSubCategories()
    {

        progressBarSubCategories.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesResponse> call = apiService.getSubCategoriesApi(categoryId ,retrieveUserLanguage(getApplicationContext()));
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse>call, Response<CategoriesResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarSubCategories.setVisibility(View.GONE);

                if(response.raw().code() == 200)
                {
                    subCategoriesList = response.body().getCategories();
                    subCategoriesAdapter = new SubCategoriesAdapter(subCategoriesList, SubCategoriesActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {
                            Intent intent = new Intent(SubCategoriesActivity.this , SubCatItemsActivity.class);
                            intent.putExtra("sub_category_id" , item.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                        }

                        @Override
                        public void onItemDelete(ItemDetailsReponse item) {

                        }

                    });
                    // Toast.makeText(RegisterActivity.this, ""+response.body().getCities(), Toast.LENGTH_SHORT).show();
                    //goToLogin();

                    recyclerSubCategories.setAdapter(subCategoriesAdapter);





                }


            }

            @Override
            public void onFailure(Call<CategoriesResponse>call, Throwable t) {
                progressBarSubCategories.setVisibility(View.GONE);

                Toast.makeText(SubCategoriesActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
