package bekia.recycle.requests.add_item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddNewItemRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("displayed_phone")
    private String displayed_phone;

    @SerializedName("category_id")
    private int category_id;

    public boolean isIs_negotiable() {
        return is_negotiable;
    }

    public void setIs_negotiable(boolean is_negotiable) {
        this.is_negotiable = is_negotiable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @SerializedName("city_id")
    private int city_id;

    @SerializedName("is_negotiable")
    private boolean is_negotiable;

    @SerializedName("price")
    private String price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDisplayed_phone() {
        return displayed_phone;
    }

    public void setDisplayed_phone(String displayed_phone) {
        this.displayed_phone = displayed_phone;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @SerializedName("images")
    private List<String> images;






}
