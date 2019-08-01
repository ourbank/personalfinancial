package com.icbc.index.util;

import com.icbc.index.model.User;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Util {

    public static String getToken(String account,String passwd){

        try {
            MessageDigest messageDigest =MessageDigest.getInstance("SHA-256");
            String temp=account+passwd;
            String  digest = messageDigest.digest(temp.getBytes()).toString();
            Random random =new Random(System.currentTimeMillis());
            StringBuffer stringBuffer=new StringBuffer();
            for (int i=0;i<6;i++){
                stringBuffer.append(digest.charAt(random.nextInt()));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String key, User user, HttpServletRequest request){

        Map<String,Object> param= new HashMap<>();
        param.put("account",user.getAccount());
        param.put("passwd",user.getPasswd());
        key+=getClientIpAddress(request);
        //key = ATGUIGU_GMALL_KEY
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256,key);
        //用户信息放入
        jwtBuilder = jwtBuilder.setClaims(param);
        //制作token
        String token = jwtBuilder.compact();
        return token;

    }


    public  static Map<String,Object> decode(String token , String key,HttpServletRequest request){

        Claims claims=null;
        key+=getClientIpAddress(request);
        try {
            claims= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch ( JwtException e) {
            return null;
        }
        return  claims;
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("x-forwarded-for");
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }


}
