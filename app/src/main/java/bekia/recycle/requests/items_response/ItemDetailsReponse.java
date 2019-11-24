package bekia.recycle.requests.items_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetailsReponse  {

    @SerializedName("name")
    private String name ;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @SerializedName("id")
    private int itemId ;


    @SerializedName("desc")
    private String desc ;

    @SerializedName("displayed_phone")
    private String displayed_phone ;

    @SerializedName("price")
    private String price ;

    public String getDisplayed_phone() {
        return displayed_phone;
    }

    public void setDisplayed_phone(String displayed_phone) {
        this.displayed_phone = displayed_phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int isIs_negotiable() {
        return is_negotiable;
    }

    public void setIs_negotiable(int is_negotiable) {
        this.is_negotiable = is_negotiable;
    }

    @SerializedName("is_negotiable")
    private int is_negotiable ;


    public List<ItemImagesDetails> getImages() {
        return images;
    }

    public void setImages(List<ItemImagesDetails> images) {
        this.images = images;
    }

    @SerializedName("images")
    private List<ItemImagesDetails> images ;

    @SerializedName("city")
    private CityItemDetails city ;

    @SerializedName("likes")
    private int likes ;

    public CityItemDetails getCity() {
        return city;
    }

    public void setCity(CityItemDetails city) {
        this.city = city;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public SubCategoryItemDetails getSub_category() {
        return sub_category;
    }

    public void setSub_category(SubCategoryItemDetails sub_category) {
        this.sub_category = sub_category;
    }

    @SerializedName("sub_category")
    private SubCategoryItemDetails sub_category ;


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
}
