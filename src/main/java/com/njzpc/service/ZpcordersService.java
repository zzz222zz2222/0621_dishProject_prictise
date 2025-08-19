package com.njzpc.service;

import com.njzpc.entity.Zpcorders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  interface ZpcordersService {

    boolean checkZpcorders(int oid);
    boolean insertZpcorders(Zpcorders zpcorders);
    boolean updateZpcorders(Map map);
    boolean deleteZpcorders(int oid);
    ArrayList<HashMap<String,String>> getAllZpcorders(String oderStatus);
}
