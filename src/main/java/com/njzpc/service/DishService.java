package com.njzpc.service;

import com.njzpc.entity.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DishService {
    boolean  checkId(int id);
    boolean  insertDish(Dish dish);
    boolean  insertDish(Dish dish ,String autoId );
    boolean updateDish(Map map);
    boolean deleteDish(String name);
    ArrayList<HashMap<String,String>> getDish(Map<String,String> map);
    ArrayList<HashMap<String,String>> getOtherDish(String name);
}


