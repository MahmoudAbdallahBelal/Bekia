package bekia.recycle.requests.city;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResponse {
    @SerializedName("cities")
    private List<CityDetails> Cities;

    public List<CityDetails> getCities() {
        return Cities;
    }

    public void setCities(List<CityDetails> cities) {
        Cities = cities;
    }

    @SerializedName("code")
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
