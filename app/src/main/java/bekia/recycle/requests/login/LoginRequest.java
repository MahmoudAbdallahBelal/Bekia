package bekia.recycle.requests.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("phone")
    private String phone;

    @SerializedName("password")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
