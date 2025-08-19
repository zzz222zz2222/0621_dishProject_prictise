package com.njzpc.service;

import com.njzpc.DAO.Dao;
import com.njzpc.entity.Dish;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j

@Service
public class DishServiceImpl implements DishService {

    @Override
    public boolean checkId(int id) {
        String sql = "select * from  dish where id =?";
        return Dao.isExist(sql, new Object[]{id});    }

    @Override
    public boolean insertDish(Dish  dish) {
        log.info("insertDish"+ dish);
        String  sql="insert into dish(restaurantName,name,price,typeId,restaurantPhone) values(?,?,?,?,?)";
        return Dao.excuteDML(sql, new Object[]{dish.getRestaurantName(),dish.getName(),
                dish.getPrice(),dish.getTypeId(),dish.getRestaurantPhone()});
    }
//@Override
//public boolean insertDish(Dish dish, String flag) { // 保留flag用于重载区分
//
//    try {
//        // 构建插入SQL（字段需与数据库表结构一致）
//        String sql = "insert into dish(restaurantName, name, price, typeId, restaurantPhone) " +
//                     "values(?, ?, ?, ?, ?)";
//        // 准备SQL参数（顺序与SQL中?一一对应）
//        Object[] params = new Object[]{
//            dish.getRestaurantName(),
//            dish.getName(),
//            dish.getPrice(),
//            dish.getTypeId(),
//            dish.getRestaurantPhone()
//        };
//        // 调用Dao执行插入，获取返回值（r为自增ID或受影响行数）
//        int r = Dao.excuteDML(sql, params, flag);
//        // 解析返回值：若r>0，说明插入成功且r即为自增ID
//        if (r > 0) {
//            dish.setId(r); // 设置自增ID到dish对象
//            return true;
//        }
//    } catch (Exception e) { // 捕获可能的非数据库异常（如参数转换错误）
//
//        return false;
//    }
//}

    @Override
    public boolean insertDish(Dish dish,String flag) {//flag是无用参数，删掉

        try {
            log.info("insertDish"+ dish);

            String  sql="insert into dish(restaurantName,name,price,typeId,restaurantPhone) values(?,?,?,?,?)";
            int r = Dao.excuteDML(sql, new Object[]{dish.getRestaurantName(),dish.getName(),
            dish.getPrice(),dish.getTypeId(),dish.getRestaurantPhone()},flag);
            if(r>0) dish.setId(r);
            return r>0;
        } catch (Exception e) {
            log.error("insertDish"+ dish+"失败"+e.getMessage(),e);
            return false;
        }
    }

    @Override
    public boolean updateDish(Map map) {
        String  sql="update  dish set name=?,price=?,typeId=? where name=?";
        return Dao.excuteDML( sql,new Object[]{map.get("name"),
                        map.get("price"),map.get("typeId"),map.get("oldName")});
    }

    @Override
    public boolean deleteDish(String name) {
        String  sql="delete from dish where name=?";
        return Dao.excuteDML(sql,new Object[]{name});
    }

    @Override
    public ArrayList<HashMap<String, String>> getDish(Map<String,String> m) {

        log.info("getDish"+m);

        int pageNo=1;
        try{
            pageNo=Integer.parseInt( m.get("pageNo"));
        }catch(Exception e){}

        int start=(pageNo-1)*5;

        String userType = m.get("userType");
        String find =  m.get("find");
        String  sql= "select id,restaurantName,dish.name as name,price,dish.typeId,typeName from dish left join category on dish.typeId = category.typeId where 1=1 ";

        //userType必须不为null
        switch (userType) {
            case "admin":
                break;
            case "customer":
                sql += " and status = 1";
                break;
            case "seller":
                sql += " and restaurantName = '"+m.get("rname")+"'";
                break;
        }

        if(find!=null&&!find.equals("")){
            sql+=" and name like '%"+find+"%' or restaurantName like '%"+find+"%'";
        }

        sql+= "  limit  "+start+" , 5";

        return  Dao.getTablesData(sql);
    }

    public ArrayList<HashMap<String,String>> getOtherDish(String restaurantName) {
        String sql = "select * from dish where restaurantName = '" + restaurantName + "'";
        return Dao.getTablesData(sql);
    }
}
