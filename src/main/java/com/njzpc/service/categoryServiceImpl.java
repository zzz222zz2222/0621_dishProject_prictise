package com.njzpc.service;

import com.njzpc.DAO.Dao;
import com.njzpc.entity.Category;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class categoryServiceImpl implements categoryService {
    @Override
    public boolean checkCategory(String typeName) {
        String sql = "select * from category where typeName =?";
        return Dao.isExist(sql, new Object[]{typeName});
    }

    @Override
    public boolean insertCategory(Category category) {

        String sql = "insert into category(typeName) values(?)";
        return Dao.excuteDML(sql, new Object[]{ category.getName()});
    }

    @Override
    public boolean updateCategory(Map map) {
        String sql = "update category set typeName =? where typeId =?";
        log.debug("参数"+map.get("name")+" "+map.get("oldTypeId"));
        return Dao.excuteDML(sql, new Object[]{map.get("name"), map.get("oldTypeId")});
    }

    @Override
    public boolean deleteCategory(int typeId) {

        String sql = "delete  from category where typeId =?";

        return Dao.excuteDML(sql, new Object[]{typeId});
    }

    @Override
    public ArrayList<HashMap<String, String>> getAllCategory(String typeName) {
        log.debug("getAllCategory"+typeName);
        String sql = "select * from category";  // 修改为只查询category表
        if(typeName != null && !typeName.equals("")) {
            sql += " where typeId like '%" + typeName + "%'";
        }
        return Dao.getTablesData(sql);
    }

    @Override
    public ArrayList<HashMap<String, String>> getAllCategory(HashMap<String, String> m) {
        log.debug("getAllCategory" + m);

        // 处理分页逻辑，默认第一页，每页5条数据
        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(m.get("pageNo"));
        } catch (Exception e) {
            // 保持默认值1
        }
        int start = (pageNo - 1) * 5;

        // 获取查询参数
        String find = m.get("find");

        // 构建基础SQL，指定需要查询的字段（避免使用*）
        String sql = "select typeId, typeName from category where 1=1 ";

// 处理搜索条件（仅搜索分类名）
        if (find != null && !find.equals("")) {
            sql += " and (typeName like '%" + find + "%' )";
        }

        // 添加分页限制
        sql += " limit " + start + " , 5";

        return Dao.getTablesData(sql);

    }
}
