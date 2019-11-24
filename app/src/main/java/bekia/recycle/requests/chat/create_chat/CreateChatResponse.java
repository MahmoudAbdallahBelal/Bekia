package bekia.recycle.requests.chat.create_chat;

import com.google.gson.annotations.SerializedName;

public class CreateChatResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CreateChatReponseDetails getChat() {
        return chat;
    }

    public void setChat(CreateChatReponseDetails chat) {
        this.chat = chat;
    }

    @SerializedName("chat")
    private CreateChatReponseDetails chat;



}
