package bekia.recycle.requests.forget_password.reset;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRq {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;


    @SerializedName("code")
    private String code;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
