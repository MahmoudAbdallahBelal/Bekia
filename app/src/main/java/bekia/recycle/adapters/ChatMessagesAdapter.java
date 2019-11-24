package bekia.recycle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.chat.get_chat_messages.GetChatMessagesResponse;
import bekia.recycle.requests.chat.get_chat_messages.MessagesDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.MyViewHolder> {

    private List<MessagesDetails> chatMessagesResponseList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView chatMessageTxt , userNameTxt;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            chatMessageTxt = (TextView) view.findViewById(R.id.text_chat_user);
            userNameTxt = (TextView) view.findViewById(R.id.text_chat_username);
            linearLayout = (LinearLayout) view.findViewById(R.id.layout_chat_item);
        }
    }

    public ChatMessagesAdapter(List<MessagesDetails> getChatMessagesResponses, Context context ) {
        this.chatMessagesResponseList = getChatMessagesResponses;
        this.mContext = context ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.chatMessageTxt.setText(chatMessagesResponseList.get(position).getMessage());

        if(Utils.retrieveUserInfoForId(mContext).getUserInfo().getId() == chatMessagesResponseList.get(position).getSenderId() ) {
//            holder.userNameTxt.setText(chatMessagesResponseList.get(position).getSender().getName());
            holder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorGray));
        }
        else {
            holder.userNameTxt.setText(chatMessagesResponseList.get(position).getSender().getName());
        }



    }



    @Override
    public int getItemCount() {
        return chatMessagesResponseList.size();
    }





}