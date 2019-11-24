package bekia.recycle.requests.items_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetItemsResponse {
    @SerializedName("items")
    private List<ItemDetailsReponse> items ;

    public List<ItemDetailsReponse> getItems() {
        return items;
    }

    public void setItems(List<ItemDetailsReponse> items) {
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @SerializedName("code")
    private String code ;


}
