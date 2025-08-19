package com.njzpc.zpccontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njzpc.service.CustomerService;
import com.njzpc.service.CustomerServiceImpl;
import com.njzpc.service.ZpcrestService;
import com.njzpc.service.ZpcrestServiceImpl;
import com.njzpc.util.MyUtil;
import lombok.extern.log4j.Log4j;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@WebServlet("/api/restaurant")//处理登录注册注销
//@WebServlet("/0721_war_exploded/user")//后续登录注册请求都要改user
@WebServlet("/api/restaurant")//后续登录注册请求都要改user
@Log4j
public class ZpcrestaurantController extends HttpServlet{

//    @Override
//    public void init() throws ServletException {
//        log.debug("init only once");
//        log.debug(this);}

    private ZpcrestService zpcrestService =new ZpcrestServiceImpl();
    private CustomerService customerService =new CustomerServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();

    try {
        // 获取请求参数userType
        String userType = request.getParameter("userType");
        log.debug("请求的用户类型: " + userType);

        // 根据userType调用不同的方法
        ArrayList<HashMap<String, String>> dataList;
        if ("seller".equals(userType)) {
            dataList = zpcrestService.getRestaurantList();
        } else if ("customer".equals(userType)) {
            dataList = customerService.getCustomerList();
        } else {
            // 处理未知类型
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("status", "error");
            errorMap.put("message", "无效的userType参数");
            out.print(JSON.toJSONString(errorMap));
            return; // 提前返回，避免继续执行
        }

        // 转换为JSON并输出
        String jsonResult = JSON.toJSONString(dataList);
        out.print(jsonResult);
        log.debug("json result is " + jsonResult);

    } catch (Exception e) {
        log.error("获取列表失败", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("status", "error");
        errorMap.put("message", "获取列表失败");
        errorMap.put("detail", e.getMessage());
        out.print(JSON.toJSONString(errorMap));
    }
}

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//
////        String contextPath = request.getContextPath();
////        log.debug("完整url上下文路径，contextPath:"+contextPath);
//
//        try {
//            // 获取列表数据
//
//            ArrayList<HashMap<String, String>> restaurants = zpcrestService.getRestaurantList();
//
//            // 使用FastJSON将数据转换为JSON字符串
//            String jsonResult = JSON.toJSONString(restaurants);
//
//            // 输出JSON响应
//            out.print(jsonResult);
//            log.debug("json result is " + jsonResult);
//        } catch (Exception e) {
//            log.error("获取商家列表失败", e);
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//
//            // 使用传统方式创建Map
//            Map<String, String> errorMap = new HashMap<>();
//            errorMap.put("status", "error");
//            errorMap.put("message", "获取商家列表失败");
//            errorMap.put("detail", e.getMessage());
//            out.print(JSON.toJSONString(errorMap));
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        log.debug(Thread.currentThread().getId());
//        log.debug("this");
        JSONObject result = new JSONObject();
        Map<String, String> map = MyUtil.getParameterMap(request);
        log.debug(map);

        request.setCharacterEncoding("UTF-8"); // 设置响应体编码
        PrintWriter out = response.getWriter();

        if ("login".equals(map.get("action"))) {

            Map m = zpcrestService.login(map);
            log.debug("查询核对的信息为"+m);

            if (m == null) {
                out.print("failure"+m);
                return;
            }                // todo 把userType信息传入m，存在浏览器

            String status = (String) m.get("status");

            //账户的状态判定
            if ( (status.equals("0")) || (status.equals("2"))) {
                result.put("success", "false");
                result.put("msg", "封禁状态，登陆失败");
                out.print(result);
                return;
            }
            m.put("userType",map.get("userType"));
            //账户类型的判定
            request.getSession().setAttribute("isLogin", m);
            out.print("success");

            return;

        }

        if ("register".equals(map.get("action"))) {

            if (zpcrestService.checkFunction(map,map.get("userType") )) {
                out.print("exist,账户已存在");
                out.print("failure");
                return;
            }


            if (zpcrestService.register(map))
            {
                out.print("success");
            } else {
                out.print("failure");
            }

        }

        String userType = map.get("userType");
        String action = map.get("action");

        if (action.equals("updateStatus")) {

            String newStatus = map.get("newStatus");

            if ("seller".equals(userType)) {
                String rphone = map.get("rphone");
                zpcrestService.updateRestaurant(newStatus,rphone);
                out.print("restaurant_trans success");

            }else if ("customer".equals(userType)) {
                String cphone = map.get("cphone");
                customerService.updateCustomer(newStatus,cphone);
                out.print("customer_trans success");

            }else{
                out.print("failure");
            }
        }
    }

}
