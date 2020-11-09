package edu.mum.chat.controller;

import edu.mum.chat.model.Status;
import edu.mum.chat.model.User;
import edu.mum.chat.service.StatusService;
import edu.mum.chat.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {
    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/gen")
    public User gen(@RequestBody User user){
        user = userService.save(user);
        on(user);
        return user;
    }

    @GetMapping("/gen")
    public User gen(@RequestParam("name") String name){
        User user = new User();
        user.setName(name);
        user = userService.save(user);
        on(user);
        return user;
    }

    @GetMapping("/on")
    public Status on(@RequestParam("id") String id){
        Optional<User> op = userService.findById(id);
        User user = null;
        if (op.isPresent()){
            user = op.get();
        }else {
            user = new User();
            user.setName(RandomStringUtils.randomAlphanumeric(10));
            user = userService.save(user);
        }

        return statusService.save(new Status(user.getId(),user));
    }

    @PostMapping("/on")
    public Status on(@RequestBody User user){
        Optional<User> op = userService.findById(user.getId());
        user = null;
        if (op.isPresent()){
            user = op.get();
        }else {
            user = new User();
            user.setName(RandomStringUtils.randomAlphanumeric(10));
            user = userService.save(user);
        }

        return statusService.save(new Status(user.getId(),user));
    }

    @GetMapping("/off")
    public HttpStatus off(@RequestParam("id") String id){
        statusService.del(id);
        return HttpStatus.OK;
    }

    @PostMapping("/off")
    public HttpStatus off(@RequestBody User user){
        statusService.del(user.getId());
        return HttpStatus.OK;
    }

    @GetMapping("/online")
    public List<Status> online(){
        return statusService.all();
    }

}
