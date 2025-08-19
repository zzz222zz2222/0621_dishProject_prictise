package com.njzpc.zpccontroller;


import com.alibaba.fastjson.JSONObject;
import com.njzpc.entity.Dish;

import com.njzpc.service.DishService;
import com.njzpc.util.MyUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
//@MultipartConfig
@RestController
@RequestMapping("api/dish")

public class DishController {

    @Autowired
    private DishService dishService;//= new DishServiceImpl();

    protected void doPost(HttpServletRequest request, @RequestParam Map map) throws IOException, ServletException {
        //处理增加请求
        request.setCharacterEncoding("utf-8");
//        resp.setContentType("application/json;charset=utf-8");
//        PrintWriter out = resp.getWriter();
        JSONObject result = new JSONObject();
//        Map<String, String> m = MyUtil.getParameterMap(request);
        log.debug("搜索功能的请求："+map);
        if (map.get("find")!=null) {

            List<HashMap<String, String>> dishesByName = dishService.getDish(map);
//            out.print(JSON.toJSONString(dishesByName)); // 只输出一次

        } else {
            Dish dish =MyUtil.mapToEntity(request, Dish.class);
            log.debug(dish);

//            if(dishService.insertDish(Dish dish)){
////                out.println("success");
//                //保存头像
//                String path=request.getServletContext().getRealPath("/")+"upload/";
//                log.debug(path);
//                Part p=request.getPart("tupian");
//                p.write(path+"dish_"+ dish.getId()+"_.png");
////                out.print("success");
//            }else{
////                out.println("failure");
//            }

//            out.print(dishService.insertDish(dish)?"success":"failure");
        }

    }

    @PutMapping("/update")
    private String updateDish(HttpServletRequest request, @RequestParam Map map) throws IOException {
        request.setCharacterEncoding("utf-8");
        log.debug(map);
        Dish dish = MyUtil.mapToEntity(request, Dish.class);
        log.debug(dish);
        if(dishService.updateDish(map)){
            return "success";
        }
        return "failure";
    }

    @PostMapping("/insert")
    private String insertDoPost(HttpServletRequest request , @RequestParam Map map, MultipartFile picture) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        log.debug(map);
        log.debug(picture);

        Dish dish =MyUtil.mapToEntity(request, Dish.class);
        Map<String, String> m = (Map)request.getSession().getAttribute("isLogin");
        log.debug("登陆状态m: " + m);

        dish.setRestaurantName(m.get("rname").toString());
        dish.setRestaurantPhone(m.get("rphone").toString());
        log.debug(dish);
        String atuoId = null;

        if(dishService.insertDish(dish,atuoId)) {
            try{
                String path = "C:\\software\\t\\0721\\src\\main\\resources\\static\\upload\\dish_"
                        + dish.getId() + "_.png";
                log.debug(path);
                picture.transferTo(new File(path));

            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return "success";
        }else{
            return "failure";
        }
    }

    @DeleteMapping("/delete")
    private String deleteDoPost(HttpServletRequest request , @RequestParam Map map) throws IOException {
        request.setCharacterEncoding("utf-8");
        log.debug(map);
        if(dishService.deleteDish(map.get("name").toString()))
        {
            return "success";
        }
        return "failure";
    }

    @GetMapping
    private ArrayList<HashMap<String, String>> doGet(HttpServletRequest request, @RequestParam Map map) throws ServletException, IOException {

//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        Map<String, String> m = MyUtil.getParameterMap(request);
        log.debug(map);
        Map<String, String> m = (Map)request.getSession().getAttribute("isLogin");
        log.debug("登陆状态m: " + m);

        try {
            m.put("pageNo",(String) map.get("pageNo"));
        } catch (Exception e) {
        }
        try {
            m.put("find",(String) map.get("find"));
        }
        catch (Exception e) {
        }

        ArrayList<HashMap<String, String>> outPutList = dishService.getDish(m);
        log.debug(outPutList);
//        out.print(JSON.toJSONString(dishesByName)); // 只输出一次
        return outPutList;

        /*框架中最后要返回map。所以需要map直接获得查找的数据*/

    }

        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理修改请求
        Map map=MyUtil.getParameterMap(req);
        log.debug(map);
        PrintWriter out=resp.getWriter();
        if(!map.get("did").equals(map.get("olddid"))
                &&   dishService.checkId(Integer.parseInt((String) map.get("did")))) {
            out.println("exist");
            return;
        }
        out.print(dishService.updateDish(map)?"success":"failure");
    }

//        @Override
//        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        //处理查询请求
//            String dname=request.getParameter("dname");
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter out=response.getWriter( );
//            out.print(JSON.toJSONString(dishService.getDish(dname)));
//
//        }
}

