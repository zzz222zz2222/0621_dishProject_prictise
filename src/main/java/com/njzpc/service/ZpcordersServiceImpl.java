package com.njzpc.service;

import com.njzpc.DAO.Dao;

import com.njzpc.entity.Zpcorders;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j
public class ZpcordersServiceImpl implements ZpcordersService {
    @Override
    public boolean checkZpcorders(int oid) {
        String sql = "select * from orders where oid =?";
        return Dao.isExist(sql, new Object[]{oid});    }

    @Override
    public boolean insertZpcorders(Zpcorders zpcorders) {
        String sql = "insert into orders(oid,cid,orderTime,ototol,orderStatus,oaddress) values(?,?,?,?,?,?)";
        return Dao.excuteDML(sql, new Object[]{
                zpcorders.getOid(),
                zpcorders.getCid(),
                zpcorders.getOrderTime(),
                zpcorders.getOtotol(),
                zpcorders.getOrderStatus(), // 确保使用正确的getter方法
                zpcorders.getOaddress()
        });
    }
    @Override
    public boolean updateZpcorders(Map map) {
        String sql = "update orders set cid = ?,orderTime = ?,ototol = ?,orderStatus = ?,oaddress = ? where oid = ?";

        return Dao.excuteDML(sql ,new Object[]{map.get("cid"), map.get("orderTime"), map.get("ototol"), map.get("orderStatus"), map.get("oaddress"), map.get("oid")});
    }

    @Override
    public boolean deleteZpcorders(int oid) {
        String sql = "delete from `orders` where oid = ?";
        return Dao.excuteDML(sql, new Object[]{oid});
    }
    @Override
    public ArrayList<HashMap<String, String>> getAllZpcorders(String oderStatus) {
        String sql = "select * from orders ";

          if (oderStatus!= null && !oderStatus.equals("")) {
              sql += " where orderStatus = ?";
          }
          return Dao.getTablesData(sql);
          }

}
