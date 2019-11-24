package bekia.recycle.requests.add_item;

import com.google.gson.annotations.SerializedName;

public class AddNewItemResponse {

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
}
