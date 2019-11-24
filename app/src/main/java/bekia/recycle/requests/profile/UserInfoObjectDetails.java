package bekia.recycle.requests.profile;

import com.google.gson.annotations.SerializedName;

import bekia.recycle.requests.items_response.CityItemDetails;

public class UserInfoObjectDetails {

    @SerializedName("id")
    private int id;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("type")
    private int userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("email")
    private String email;

    public CityItemDetails getCity() {
        return city;
    }

    public void setCity(CityItemDetails city) {
        this.city = city;
    }

    @SerializedName("phone")
    private String phone;

    @SerializedName("city")
    private  CityItemDetails city;
}
