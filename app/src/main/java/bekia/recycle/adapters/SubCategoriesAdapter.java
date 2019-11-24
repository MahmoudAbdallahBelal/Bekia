package bekia.recycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.web.ApiClient;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyViewHolder> {

    private List<CategoryDetails> subCategoriesList;
    Context mContext;
    OnItemClickListener onItemClickListener ;
    //Integer[] categoriesImages={R.drawable.main_electronic , R.drawable.main_garbage};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryName;
        public ImageView imageCategory;


        public MyViewHolder(View view) {
            super(view);
            txtCategoryName = (TextView) view.findViewById(R.id.text_sub_category);
            imageCategory =   (ImageView) view.findViewById(R.id.image_sub_category);
        }
    }

    public SubCategoriesAdapter(List<CategoryDetails> subCategoriesList, Context context , OnItemClickListener onItemClickListener ) {
        this.subCategoriesList = subCategoriesList;
        this.mContext = context ;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txtCategoryName.setText(subCategoriesList.get(position).getName());

         Picasso.get()
                .load(ApiClient.IMAGES_URL+subCategoriesList.get(position).getImage())
                .into(holder.imageCategory);

      // holder.imageCategory.setBackgroundResource(categoriesImages[position]);

        holder.txtCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(subCategoriesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoriesList.size();
    }





}