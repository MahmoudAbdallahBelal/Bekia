package bekia.recycle.requests.forget_password.reset;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRes {
    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
