package edu.mum.ea_auth.controller;

import edu.mum.ea_auth.entity.User;
import edu.mum.ea_auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/gen_token")
    public ResponseEntity<String> generateToken(@RequestBody User user){
        String token = authService.generateToken(user.getId(),user.getEmail(),user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<Boolean> verify(@PathVariable("token") String token){
        return ResponseEntity.status(HttpStatus.OK).body(authService.verify(token));
    }



}
