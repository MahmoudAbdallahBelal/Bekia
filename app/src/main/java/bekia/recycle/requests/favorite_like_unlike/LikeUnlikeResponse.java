package bekia.recycle.requests.favorite_like_unlike;

import com.google.gson.annotations.SerializedName;

public class LikeUnlikeResponse {

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
