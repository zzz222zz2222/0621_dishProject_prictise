package com.njzpc.zpccontroller;/*
 * @author Jiang longteng
 * @date 2025/7/26 11:32
 * */

import com.njzpc.DAO.Dao;
import com.njzpc.entity.Dish;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/select")
public class SelectController {
    @GetMapping
    public List select(){
        String sql = "SELECT restaurantName,dish.name as dishName,price,dish.typeId,category.typeName as name FROM dish LEFT JOIN category ON dish.typeId = category.typeId WHERE 1=1  AND restaurantName = 'zpc'  LIMIT  0 , 5";
        return Dao.getTablesData(sql);
    }
}
/**
 * 插入菜品并获取自增ID，保留flag参数用于函数重载区分
 * @param dish 待插入的菜品对象
 * @param flag 重载区分参数（无实际业务意义）
 * @return 插入成功返回true，失败返回false
 */
