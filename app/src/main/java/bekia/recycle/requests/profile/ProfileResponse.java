package bekia.recycle.requests.profile;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("user_info")
    private UserInfoObjectDetails userInfo;


    @SerializedName("code")
    private String code;

    public UserInfoObjectDetails getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoObjectDetails userInfo) {
        this.userInfo = userInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
