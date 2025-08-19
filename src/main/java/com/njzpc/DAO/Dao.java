package com.njzpc.DAO;



import com.njzpc.entity.Category;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
@Log4j
public class Dao {
    private Dao(){}
    //读取数据库配置文件
//    private static final Logger log=Logger.getLogger(Dao.class);


    private static String url;
    private static String user;
    private static String pass;
    private static String driver;
    static {
        try (InputStream is =   Dao.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(is);
            url = props.getProperty("url");
            user = props.getProperty("user");
            pass = props.getProperty("pass");
            driver = props.getProperty("driver");
        } catch (IOException e) {
            throw new ExceptionInInitializerError("加载数据库配置失败: " + e.getMessage());
        }
    }

    public static boolean excDML(String sql1, String sql2) throws SQLException {
        Connection conn = null;
        try {
            Statement st = conn.createStatement();
            conn.setAutoCommit(false);
            boolean flag = st.executeUpdate(sql1) > 0;
            boolean flag2 = st.executeUpdate(sql2) > 0;
            if (flag && flag2) {
                conn.commit();
                return true;
            } else {
                throw new SQLException("执行失败");
            }
        }catch (Exception e){
            conn.rollback();
            throw new SQLException(e.getMessage());
        }finally {
            if(conn!=null){
                conn.close();
            }
        }}


    public static boolean excuteDML(String sql) {
        try (Connection conn = getConn()) {
            return conn.createStatement().executeUpdate(sql) > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage() + sql);
        }
        return false;
    }

    public static boolean excuteDML(String sql, Object params[]) {
        log.debug("执行SQL: " + sql);
        try (Connection conn = getConn()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            log.error(sql + "执行失败" + e.getMessage());
        }
        return false;
    }
    public static int excuteDML(String sql, Object params[], String flag) {
        int r = 0;
        try (Connection conn = getConn()) {
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            r= pst.executeUpdate();
            if(r>0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    r = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            log.error(sql + "执行失败" + e.getMessage());
        }
            return r;
        }



    public static boolean isExist(String sql) {
        boolean flag = false;
        try (Connection conn = getConn()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            return rs.next();
        } catch (Exception e) {
            log.error(sql + "执行失败" + e.getMessage());
        }
        return flag;
    }

    public static boolean isExist(String sql, Object params[]) {
        try (Connection conn = getConn()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            log.error(sql + "执行失败" + e.getMessage());
        }
        return false;
    }




    private static Connection getConn(){
        log.debug(url+" "+user+" "+pass+" "+driver);
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);

            stmt = conn.createStatement();
            log.debug(stmt);

        }catch (Exception e) {
            log.fatal(e.getMessage());
        }
        return conn;
    }


    public  static ArrayList<Category> getZpccategory(){
        ArrayList<Category> lst = new ArrayList<>(50);
        try (Connection conn = getConn()) {
            ResultSet rs = conn.createStatement().executeQuery("select * from category");
            while (rs.next()) {
                Category category = new Category();
                category.setTypeId(rs.getInt("typeid"));
                category.setName(rs.getString("name"));
                lst.add(category);
            }
        }catch (Exception e){
            log.error("执行失败:"+e.getMessage());
        }
        return lst;
    }


    public static ArrayList<HashMap<String,String>> getTablesData(String sql){
        log.debug(sql);

        ArrayList<HashMap<String,String>> lst = new ArrayList<>(50);
        try (Connection conn = getConn()) {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();
            while (rs.next()) {
                HashMap<String, String> map = new HashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    map.put(md.getColumnName(i), rs.getString(i));
                }
                lst.add(map);
            }
        }catch (Exception e){
            log.error(e.getMessage()+sql);
        }
        return lst;
    }


//    public static void main(String[] args) throws SQLException {
//
//        out.println(MyUtil.encryptMD5("zpc@qq.com"));
//        out.println(MyUtil.encryptMD5("123").length());
//
//
//    }
}