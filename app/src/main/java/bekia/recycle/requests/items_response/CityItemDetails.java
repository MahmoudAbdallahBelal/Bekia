package bekia.recycle.requests.items_response;


import com.google.gson.annotations.SerializedName;

public class CityItemDetails {
    @SerializedName("name_en")
    private  String nameEn;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
