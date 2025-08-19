package com.njzpc.service;

import com.njzpc.DAO.Dao;
import com.njzpc.util.MyUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j

@Service

public class  LoginRegisterServiceImpl implements LoginRegisterService {

    @Override
    public boolean checkFunction(Map map, String userType) {
        String sql =null;
        Object[] data=null;
        switch (userType) {
            case "seller":
                sql ="select * from restaurant where rphone=? and raddress = ?";
                data = new Object[]{map.get("rphone"),map.get("raddress")};
                break;

            case "customer":
                sql ="select * from customer where cphone = ? and ccode = ?";
                data = new Object[]{map.get("cphone"),MyUtil.encryptMD5((String) map.get("rcode"))};
                break;
        }
        return Dao.isExist(sql,data);

    }

    @Override
public Map login(Map map) {
        log.debug(map);
        String userType= (String) map.get("userType");
        if(userType==null) return  null;

        String code = MyUtil.encryptMD5(map.get("code").toString());
        log.debug(code);
        String sql =null;

        switch (userType){
            case "admin":
                 sql = "select * from admin where pass = '" + code + "'";
                break;

            case "seller":
                sql = "select * from restaurant where rname ='" + map.get("name")
                  + "' and rcode='" + code + "' and status=1";
                break;

            case "customer":
                sql= "select * from customer where cname ='" + map.get("name")
                        + "'and ccode='" +code + "' and status=1";
                break;
        }
        log.debug("当前执行的sql:"+sql);

        ArrayList<HashMap<String, String>> list = Dao.getTablesData(sql);
        log.debug(list);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean register(Map map) {
        String userType = (String) map.get("userType");
        String sql = null;
        Object data[] = null;

        switch(userType){

            case "seller":
                sql = "insert into restaurant(rname,rcode,raddress,rphone) values(?,?,?,?)";
                data = new Object[]{map.get("name"), MyUtil.encryptMD5((String)map.get("code")),
                        map.get("raddress"),map.get("rphone")};
                break;

            case "customer":
                sql = "insert into customer(cname,ccode,caddress,cphone) values(?,?,?,?)";
                data = new Object[]{map.get("name"), MyUtil.encryptMD5((String)map.get("code")),
                    map.get("caddress"),map.get("cphone")};
                break;
        }

        return Dao.excuteDML(sql, data);
    }

    @Override
    public ArrayList<HashMap<String, String>> getRestaurantList() {
        String sql = "SELECT rid, rname, raddress, rphone,status FROM restaurant";
        return Dao.getTablesData(sql);
    }


    @Override
    public boolean deleteRestaurant(Map map) {
        String sql = "delete from restaurant where rphone = ?";
        return Dao.excuteDML(sql, new Object[]{map.get("rphone")});
    }

    @Override
    public boolean updateRestaurant(Map map) {
        String sql = "update restaurant set rcode = ?,rname = ?,raddress = ?,rphone = ?,status = ? where rphone = ? ";

        return Dao.excuteDML(sql ,new Object[]{map.get("code"), map.get("name"),
                map.get("raddress"), map.get("rphone"), map.get("status"), map.get("rphone")});
    }
    public boolean updateRestaurant(String newStatus,String rphone){
        String sql = "update restaurant set status = ? where rphone = ?";
        return Dao.excuteDML(sql, new Object[]{newStatus,rphone});

    }


}
