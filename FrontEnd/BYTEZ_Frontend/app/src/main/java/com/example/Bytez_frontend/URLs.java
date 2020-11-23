package com.example.Bytez_frontend;

/**
 * class that saves all the urls used throughout the app
 */
public class URLs
{
    private static final String ROOT_URL = "http://coms-309-vb-02.cs.iastate.edu:8080/";

    public static final String URL_REGISTER = ROOT_URL + "user/register";
    public static final String URL_LOGIN = ROOT_URL + "user/login";
    public static final String URL_AUTHORS_WORK = ROOT_URL + "user/reviews/";
    public static final String URL_REST_LIST = ROOT_URL + "restaurant/";
    public static final String URL_UPDATE_USER_INFO = ROOT_URL + "user/updateUserInfo/";
    public static final String URL_UPDATE_USERNAME = ROOT_URL + "user/updateUserName/";
    public static final String URL_GET_USER_FRIENDS = ROOT_URL + "user/getFriends/";
    public static final String URL_GET_USER_FRIEND_REQUESTS = ROOT_URL + "user/getFriendRequests/";
    public static final String URL_SEND_FRIEND_REQUEST = ROOT_URL + "friendship/friendRequest/";
    public static final String URL_REMOVE_FRIEND = ROOT_URL + "friendship/removeFriend/";
    public static final String URL_REVIEW = ROOT_URL + "review/add/";
    public static final String URL_BUG_ADD = ROOT_URL + "report/add/";
    public static final String URL_REVIEW_LIST = ROOT_URL + "review/";
    public static final String URL_REST_IN_REVIEW = ROOT_URL + "review/restaurant/";
    public static final String URL_AUTHOR_OF_REVIEW = ROOT_URL +  "review/author/";
    public static final String URL_DELETE_REVIEW = ROOT_URL + "review/delete/";
    public static final String URL_CHANGE_CRIT_FOOD = ROOT_URL + "user/changeCritFood/";
    public static final String URL_CHANGE_CRIT_SERVICE = ROOT_URL + "user/changeCritService/";
    public static final String URL_CHANGE_CRIT_CLEAN = ROOT_URL + "user/changeCritClean/";
    public static final String URL_CHANGE_ALL_CRITS = ROOT_URL + "user/changeCritValues/";
    public static final String URL_HELPFUL_PRESS = ROOT_URL +  "review/helpful/";
    public static final String URL_HELPFUL_UNPRESS = ROOT_URL +  "review//";
    public static final String URL_AGREE_PRESS = ROOT_URL +  "review/like/";
    public static final String URL_AGREE_UNPRESS = ROOT_URL +  "review//";
    public static final String URL_DISAGREE_PRESS = ROOT_URL +  "review/dislike/";
    public static final String URL_DISAGREE_UNPRESS = ROOT_URL +  "review//";
    public static final String URL_GET_HELPFULS = ROOT_URL +  "review/getHelpfuls/";
    public static final String URL_GET_AGREES = ROOT_URL +  "review/getLikes/";
    public static final String URL_GET_DISAGREES = ROOT_URL +  "review/getDislikes/";
    public static final String URL_WEBSOCKET = "ws://coms-309-vb-02.cs.iastate.edu:8080/chat/";
    public static final String URL_DELETE_CHAT_HISTORY = ROOT_URL + "messages/delete/";
}
