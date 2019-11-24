package bekia.recycle.requests.categories;

import com.google.gson.annotations.SerializedName;

import bekia.recycle.web.ApiClient;

public class CategoryDetails {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public String getImage() {


        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
