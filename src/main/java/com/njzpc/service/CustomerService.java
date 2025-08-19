package com.njzpc.service;/*
 * @author Jiang longteng
 * @date 2025/7/22 14:33
 * */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CustomerService {
    boolean deleteCustomer(Map map);
    boolean updateCustomer(Map map);
    ArrayList<HashMap<String, String>> getCustomerList();
    boolean updateCustomer(String newStatus ,String cphone);
}
