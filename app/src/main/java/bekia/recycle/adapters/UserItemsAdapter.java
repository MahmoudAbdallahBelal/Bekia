package bekia.recycle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.requests.items_response.ItemDetailsReponse;

public class UserItemsAdapter extends RecyclerView.Adapter<UserItemsAdapter.MyViewHolder> {

    private List<ItemDetailsReponse> itemDetailsReponseList;
    Context mContext;
    OnItemClickListener onItemClickListener ;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName , itemDesc;
        public ImageView imageViewItem , imgDelete;



        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.text_item_name);
            itemDesc =   (TextView) view.findViewById(R.id.text_item_desc);
            imageViewItem = (ImageView)view.findViewById(R.id.image_item_);
            imgDelete = (ImageView)view.findViewById(R.id.image_delete_item);
        }
    }

    public UserItemsAdapter(List<ItemDetailsReponse> itemDetailsReponses, Context context , OnItemClickListener onItemClickListener ) {
        this.itemDetailsReponseList = itemDetailsReponses;
        this.mContext = context ;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.itemName.setText(itemDetailsReponseList.get(position).getName());
        holder.itemDesc.setText(itemDetailsReponseList.get(position).getDesc());

       Bitmap bitmap = StringToBitMap(itemDetailsReponseList.get(position).getImages().get(0).getImage());
       if(bitmap != null)
       holder.imageViewItem.setImageBitmap(bitmap);

        holder.itemDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemOfSubCategoryClick(itemDetailsReponseList.get(position));

            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemDelete(itemDetailsReponseList.get(position));

            }
        });



    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return itemDetailsReponseList.size();
    }





}