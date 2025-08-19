package com.njzpc.zpccontroller;

import com.alibaba.fastjson.JSON;
import com.njzpc.entity.Zpcorders;
import com.njzpc.service.ZpcordersService;
import com.njzpc.service.ZpcordersServiceImpl;
import com.njzpc.util.MyUtil;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@MultipartConfig
@Log4j
//@WebServlet("/api/orders")
@WebServlet("/api/orders")
public class ZpcordersController extends HttpServlet {

private ZpcordersService zpcordersService = new ZpcordersServiceImpl();

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        Zpcorders zpcorders = MyUtil.mapToEntity(request, Zpcorders.class);
        log.debug(zpcorders);
        PrintWriter out = resp.getWriter();
        if(zpcordersService.checkZpcorders(zpcorders.getOid())) {
            out.println("exist");
            return;
        }
        if(zpcordersService.insertZpcorders(zpcorders)){
            out.println("success");
            //保存图片
            String path=request.getServletContext().getRealPath("/")+"upload/";
            log.debug(path);
            Part p=request.getPart("tupian");
            p.write(path+"orders"+zpcorders.getOid()+"_.png");
        }else{
            out.println("failure");
        }

        out.print(zpcordersService.insertZpcorders(zpcorders)?"success":"failure");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理修改请求
        req.setCharacterEncoding("utf-8");
        Map map=MyUtil.getParameterMap(req);
        log.debug(map);
        PrintWriter out=resp.getWriter();
        if(!map.get("oid").equals(map.get("oid"))
                && zpcordersService.checkZpcorders(Integer.parseInt((String) map.get("oid")))) {
            out.println("exist");
            return;
        }
        out.print(zpcordersService.updateZpcorders(map)?"success":"failure");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理删除请求
        req.setCharacterEncoding("utf-8");
        Map map=MyUtil.getParameterMap(req);
        log.debug(map);
        int oid=Integer.parseInt(req.getParameter("oid"));
        PrintWriter out=resp.getWriter();
        out.print(zpcordersService.deleteZpcorders(oid)?"success":"failure");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 移除orderStatus参数，获取所有订单
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(zpcordersService.getAllZpcorders(null)));
    }
}


