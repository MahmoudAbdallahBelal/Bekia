package bekia.recycle.views.chat.user_chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.ChatItemsUsersAdapter;
import bekia.recycle.adapters.ChatMessagesAdapter;
import bekia.recycle.helper.Constants;
import bekia.recycle.helper.OnItemChatsClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.chat.get_chat_messages.GetChatMessagesResponse;
import bekia.recycle.requests.chat.get_user_chats.ChatsDetails;
import bekia.recycle.requests.chat.get_user_chats.GetUserChatsResponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.chat.create_chat_message.CreateChatMessageActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class UserChatsActivity extends AppCompatActivity {

    private ProgressBar progressBarUserItemChats;
    private RecyclerView recyclerViewChats;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chats);
        recyclerViewChats = findViewById(R.id.recycle_user_items_chats);
        progressBarUserItemChats = findViewById(R.id.progress_user_items_chats);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewChats.setLayoutManager(mLayoutManager);
        recyclerViewChats.setItemAnimator(new DefaultItemAnimator());

        getUserChats();
    }

    private List<ChatsDetails> chatsDetailsList = new ArrayList<>();
    private ChatItemsUsersAdapter chatMessagesAdapter;
    private void getUserChats()
    {
        progressBarUserItemChats.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token = loginResponse.getToken_type() + " "+loginResponse.getAccess_token();

        Call<GetUserChatsResponse> call = apiService.getUserChatsApi(token ,
                retrieveUserLanguage(getApplicationContext()));
        call.enqueue(new Callback<GetUserChatsResponse>() {
            @Override
            public void onResponse(Call<GetUserChatsResponse>call, final Response<GetUserChatsResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarUserItemChats.setVisibility(View.GONE);

                if(response.raw().code() == 200)
                {
                    chatsDetailsList =  response.body().getChatsDetails();

                    chatMessagesAdapter = new ChatItemsUsersAdapter(chatsDetailsList, UserChatsActivity.this, new OnItemChatsClickListener() {
                        @Override
                        public void onItemClick(ChatsDetails item) {

                            Constants.chatsDetails = item;
                            Intent in = new Intent(UserChatsActivity.this , GetChatItemActivity.class);
                            in.putExtra("item_user_id" ,response.body().getChatsDetails().get(0).getItemUserId() );
                            in.putExtra("client_id" ,response.body().getChatsDetails().get(0).getClientId() );

                            startActivity(in);
                        }
                    });

                    recyclerViewChats.setAdapter(chatMessagesAdapter);





                }


            }

            @Override
            public void onFailure(Call<GetUserChatsResponse>call, Throwable t) {
                progressBarUserItemChats.setVisibility(View.GONE);

                Toast.makeText(UserChatsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }




}
