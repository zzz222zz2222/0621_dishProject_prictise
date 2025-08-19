package com.njzpc.service;

import com.njzpc.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface categoryService {
    boolean checkCategory(String typeName);
    boolean insertCategory(Category category);
    boolean updateCategory(Map map);
    boolean deleteCategory(int typeId);
    ArrayList<HashMap<String,String>> getAllCategory(String typeId);
 ArrayList<HashMap<String,String>> getAllCategory(HashMap<String,String> map);

}
