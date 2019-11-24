package bekia.recycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.helper.OnItemChatsClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.chat.get_chat_messages.MessagesDetails;
import bekia.recycle.requests.chat.get_user_chats.ChatsDetails;

public class ChatItemsUsersAdapter extends RecyclerView.Adapter<ChatItemsUsersAdapter.MyViewHolder> {

    private List<ChatsDetails> chatsList;
    Context mContext;
    OnItemChatsClickListener onItemChatsClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView chatItemTxt;


        public MyViewHolder(View view) {
            super(view);
            chatItemTxt = (TextView) view.findViewById(R.id.text_chats_items);
        }
    }

    public ChatItemsUsersAdapter(List<ChatsDetails> chatsDetails, Context context , OnItemChatsClickListener onItemChatsClickListener ) {
        this.chatsList = chatsDetails;
        this.mContext = context ;
        this.onItemChatsClickListener = onItemChatsClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_chats, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.chatItemTxt.setText(chatsList.get(position).getItemDetailsWithChat().getName());
        holder.chatItemTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChatsClickListener.onItemClick(chatsList.get(position));
            }
        });



    }



    @Override
    public int getItemCount() {
        return chatsList.size();
    }





}