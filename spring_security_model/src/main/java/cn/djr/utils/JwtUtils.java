package cn.djr.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmConstraints;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    //默认有效期:一小时
    public static final long JWT_TTL = 1000 * 60 * 60L;

    //设置密钥（盐值）
    public static final String JWT_KEY = "djr6866";

    //用于生成令牌的uuid
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    //用于生成令牌的加密后盐值
    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtils.JWT_KEY);
        SecretKeySpec key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    //创建JWT令牌
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder jwtBuilder = getJwtBuilder(subject, ttlMillis, getUUID());
        return jwtBuilder.compact();
    }

    //生成jwt的业务逻辑代码
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {

        //指定签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //加密后的盐值
        SecretKey secretKey = generalKey();

        //系统当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //如果没指定过期时间，则默认1小时
        if (ttlMillis == null) {
            ttlMillis = JwtUtils.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)           //唯一id
                .setSubject(subject)   //主题
                .setIssuer("djr")      //签发者
                .setIssuedAt(now)      //签发时间
                .signWith(signatureAlgorithm, secretKey) //签名算法和密钥
                .setExpiration(expDate); //过期时间
    }

    //解析JWT令牌
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}
