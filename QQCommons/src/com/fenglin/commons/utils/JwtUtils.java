package com.fenglin.commons.utils;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 */

public class JwtUtils {

    /**
     * 签发JWT
     * @param id loginCode
     * @param username 用户名
     * @param map JSON数据,验证信息的主要部分
     * @param secretKey 加密密钥
     * @param ttl 令牌有效时长
     * @return  String
     */
    public static String createJWT(String id, String username, Map<String,Object> map, String secretKey, long ttl) {

        //1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject("SSO-TOKEN")   // 主题
                .setIssuer(username)     // 签发者
                .setIssuedAt(new Date())      // 签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey); // 签名算法以及密匙

        //2. 根据map设置claims,比如用户的角色,公司的id,公司的名称等
        jwtBuilder.setClaims(map);

        // 过期时间
        Date expDate = new Date(System.currentTimeMillis() + ttl);
        jwtBuilder.setExpiration(expDate);
        return jwtBuilder.compact();
    }

    /**
     *解析JWT字符串
     *
     * @param jwtStr
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwtStr, String secretKey)  {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
