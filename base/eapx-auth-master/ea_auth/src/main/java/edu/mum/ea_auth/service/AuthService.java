package edu.mum.ea_auth.service;

import edu.mum.ea_auth.entity.User;
import edu.mum.common.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JWTUtil jwtUtil;

    public String generateToken(String id, String email, String role){
        String token = jwtUtil.createJWT(id,email,role);
        redisTemplate.opsForHash();
        return token;
    }

    public Boolean verify(String token){
            if (token.startsWith("Bearer ")){
                token = token.substring(7);
                try {
                    Claims claims =  jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("role");
                    //to do
                    return true;
                }catch (Exception e){
                    return false;
                }
            }
            return false;
        }

}
