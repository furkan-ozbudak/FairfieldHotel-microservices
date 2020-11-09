package edu.mum.chatserver.service;

import edu.mum.chatserver.model.Status;
import edu.mum.chatserver.model.User;
import edu.mum.chatserver.repository.StatusRepository;
import edu.mum.chatserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    public Status save(Status status){
        return statusRepository.save(status);
    }

    public Status save(String id){
        Optional<User> optionalStatus = userRepository.findById(id);
        if (optionalStatus.isPresent()){
            return statusRepository.save(new Status(id,optionalStatus.get()));
        }else {
            return null;
        }
    }

    public void del(String id){
         statusRepository.deleteById(id);
    }

    public List<Status> all(){
        return (List<Status>) statusRepository.findAll();
    }

    public Status getById(String id){
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if (optionalStatus.isPresent()){
            return optionalStatus.get();
        }else {
            return null;
        }
    }

    public void clear(){
        statusRepository.deleteAll();
    }

}
