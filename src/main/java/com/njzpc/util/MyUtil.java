package com.njzpc.util;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyUtil {
    /**
     * 文档注释
     * MD5加密字符串（不可逆）
     * @param input 原始字符串
     * @return 32位小写MD5哈希值
     */
    public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * MD5加密字符串（不可逆）
     * @param //input 原始字符串
     * @return 32位小写MD5哈希值
     */
    public static <T> T mapToEntity(HttpServletRequest request, Class<T> clazz) {
        try {
            T entity = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String paramName = field.getName();
                String paramValue = request.getParameter(paramName);

                if (paramValue != null && !paramValue.isEmpty()) {
                    Class<?> fieldType = field.getType();

                    if (fieldType == String.class) {
                        field.set(entity, paramValue);
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        field.set(entity, Integer.parseInt(paramValue));
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        field.set(entity, Long.parseLong(paramValue));
                    } else if(fieldType == double.class || fieldType == Double.class) {
                        field.set(entity, Double.parseDouble(paramValue));
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        field.set(entity, Boolean.parseBoolean(paramValue));
                    } else if (fieldType == Date.class) {
                        // 支持yyyy-MM-dd格式的日期转换
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        field.set(entity, sdf.parse(paramValue));
                    }
                    // 可以根据需要添加更多数据类型的转换
                }
            }

            return entity;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将HttpServletRequest中的所有参数存储到一个HashMap中
     * @param //request HttpServletRequest对象
     * @return 包含所有请求参数的HashMap
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
//gettableData 方发一样的效果
        // 获取所有参数名
        java.util.Enumeration<String> paramNames = request.getParameterNames();

        // 遍历参数名并获取对应的值
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }

}

