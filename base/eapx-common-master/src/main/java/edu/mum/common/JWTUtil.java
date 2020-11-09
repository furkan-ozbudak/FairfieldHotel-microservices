package edu.mum.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
        private String key = "ea_skeleton" ;

        private long ttl = 3600000 ;//3600s

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        /**
         * 生成JWT
         *
         * @param id
         * @param subject
         * @return
         */
        public String createJWT(String id, String subject, String role) {
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            JwtBuilder builder = Jwts.builder().setId(id)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .signWith(SignatureAlgorithm.HS256, key).claim("role", role);
            if (ttl > 0) {
                builder.setExpiration( new Date( nowMillis + ttl));
            }
            return builder.compact();
        }

        public Claims parseJWT(String jwtStr){
            return  Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtStr)
                    .getBody();
        }
}
