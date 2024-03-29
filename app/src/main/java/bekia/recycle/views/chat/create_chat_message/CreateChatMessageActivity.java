package bekia.recycle.views.chat.create_chat_message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bekia.recycle.R;
import bekia.recycle.adapters.ChatMessagesAdapter;
import bekia.recycle.adapters.SubCategoriesAdapter;
import bekia.recycle.helper.OnItemClickListener;
import bekia.recycle.helper.Utils;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.chat.create_chat_message.CreateChatMessageRequest;
import bekia.recycle.requests.chat.create_chat_message.CreateChatMessageResponse;
import bekia.recycle.requests.chat.get_chat_messages.GetChatMessagesResponse;
import bekia.recycle.requests.chat.get_chat_messages.MessagesDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.views.LocaleManager;
import bekia.recycle.views.sub_cat_items.SubCatItemsActivity;
import bekia.recycle.views.sub_categories.SubCategoriesActivity;
import bekia.recycle.web.ApiClient;
import bekia.recycle.web.ApiInterface;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.util.ArrayList;
import java.util.List;

import static bekia.recycle.helper.Utils.retrieveUserLanguage;
import static bekia.recycle.views.settings.SettingsActivity.lan;

public class CreateChatMessageActivity extends LocalizationActivity {

    private RecyclerView recyclerViewChat;
    private ImageButton imgSendMessage;
    private EditText typeMessageEdit;
    private ProgressBar progressBarLoadMessages;
    private List<MessagesDetails> chatMessagesResponseList = new ArrayList<>();
    private ChatMessagesAdapter chatMessagesAdapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base,lan));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage(lan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat_message);

        recyclerViewChat =findViewById(R.id.recycle_chat);
        imgSendMessage = findViewById(R.id.image_button_send_message);
        typeMessageEdit = findViewById(R.id.edit_type_message);
        progressBarLoadMessages = findViewById(R.id.progress_load_messages);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewChat.setLayoutManager(mLayoutManager);
        recyclerViewChat.setItemAnimator(new DefaultItemAnimator());


        getChatMessages();



        imgSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();
            }
        });

    }


    private void sendMessage()
    {
        progressBarLoadMessages.setVisibility(View.VISIBLE);
        CreateChatMessageRequest createChatMessageRequest = new CreateChatMessageRequest();
        createChatMessageRequest.setChatId(getIntent().getIntExtra("chat_id", 0));
        createChatMessageRequest.setMessage(""+typeMessageEdit.getText().toString());

        int userItemId= Utils.retrieveChatResponse(this).getChat().getItemUserId();
        int clientId= Utils.retrieveChatResponse(this).getChat().getClientId();
        int currentUserId = Utils.retrieveUserInfoForId(this).getUserInfo().getId();

        if(currentUserId == userItemId )
         createChatMessageRequest.setReceiverId(clientId);
        if(currentUserId == clientId )
            createChatMessageRequest.setReceiverId(userItemId);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token = loginResponse.getToken_type() + " "+loginResponse.getAccess_token();

        Call<CreateChatMessageResponse> call = apiService.createChatMessageApi(token ,createChatMessageRequest);
        call.enqueue(new Callback<CreateChatMessageResponse>() {
            @Override
            public void onResponse(Call<CreateChatMessageResponse>call, Response<CreateChatMessageResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarLoadMessages.setVisibility(View.GONE);

                if(response.raw().code() == 200)
                {
                    getChatMessages();
                }


            }

            @Override
            public void onFailure(Call<CreateChatMessageResponse>call, Throwable t) {
                progressBarLoadMessages.setVisibility(View.GONE);

                Toast.makeText(CreateChatMessageActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();

        getChatMessages();
    }

    private void getChatMessages()
    {
        progressBarLoadMessages.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        LoginResponse loginResponse = Utils.retrieveUserInfo(this);
        String token = loginResponse.getToken_type() + " "+loginResponse.getAccess_token();

        Call<GetChatMessagesResponse> call = apiService.getChatMessagesApi(token ,
                retrieveUserLanguage(getApplicationContext()) ,
                getIntent().getIntExtra("chat_id",0));
        call.enqueue(new Callback<GetChatMessagesResponse>() {
            @Override
            public void onResponse(Call<GetChatMessagesResponse>call, Response<GetChatMessagesResponse> response) {
                // List<String> movies = response.body();
                // Log.d(TAG, "Number of movies received: " + movies.size());
                progressBarLoadMessages.setVisibility(View.GONE);

                if(response.raw().code() == 200)
                {
                    chatMessagesResponseList =  response.body().getMessagesDetails();

                    chatMessagesAdapter = new ChatMessagesAdapter(chatMessagesResponseList , CreateChatMessageActivity.this);

                    recyclerViewChat.setAdapter(chatMessagesAdapter);





                }


            }

            @Override
            public void onFailure(Call<GetChatMessagesResponse>call, Throwable t) {
                progressBarLoadMessages.setVisibility(View.GONE);

                Toast.makeText(CreateChatMessageActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
