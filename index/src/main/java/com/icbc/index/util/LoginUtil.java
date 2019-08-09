package com.icbc.index.util;


import com.icbc.index.entity.Manager;
import io.jsonwebtoken.*;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginUtil {


    private final static String key = "icbcsdc";

    public static String getCode(Manager manager) {
        String account = manager.getAccount();
        String passsword = manager.getPassword();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String temp = account + passsword;
            messageDigest.update(temp.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            Random random = new Random(System.currentTimeMillis());
            StringBuffer stringBuffer = new StringBuffer();
            String tempDigst = null;
            for (int i = 0; i < digest.length; i++) {
                tempDigst = Integer.toHexString(digest[i] & 0xFF);
                if (tempDigst.length() == 1) {
                    //1得到一位的进行补0操作
                    stringBuffer.append("0");
                }
                stringBuffer.append(tempDigst);
            }
            tempDigst = stringBuffer.toString();
            stringBuffer.setLength(0);
            for (int i = 0; i < 6; i++) {
                stringBuffer.append(tempDigst.charAt(random.nextInt(64)));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(Manager manager) {


        Map<String, Object> param = new HashMap<>();
        param.put("account", manager.getAccount());
        param.put("password", manager.getPassword());
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, key);
        //用户信息放入
        jwtBuilder = jwtBuilder.setClaims(param);
        //制作token
        String token = jwtBuilder.compact();
        return token;

    }


    public static Map<String, Object> decode(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            return null;
        }
        return claims;
    }


}
