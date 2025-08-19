package com.njzpc.entity;/*
 * @author Jiang longteng
 * @date 2025/7/22 18:56
 * */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class Seller {
    private int id;
    private String rphone; // 商家电话（唯一标识）
    private String rcode;
    private String rname; // 商家名称
    private String raddress; // 商家地址
    private Integer status; // 商家状态（与之前的状态逻辑对应）
    // 其他字段：如密码、营业时间等
}