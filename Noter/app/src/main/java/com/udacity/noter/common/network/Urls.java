package com.udacity.noter.common.network;

public class Urls {

    //    private static final String BASE_URL = "http://192.168.1.2/noter/index.php/api/";
    private static final String BASE_URL = "https://morethink2.000webhostapp.com/noter/index.php/api/";
    public static final String ADD_USER = BASE_URL + "User/adduser";
    public static final String LOGIN = BASE_URL + "User/login";
    public static final String ADD_NOTE = BASE_URL + "Note/addnote";
    public static final String GET_ALL_NOTES = BASE_URL + "Note/getmynotes?user_id=";

}
