package bekia.recycle.requests.forget_password;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordRq {
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
