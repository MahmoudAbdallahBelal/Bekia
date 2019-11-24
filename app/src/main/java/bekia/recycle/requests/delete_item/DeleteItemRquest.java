package bekia.recycle.requests.delete_item;

import com.google.gson.annotations.SerializedName;

public class DeleteItemRquest {
    @SerializedName("item_id")
    private int itemId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
