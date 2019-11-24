package bekia.recycle.requests.categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {
    @SerializedName("categories")
    private List<CategoryDetails> categories;

    public List<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDetails> categories) {
        this.categories = categories;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @SerializedName("code")
    private String code;

}
