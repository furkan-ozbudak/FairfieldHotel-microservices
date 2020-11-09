package edu.mum.user.controller;

import edu.mum.common.Result;
import edu.mum.common.StatusCode;
import edu.mum.user.entity.User;
import edu.mum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Value("${authUrl}")
    private String authUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestBody User user){
//        to do
//        String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkcode_"+user.getEmail());
//
//        if (checkcodeRedis.isEmpty()){
//            return new Result(false, StatusCode.ERROR, "Plz get email code");
//        }
        userService.add(user);
        return new Result(true,StatusCode.OK,"register suc");
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        user = userService.login(user.getEmail(),user.getPassword());

        if (user == null){

            return new Result(false,StatusCode.LOGINERROR,"Login Error");

        }
        ResponseEntity<String> response
                = restTemplate.postForEntity(authUrl + "/auth/gen_token", user, String.class);
        Map<String,Object> map = new HashMap<>();

        map.put("token",response.getBody());
        map.put("role","user");
        map.put("email",user.getEmail());
        map.put("uid",user.getId());
        return new Result(true,StatusCode.OK,"Login Suc",map);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Result testAuth(){
        return new Result(true,StatusCode.OK,"Login Suc");
    }


}
