package bekia.recycle.views.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.MostPopularItemsAdapter;
import bekia.recycle.adapters.SubCategoriesItemsAdapter;
import bekia.recycle.helper.Constants;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.GetItemsResponse;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.views.item_details.ItemDetailsActivity;
import bekia.recycle.views.sub_cat_items.SubCatItemsActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {



    private View mView;

    RecyclerView recycleMostPopular;
    ProgressBar progressBarMostPopular;

    TextView txTNoItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_most_popular, container, false);
        recycleMostPopular = mView.findViewById(R.id.recycle_most_popular_items);
        progressBarMostPopular = mView.findViewById(R.id.progress_most_popular_items);

        txTNoItems = mView.findViewById(R.id.text_no_most_popular_items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recycleMostPopular.setLayoutManager(mLayoutManager);
        recycleMostPopular.setItemAnimator(new DefaultItemAnimator());


    return  mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        getMostPopularItems();

    }

    MostPopularItemsAdapter mostPopularItemsAdapter ;
    List<ItemDetailsReponse> mostPopularList;
    private void getMostPopularItems()
    {

        progressBarMostPopular.setVisibility(View.VISIBLE);
        recycleMostPopular.setVisibility(View.GONE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemsResponse> call = apiService.getMostPopularApi( 3,"en");
        call.enqueue(new Callback<GetItemsResponse>() {
            @Override
            public void onResponse(Call<GetItemsResponse>call, Response<GetItemsResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarMostPopular.setVisibility(View.GONE);
                recycleMostPopular.setVisibility(View.VISIBLE);


                if(response.raw().code() == 200)
                {
                    mostPopularList = response.body().getItems();
                    mostPopularItemsAdapter = new MostPopularItemsAdapter(mostPopularList,
                            getActivity(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(CategoryDetails item) {


                        }

                        @Override
                        public void onItemOfSubCategoryClick(ItemDetailsReponse item) {

                            try {
                                Constants.itemDetails = new ItemDetailsReponse();
                               Constants. itemDetails = item;
                                Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
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

                    if(mostPopularList.size() == 0)
                    {
                        txTNoItems.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        txTNoItems.setVisibility(View.GONE);
                    }
                    recycleMostPopular.setAdapter(mostPopularItemsAdapter);





                }


            }

            @Override
            public void onFailure(Call<GetItemsResponse>call, Throwable t) {
                progressBarMostPopular.setVisibility(View.GONE);
                recycleMostPopular.setVisibility(View.VISIBLE);


                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
