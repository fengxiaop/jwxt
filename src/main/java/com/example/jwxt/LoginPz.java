package com.example.jwxt;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 登录
 */
public class LoginPz {
    public static String hello() {
        Map<String,String> param = new HashMap<>();
        String userAccount = "";//在“”中填写学号
        String userPassword = "";//在“”中填写密码
        byte[] userbyte = userAccount.getBytes();//userAccout的byte数组
        byte[] passbyte = userPassword.getBytes();//password的byte数组
        String encoded = Base64.getEncoder().encodeToString(userbyte)+"%%%"+Base64.getEncoder().encodeToString(passbyte);//教务系统的加密方法
//        String encoded = "MTk0MDgwMDAzMjI=%%%cGoyMDAyMDMxOA==";
//        System.out.println(encoded);  打印encoded  人工检验算法是否正确
        param.put("userAccount",userAccount);
        param.put("userPassword",userPassword);
        param.put("encoded",encoded);
        List<URI> redirectLocations = null;
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost("http://218.75.197.123:83/jsxsd/xk/LoginToXk");//学校的教务系统网址
            //请求头
            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.addHeader("Cookie","JSESSIONID=22B4C4CE6240C6C53FF6BC3C197E3B83; SERVERID=121; JSESSIONID=8FFFAEA49DC840CE5A3135330C06CED3");
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单登录
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            HttpClientContext context = HttpClientContext.create();
            response = httpClient.execute(httpPost,context);
            //获取Cookie信息,得到两个参数 JSESSIONID 、 Serverid
            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
//                System.out.println("name:"+name+","+value);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return " ";
    }
}


