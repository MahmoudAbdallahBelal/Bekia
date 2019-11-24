package bekia.recycle.helper;

import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;

public interface OnItemClickListener {
    void onItemClick(CategoryDetails item);
    void onItemOfSubCategoryClick(ItemDetailsReponse item);
    void onItemDelete(ItemDetailsReponse item);

}
