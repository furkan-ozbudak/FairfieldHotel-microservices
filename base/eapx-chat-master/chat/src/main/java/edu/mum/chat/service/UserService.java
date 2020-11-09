package edu.mum.chat.service;

import edu.mum.chat.model.User;
import edu.mum.chat.repository.UserRepository;
import edu.mum.chat.tools.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
