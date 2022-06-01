package com.podorozhnik.final_values;

public class APIValues {
    public static final String TARGET_OBJECT = "result";
    public static final String TARGET_ARRAY = "items";
    public static final String LOCATION_NAME = "full_name";
    public static final String LOCATION_POINT = "point";
    public static final String LOCATION_LAT = "lat";
    public static final String LOCATION_LON = "lon";
    public static final String LOCATION_URL = "https://catalog.api.2gis.com/3.0/items/geocode?" +
            "type=adm_div.city,adm_div.district,adm_div.division,adm_div.living_area,adm_div.place," +
            "adm_div.settlement,building,street&fields=items.full_adress_name,items.point&" +
            "sort=distance&key=ruxckr9415&q=";
    public static final String NOTIFICATION_URL = "https://api.pushy.me/push?api_key=";
    public static final String NOTIFICATION_TOKEN = "531d98132d8b9c22285524435c4c1284e0dd4c590c877be24602dd80097e8849";
    public static final String NOTIFICATION_RECEIVER = "to";
    public static final String NOTIFICATION_DATA = "data";
    public static final String NOTIFICATION_MESSAGE = "message";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String CONTENT_TYPE = "application/json";
}
