package bekia.recycle.requests.edit_profile;

import com.google.gson.annotations.SerializedName;

public class EditProfileReq {

@SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;


    @SerializedName("email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("city_id")
    private int cityId;
}
