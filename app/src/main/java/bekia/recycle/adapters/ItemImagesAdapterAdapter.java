package bekia.recycle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.GlideApp;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.web.ApiClient;

public class ItemImagesAdapterAdapter extends RecyclerView.Adapter<ItemImagesAdapterAdapter.MyViewHolder> {

    private List<Bitmap> itemImages;
    Context mContext;
    OnItemClickListener onItemClickListener ;
//    Integer[] categoriesImages={R.drawable.main_electronic , R.drawable.main_garbage};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageItem;


        public MyViewHolder(View view) {
            super(view);
            imageItem =   (ImageView) view.findViewById(R.id.image_item_image);
        }
    }

    public ItemImagesAdapterAdapter(List<Bitmap> itemImages, Context context , OnItemClickListener onItemClickListener ) {
        this.itemImages = itemImages;
        this.mContext = context ;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.imageItem.setImageBitmap(itemImages.get(position));
    }

    @Override
    public int getItemCount() {
        return itemImages.size();
    }





}