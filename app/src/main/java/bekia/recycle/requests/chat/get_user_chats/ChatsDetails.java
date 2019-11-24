package bekia.recycle.requests.chat.get_user_chats;

import com.google.gson.annotations.SerializedName;

public class ChatsDetails {

    @SerializedName("id")
    private int chatId;


    @SerializedName("item_id")
    private int itemId;


    @SerializedName("client_id")
    private int clientId;

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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(int itemUserId) {
        this.itemUserId = itemUserId;
    }

    public ItemDetailsWithChat getItemDetailsWithChat() {
        return itemDetailsWithChat;
    }

    public void setItemDetailsWithChat(ItemDetailsWithChat itemDetailsWithChat) {
        this.itemDetailsWithChat = itemDetailsWithChat;
    }

    @SerializedName("item_user_id")
    private int itemUserId;

    @SerializedName("item")
    private ItemDetailsWithChat itemDetailsWithChat;
}
