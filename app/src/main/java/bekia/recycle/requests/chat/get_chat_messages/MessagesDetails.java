package bekia.recycle.requests.chat.get_chat_messages;

import com.google.gson.annotations.SerializedName;

public class MessagesDetails {

    @SerializedName("chat_id")
    private int chat_id;

    public SenderDetails getSender() {
        return sender;
    }

    public void setSender(SenderDetails sender) {
        this.sender = sender;
    }

    public ReceiverDetails getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverDetails receiver) {
        this.receiver = receiver;
    }

    @SerializedName("sender")
    private SenderDetails sender;

    @SerializedName("receiver")
    private ReceiverDetails receiver;

    @SerializedName("message")
    private String message;

    @SerializedName("sender_id")
    private int senderId;

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    @SerializedName("receiver_id")
    private int receiverId;



}
