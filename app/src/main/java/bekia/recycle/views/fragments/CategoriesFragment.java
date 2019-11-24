package bekia.recycle.views.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.CategoriesAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.views.home.HomeActivity;
import bekia.recycle.views.sub_categories.SubCategoriesActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    ProgressBar progressBarGetCategories;
    RecyclerView recyclerViewCategories;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);


        recyclerViewCategories = view.findViewById(R.id.recycle_categories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategories.setLayoutManager(mLayoutManager);
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        progressBarGetCategories = view.findViewById(R.id.progress_get_categories);


        getCategories();
        return view;
    }

    CategoriesAdapter categoriesAdapter ;
    List<CategoryDetails> categoriesList;
    private void getCategories()
    {

        //defaultSelected();
        progressBarGetCategories.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesResponse> call = apiService.getCategoriesApi("en");
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse>call, Response<CategoriesResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarGetCategories.setVisibility(View.GONE);

                if(response.raw().code() == 200)
                {
                    categoriesList = response.body().getCategories();
                    categoriesAdapter = new CategoriesAdapter(categoriesList, getActivity(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {

                            Intent intent = new Intent(getActivity() , SubCategoriesActivity.class);
                            intent.putExtra("category_id" , item.getId());
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
                    recyclerViewCategories.setAdapter(categoriesAdapter);





                }


            }

            @Override
            public void onFailure(Call<CategoriesResponse>call, Throwable t) {
                progressBarGetCategories.setVisibility(View.GONE);

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
