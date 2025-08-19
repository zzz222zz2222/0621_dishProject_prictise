package com.njzpc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface LoginRegisterService {
    boolean checkFunction(Map map,String userType);
    Map login(Map map);
    boolean register(Map map);
    ArrayList<HashMap<String, String>> getRestaurantList();

    boolean updateRestaurant(Map map);
    boolean updateRestaurant(String newStatus,String status);
    boolean deleteRestaurant(Map map);


}
