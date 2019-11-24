package bekia.recycle.requests.chat.get_chat_messages;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetChatMessagesResponse {
    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MessagesDetails> getMessagesDetails() {
        return messagesDetails;
    }

    public void setMessagesDetails(List<MessagesDetails> messagesDetails) {
        this.messagesDetails = messagesDetails;
    }

    @SerializedName("messages")
    private List<MessagesDetails> messagesDetails;
}
