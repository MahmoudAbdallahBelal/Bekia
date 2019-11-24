package bekia.recycle.requests.chat.create_chat;

import com.google.gson.annotations.SerializedName;

public class CreateChatReponseDetails {
    @SerializedName("id")
    private int chatId;

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("item_user_id")
    private int itemUserId;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(int itemUserId) {
        this.itemUserId = itemUserId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @SerializedName("client_id")
    private int clientId;






}
