package edu.mum.chatserver.service;

import edu.mum.chatserver.model.User;
import edu.mum.chatserver.repository.UserRepository;
import edu.mum.chatserver.tools.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdWorker idWorker;

    public User save(User user){
        user.setId(idWorker.nextId()+"");
        return userRepository.save(user);
    }

    public Optional<User> findById(String id){
        return userRepository.findById(id);
    }



}
