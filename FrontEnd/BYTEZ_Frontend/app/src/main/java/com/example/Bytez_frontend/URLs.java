package com.example.Bytez_frontend;

public class URLs
{
    private static final String ROOT_URL = "http://coms-309-vb-02.cs.iastate.edu:8080/";

    public static final String URL_REGISTER = ROOT_URL + "user/register";
    public static final String URL_LOGIN = ROOT_URL + "user/login";
    public static final String URL_REST_LIST = ROOT_URL + "restaurant/";
    public static final String URL_UPDATE_USER_INFO = ROOT_URL + "user/updateUserInfo/";
    public static final String URL_UPDATE_USERNAME = ROOT_URL + "user/updateUserName/";
    public static final String URL_GET_USER_FRIENDS = ROOT_URL + "user/getFriends/";
    public static final String URL_GET_USER_FRIEND_REQUESTS = ROOT_URL + "user/getFriendRequests/";
    public static final String URL_SEND_FRIEND_REQUEST = ROOT_URL + "friendship/friendRequest/";
    public static final String URL_REMOVE_FRIEND = ROOT_URL + "friendship/removeFriend/";

}
