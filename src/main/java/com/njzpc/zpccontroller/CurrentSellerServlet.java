//package com.njzpc.zpccontroller;
//import com.alibaba.fastjson.JSON;
//import lombok.extern.log4j.Log4j;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Map;
//
//@Log4j
////@WebServlet("/api/currentSeller")
//@WebServlet("/currentSeller")
//public class CurrentSellerServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json;charset=utf-8");
//        PrintWriter out = response.getWriter();
//
//        // 1. 从Session中获取当前登录的商家信息（Map类型）
//        HttpSession session = request.getSession();
//        // 注意：键名"loginSeller"必须与登录时存储的键名一致
//        Map<String, String> currentSellerMap = (Map<String, String>) session.getAttribute("loginSeller");
//        log.debug(currentSellerMap);
//        // 2. 判断商家是否已登录（Map是否为null或空）
//        if (currentSellerMap == null || currentSellerMap.isEmpty()) {
//            out.print("{\"error\": \"未登录，请先登录\"}");
//            return;
//        }
//
//        // 3. 验证Map中是否包含关键信息（rphone，商家唯一标识）
//        if (!(currentSellerMap.containsKey("rphone")||(currentSellerMap.containsKey("cphone")))) {
//            out.print("{\"error\": \"登录信息不完整，缺少商家标识\"}");
//            return;
//        }
//
//        // 4. 已登录：返回Map中的商家信息（包含rphone等字段）
//        String sellerJson = JSON.toJSONString(currentSellerMap);
//        out.print(sellerJson);
//        log.debug("当前登录商家信息：" + sellerJson);
//    }
//}