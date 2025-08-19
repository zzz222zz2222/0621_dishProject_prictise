package com.njzpc.zpccontroller;


import com.alibaba.fastjson.JSONObject;
import com.njzpc.service.LoginRegisterService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.util.Map;

//@WebServlet("/api/restaurant")//处理登录注册注销
//@WebServlet("/0721_war_exploded/user")//后续登录注册请求都要改user
//@WebServlet("/user")//后续登录注册请求都要改user




@RequestMapping("/user")
@RestController
@Log4j
public class LoginRegisterController {

//    private CustomerService customerService;// =new CustomerServiceImpl();

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    response.setContentType("application/json;charset=utf-8");
//    PrintWriter out = response.getWriter();
//
//    try {
//        // 获取请求参数userType
//        String userType = request.getParameter("userType");
//        log.debug("请求的用户类型: " + userType);
//
//        // 根据userType调用不同的方法
//        ArrayList<HashMap<String, String>> dataList;
//        if ("seller".equals(userType)) {
//            dataList = zpcrestService.getRestaurantList();
//        } else if ("customer".equals(userType)) {
//            dataList = customerService.getCustomerList();
//        } else {
//            // 处理未知类型
//            Map<String, String> errorMap = new HashMap<>();
//            errorMap.put("status", "error");
//            errorMap.put("message", "无效的userType参数");
//            out.print(JSON.toJSONString(errorMap));
//            return; // 提前返回，避免继续执行
//        }
//
//        // 转换为JSON并输出
//        String jsonResult = JSON.toJSONString(dataList);
//        out.print(jsonResult);
//        log.debug("json result is " + jsonResult);
//
//    } catch (Exception e) {
//        log.error("获取列表失败", e);
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("status", "error");
//        errorMap.put("message", "获取列表失败");
//        errorMap.put("detail", e.getMessage());
//        out.print(JSON.toJSONString(errorMap));
//    }
//}

    @Autowired//自动装配
    private LoginRegisterService login_register_service; //=new ZpcrestServiceImpl();

    @PostMapping("/login")
    private String loginDoPost(HttpServletRequest request, @RequestParam Map map) throws IOException {
        //仅保留登录
        request.setCharacterEncoding("UTF-8");// 设置响应体编码
        JSONObject result = new JSONObject();
//        Map<String, String> map = MyUtil.getParameterMap(request);//框架可以自取请求数据
        log.debug(map);

//        PrintWriter out = response.getWriter();
//        if ("login".equals(map.get("action"))) {

            Map m = login_register_service.login(map);
            log.debug("查询核对的信息为" + m);
            if (m == null) {
//                out.print("failure"+m);
                return "failure";
            }                // todo 把userType信息传入m，存在浏览器
            String status = (String) m.get("status");
            //账户的状态判定
            if ((status.equals("0")) || (status.equals("2"))) {
//                result.put("success", "false");
//                result.put("msg", "封禁状态，登陆失败");
//                out.print(result);
                return "failure";
            }
            m.put("userType", map.get("userType"));
            //账户类型的判定
            request.getSession().setAttribute("isLogin", m);
//            out.print("success");
            return "success";

    }

    @PostMapping("/register")
        private String registerDoPut( /*HttpServletRequest request,*/ @RequestParam Map map)throws IOException{
            log.debug(map);
        if (login_register_service.checkFunction(map,(String) map.get("userType"))) {
            return "exist";// todo 前端要做对应的响应
        }
            return login_register_service.register(map)? "success":"failure";//todo 需要做状态检验
        }


//        return "failire";


//        if ("register".equals(map.get("action"))) {
//
//            if (zpcrestService.checkFunction(map,map.get("userType") )) {
//                out.print("exist,账户已存在");
//                out.print("failure");
//                return;
//            }
//
//
//            if (zpcrestService.register(map))
//            {
//                out.print("success");
//            } else {
//                out.print("failure");
//            }
//
//        }
//
//        String userType = map.get("userType");
//        String action = map.get("action");
//
//        if (action.equals("updateStatus")) {
//
//            String newStatus = map.get("newStatus");
//
//            if ("seller".equals(userType)) {
//                String rphone = map.get("rphone");
//                zpcrestService.updateRestaurant(newStatus,rphone);
//                out.print("restaurant_trans success");
//
//            }else if ("customer".equals(userType)) {
//                String cphone = map.get("cphone");
//                customerService.updateCustomer(newStatus,cphone);
//                out.print("customer_trans success");
//
//            }else{
//                out.print("failure");
//            }
//        }
//    }

}