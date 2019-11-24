package bekia.recycle.requests.chat.get_user_chats;

import com.google.gson.annotations.SerializedName;

public class ItemDetailsWithChat {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
