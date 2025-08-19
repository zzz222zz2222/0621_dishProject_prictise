package com.njzpc.zpccontroller;

import com.njzpc.entity.Category;
import com.njzpc.service.categoryService;
import com.njzpc.service.categoryServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
//@WebServlet("/api/category")
//@WebServlet("/api/category")
@Log4j
@RestController
@RequestMapping("api/category")
public class categoryController {
    private categoryService categoryService = new categoryServiceImpl();
@PostMapping("/insert")
    private String doPost(HttpServletRequest request, @RequestParam Map map) throws  IOException {
        request.setCharacterEncoding("utf-8");
        log.debug(map);

        if(categoryService.checkCategory((String) map.get("typeName"))) {

            return "exist,failure";
        }
        Category category=new Category();
        category.setName((String) map.get("name"));
        return categoryService.insertCategory(category)?"success":"failure";

    }

//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //处理修改请求
//        req.setCharacterEncoding("utf-8");
//        Map map=MyUtil.getParameterMap(req);
//        log.debug(map);
//        PrintWriter out=resp.getWriter();
//        if(!map.get("typeid").equals(map.get("oldtypeid"))
//                && categoryService.checkCategory(Integer.parseInt((String) map.get("typeid")))) {
//            out.println("exist");
//            return;
//        }
//        out.print(categoryService.updateCategory(map)?"success":"failure");
//    }

    @PutMapping(value = "/update")
    private String doPut(HttpServletRequest request, @RequestParam Map map) throws  IOException {
        request.setCharacterEncoding("utf-8");
        log.debug(map);
        if (categoryService.checkCategory((String) map.get("name"))) {
            return "exist,failure";
        }
        return categoryService.updateCategory(map)?"success":"failure";
    }

    @DeleteMapping(value = "delete/{typeId}")
    private String doDelete(HttpServletRequest request,@RequestParam Map map) throws ServletException, IOException {
        //处理删除请求
        request.setCharacterEncoding("utf-8");
//        Map map=MyUtil.getParameterMap(req);
        log.debug(map);
        int typeId=Integer.parseInt((String) map.get("typeId"));
        return categoryService.deleteCategory(typeId)?"success":"failure";
    }

    @GetMapping
    private ArrayList<HashMap<String,String>> doGet(HttpServletRequest request, @RequestParam Map map) throws IOException {
    //处理查询请求
        log.debug(map);
//    response.setContentType("application/json;charset=utf-8");
//        PrintWriter out=response.getWriter();
        String typeName=null;
//        try {
//            typeName=(String) map.get("typeName");
//        } catch (Exception e) {
//        }
        ArrayList<HashMap<String,String>> rs = categoryService.getAllCategory((HashMap<String,String>)map);
        log.debug(rs);
        return rs;
//      out.print(JSON.toJSONString(rs));
    }
}