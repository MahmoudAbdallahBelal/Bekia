package bekia.recycle.requests.chat.create_chat_message;

import com.google.gson.annotations.SerializedName;

public class CreateChatMessageRequest {
    @SerializedName("chat_id")
    private int chatId;

    @SerializedName("message")
    private String message;

    @SerializedName("receiver_id")
    private int receiverId;


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }




}
