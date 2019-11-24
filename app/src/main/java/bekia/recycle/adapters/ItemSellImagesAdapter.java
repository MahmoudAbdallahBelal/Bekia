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
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.categories.CategoryDetails;

public class ItemSellImagesAdapter extends RecyclerView.Adapter<ItemSellImagesAdapter.MyViewHolder> {

    private List<Bitmap> sellItemImages;
    Context mContext;
    OnItemClickListener onItemClickListener ;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageSellItem;

        public MyViewHolder(View view) {
            super(view);
            imageSellItem = (ImageView) view.findViewById(R.id.image_sell_item);

        }
    }

    public ItemSellImagesAdapter(List<Bitmap> sellItemImages, Context context , OnItemClickListener onItemClickListener ) {
        this.sellItemImages = sellItemImages;
        this.mContext = context ;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sell_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imageSellItem.setImageBitmap(sellItemImages.get(position));
        holder.imageSellItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return sellItemImages.size();
    }





}