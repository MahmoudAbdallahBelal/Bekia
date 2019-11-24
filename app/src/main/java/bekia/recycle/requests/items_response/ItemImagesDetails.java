package bekia.recycle.requests.items_response;

import com.google.gson.annotations.SerializedName;

public class ItemImagesDetails {

    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
