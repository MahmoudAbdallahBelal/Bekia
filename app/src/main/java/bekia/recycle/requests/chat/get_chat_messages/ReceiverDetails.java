package bekia.recycle.requests.chat.get_chat_messages;

import com.google.gson.annotations.SerializedName;

public class ReceiverDetails {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
