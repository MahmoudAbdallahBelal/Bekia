package bekia.recycle.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import bekia.recycle.requests.chat.create_chat.CreateChatResponse;
import bekia.recycle.requests.login.LoginResponse;
import bekia.recycle.requests.profile.ProfileResponse;

public class Utils {
    //login
    public static SharedPreferences sharedPreferences;
    public static  String SharedPreferencesName="user_info";
    public static  String SharedPreferences_login_response="login_response";

    //user info
    public static SharedPreferences sharedPreferencesUserInfo;
    public static  String SharedPreferencesUserInfoName="user_info_for_id";
    public static  String SharedPreferences_User_Info_Key="user_info_id";


    //chat
    public static SharedPreferences sharedPreferencesChat;
    public static  String SharedPreferences_Chat_Name="chat_response";
    public static  String SharedPreferencesCreateChatKey="create_chat_reponse";



    //language
    public static SharedPreferences sharedPreferencesLanguage;
    public static  String SharedPreferencesNameLanguage="user_language";
    public static  String SharedPreferences_select_language="language";



    public static void saveUserInfo(Context context , LoginResponse loginResponse)
    {
        sharedPreferences = context.getSharedPreferences(SharedPreferencesName, 0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginResponse);
        editor.putString(SharedPreferences_login_response , json);
        editor.commit();

    }


    public static LoginResponse retrieveUserInfo(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SharedPreferencesName, 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SharedPreferences_login_response,null);
        LoginResponse obj = gson.fromJson(json , LoginResponse.class);

        return obj;
    }
// user info===========================================================

    public static void saveUserInfoForId(Context context , ProfileResponse profileResponse)
    {
        sharedPreferencesUserInfo = context.getSharedPreferences(SharedPreferencesUserInfoName, 0);
        SharedPreferences.Editor editor= sharedPreferencesUserInfo.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profileResponse);
        editor.putString(SharedPreferences_User_Info_Key , json);
        editor.commit();

    }


    public static ProfileResponse retrieveUserInfoForId(Context context)
    {
        sharedPreferencesUserInfo = context.getSharedPreferences(SharedPreferencesUserInfoName, 0);
        Gson gson = new Gson();
        String json = sharedPreferencesUserInfo.getString(SharedPreferences_User_Info_Key,null);
        ProfileResponse obj = gson.fromJson(json , ProfileResponse.class);

        return obj;
    }


// chat =================================================


    public static void saveCreateChatResponse(Context context , CreateChatResponse createChatResponse)
    {
        sharedPreferencesChat = context.getSharedPreferences(SharedPreferences_Chat_Name, 0);
        SharedPreferences.Editor editor= sharedPreferencesChat.edit();
        Gson gson = new Gson();
        String json = gson.toJson(createChatResponse);
        editor.putString(SharedPreferencesCreateChatKey , json);
        editor.commit();

    }
    public static CreateChatResponse retrieveChatResponse(Context context)
    {
        sharedPreferencesChat = context.getSharedPreferences(SharedPreferences_Chat_Name, 0);
        Gson gson = new Gson();
        String json = sharedPreferencesChat.getString(SharedPreferencesCreateChatKey,null);
        CreateChatResponse obj = gson.fromJson(json , CreateChatResponse.class);

        return obj;
    }

    //===========================





    public static void saveUserLanguage(Context context , String selectedLanguage)
    {
        sharedPreferencesLanguage = context.getSharedPreferences(SharedPreferencesNameLanguage, 0);
        SharedPreferences.Editor editor= sharedPreferencesLanguage.edit();
        editor.putString(SharedPreferences_select_language , selectedLanguage);
        editor.commit();

    }



    public static String retrieveUserLanguage(Context context)
    {
        sharedPreferencesLanguage = context.getSharedPreferences(SharedPreferencesNameLanguage, 0);
        String selectedLanguage = sharedPreferencesLanguage.getString(SharedPreferences_select_language,null);

        return selectedLanguage;
    }

}
