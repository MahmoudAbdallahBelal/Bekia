package bekia.recycle.helper;

import bekia.recycle.requests.categories.CategoryDetails;
import bekia.recycle.requests.chat.get_user_chats.ChatsDetails;
import bekia.recycle.requests.items_response.ItemDetailsReponse;

public interface OnItemChatsClickListener {
    void onItemClick(ChatsDetails item);

}
