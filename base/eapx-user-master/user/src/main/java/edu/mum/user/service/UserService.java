package edu.mum.user.service;

import edu.mum.common.IdWorker;
import edu.mum.user.dao.UserDAO;
import edu.mum.user.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private IdWorker idWorker;

    public User login(String email, String password) {
        User user =   userDAO.findByEmail(email);

        if (user != null && bCryptPasswordEncoder.matches(password,user.getPassword())){
            return user;
        }
        return null;
    }

    public void add(User user) {
        user.setId( idWorker.nextId()+"");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public void sendEmail(String email) {
        String checkcode = RandomStringUtils.randomNumeric(6);
        System.out.println("Code: "+checkcode);
    }
}
