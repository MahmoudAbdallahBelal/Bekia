package bekia.recycle.requests.chat.get_user_chats;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserChatsResponse {
    @SerializedName("chats")
    private List<ChatsDetails> chatsDetails;

    public List<ChatsDetails> getChatsDetails() {
        return chatsDetails;
    }

    public void setChatsDetails(List<ChatsDetails> chatsDetails) {
        this.chatsDetails = chatsDetails;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @SerializedName("code")
    private String code;
}
