package edu.mum.user.dao;

import edu.mum.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends MongoRepository<User,String> {
    public User findByEmail(String email);
}
