package com.njzpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
      private  int id;
      private  String restaurantName;
      private   String  name;
      private  double  price;
      private   int  typeId;
      private String restaurantPhone;

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", typeId=" + typeId +
                ", restaurantPhone='" + restaurantPhone + '\'' + // 补充遗漏的字段
                '}';
    }

//      public Dish(int did, String rname, String dname, double dprice, int typeid) {
//            this.did = did;
//            this.rname = rname;
//            this.dname = dname;
//            this.dprice = dprice;
//            this.typeid = typeid;
//      }
//
//      public Dish() {
//      }
//
//
//      public double getDprice() {
//            return dprice;
//      }
//
//      public void setDprice(double dprice) {
//            this.dprice = dprice;
//      }
//
//      public int getDid() {
//            return did;
//      }
//
//      public void setDid(int did) {
//            this.did = did;
//      }
//
//      public String getDname() {
//            return dname;
//      }
//
//      public void setDname(String dname) {
//            this.dname = dname;
//      }
//
//      public String getRname() {
//            return rname;
//      }
//
//      public void setRname(String rname) {
//            this.rname = rname;
//      }
//
//      public int getTypeid() {
//            return typeid;
//      }
//
//      public void setTypeid(int typeid) {
//            this.typeid = typeid;
//      }
}
