package bekia.recycle.web;

import bekia.recycle.requests.add_item.AddNewItemRequest;
import bekia.recycle.requests.add_item.AddNewItemResponse;
import bekia.recycle.requests.categories.CategoriesResponse;
import bekia.recycle.requests.chat.create_chat.CreateChatResponse;
import bekia.recycle.requests.chat.create_chat_message.CreateChatMessageRequest;
import bekia.recycle.requests.chat.create_chat_message.CreateChatMessageResponse;
import bekia.recycle.requests.chat.get_chat_messages.GetChatMessagesResponse;
import bekia.recycle.requests.chat.get_user_chats.GetUserChatsResponse;
import bekia.recycle.requests.city.CityResponse;
import bekia.recycle.requests.delete_item.DeleteItemResponse;
import bekia.recycle.requests.delete_item.DeleteItemRquest;
import bekia.recycle.requests.edit_profile.EditProfileReq;
import bekia.recycle.requests.edit_profile.EditProfileRes;
import bekia.recycle.requests.favorite_like_unlike.LikeUnlikeResponse;
import bekia.recycle.requests.forget_password.ForgetPasswordRes;
import bekia.recycle.requests.forget_password.ForgetPasswordRq;
import bekia.recycle.requests.forget_password.reset.ResetPasswordRes;
import bekia.recycle.requests.forget_password.reset.ResetPasswordRq;
import bekia.recycle.requests.items_response.GetItemsResponse;
import bekia.recycle.requests.login.LoginRequest;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.requests.profile.ProfileResponse;
import bekia.recycle.requests.register.RegisterRequest;
import bekia.recycle.requests.register.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("auth/signup")
    Call<RegisterResponse> registerApi(@Query("lang") String language , @Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<LoginResponse> loginApi(@Query("lang") String language , @Body LoginRequest loginRequest);

    @GET("getCities")
    Call<CityResponse> getCitiesApi(@Query("lang") String language);

    @GET("getMainCategories")
    Call<CategoriesResponse> getCategoriesApi(@Query("lang") String language);

    @GET("getMostPopularItems")
    Call<GetItemsResponse> getMostPopularApi(@Header("items_num") int itemsNumber,@Query("lang") String language);


    @GET("getSubCatsOfMainCategory/{category_id}")
    Call<CategoriesResponse> getSubCategoriesApi(@Path("category_id") int categoryId ,@Query("lang") String language );

    @GET("getItemsByCategory/{category}")
    Call<GetItemsResponse> getAllItemsApi(@Path("category") int subCategoryId , @Query("lang") String language );


    @POST("auth/likeUnlikeItem")
    Call<LikeUnlikeResponse> likeAndUnlikeApi(@Header("Authorization") String token, @Query("item_id") int item_id, @Query("lang") String language);

    @GET("auth/user")
    Call<ProfileResponse> getUserInfoApi(@Header("Authorization") String token, @Query("lang") String language);

    @GET("searchItems")
    Call<GetItemsResponse> searchItemApi(@Query("name") String name , @Query("category_id") String category_id ,@Query("city_id") String city_id);

    @GET("auth/userMyItem")
    Call<GetItemsResponse> getUserItemsApi(@Header("Authorization") String token, @Query("lang") String language);

    @POST("auth/addNewItem")
    Call<AddNewItemResponse> addNewItemApi(@Header("Authorization") String token, @Query("lang") String language , @Body AddNewItemRequest addnewItemResponse);

    // chat Apis

    @POST("auth/createChat")
    Call<CreateChatResponse> createChatApi(@Header("Authorization") String token, @Query("lang") String language , @Query("item_id") int itemId );

    @POST("auth/createChatMessage")
    Call<CreateChatMessageResponse> createChatMessageApi(@Header("Authorization") String token , @Body CreateChatMessageRequest createChatMessageRequest);

    @GET("auth/chatMessages")
    Call<GetChatMessagesResponse> getChatMessagesApi(@Header("Authorization") String token, @Query("lang") String language , @Query("chat_id") int chatId);

    @GET("auth/getUserChats")
    Call<GetUserChatsResponse> getUserChatsApi(@Header("Authorization") String token, @Query("lang") String language );

    // delete item

    @POST("auth/deleteItem")
    Call<DeleteItemResponse> deleteItemApi(@Header("Authorization") String token, @Query("lang") String language , @Body DeleteItemRquest deleteItemRquest);


    @POST("auth/forgetPassword")
    Call<ForgetPasswordRes> forgetPasswordApi(@Body ForgetPasswordRq forgetPasswordRq);

    @POST("auth/reset_password")
    Call<ResetPasswordRes> resetPasswordApi(@Body ResetPasswordRq resetPasswordRq);

    @POST("auth/editProfile")
    Call<EditProfileRes> editProfileApi(@Header("Authorization") String token , @Body EditProfileReq editProfileReq);









}
