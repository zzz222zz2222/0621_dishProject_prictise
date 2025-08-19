package com.njzpc.service;/*
 * @author Jiang longteng
 * @date 2025/7/22 14:38
 * */

import com.njzpc.DAO.Dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean deleteCustomer(Map map) {
        String sql = "delete from customer where cphone = ?";
        return Dao.excuteDML(sql, new Object[]{map.get("cphone")});
    }

    @Override
    public boolean updateCustomer(Map map) {
        String sql = "update customer set ccode = ?,cname = ?,caddress = ?,cphone = ?,status = ? where cphone = ?" +
                "and cname=? ";
        return Dao.excuteDML(sql ,new Object[]{map.get("rcode"), map.get("rname"),
                map.get("caddress"), map.get("cphone"), map.get("status"), map.get("cphone"),map.get("rname")});

    }

    @Override
    public ArrayList<HashMap<String, String>> getCustomerList() {
        String sql = "SELECT cid,cname,cphone, caddress,status FROM customer";
        return Dao.getTablesData(sql);
    }

    @Override
    public boolean updateCustomer(String newStatus, String cphone) {
        String sql = "update customer set status = ? where cphone = ?";
        return Dao.excuteDML(sql, new Object[]{newStatus,cphone});
    }
}
