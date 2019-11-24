package bekia.recycle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.GlideApp;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.web.ApiClient;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private List<CategoryDetails> categoriesList;
    Context mContext;
    OnItemClickListener onItemClickListener ;
//    Integer[] categoriesImages={R.drawable.main_electronic , R.drawable.main_garbage};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryName;
        public ImageView imageCategory;


        public MyViewHolder(View view) {
            super(view);
            txtCategoryName = (TextView) view.findViewById(R.id.text_category);
            imageCategory =   (ImageView) view.findViewById(R.id.image_category);
        }
    }

    public CategoriesAdapter(List<CategoryDetails> categoriesList, Context context , OnItemClickListener onItemClickListener ) {
        this.categoriesList = categoriesList;
        this.mContext = context ;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtCategoryName.setText(categoriesList.get(position).getName());


      String urlImage =  ApiClient.IMAGES_URL+categoriesList.get(position).getImage();
//             "https://data.whicdn.com/images/55631181/superthumb.jpg";
        GlideApp.with(mContext)
                .load(urlImage)
                .fitCenter()
                .into(holder.imageCategory);

       // Toast.makeText(mContext, ""+urlImage, Toast.LENGTH_SHORT).show();

        /* Picasso.get()
                .load(ApiClient.IMAGES_URL+categoriesList.get(position).getImage())
                 .resize(6000, 2000)
                 .onlyScaleDown()
                 .fit().centerCrop()
                 .into(holder.imageCategory);
         */

//       holder.imageCategory.setBackgroundResource(categoriesImages[position]);

        holder.txtCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(categoriesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }





}