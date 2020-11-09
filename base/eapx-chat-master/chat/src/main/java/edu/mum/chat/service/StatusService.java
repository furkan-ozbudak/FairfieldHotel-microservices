package edu.mum.chat.service;

import edu.mum.chat.model.Status;
import edu.mum.chat.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public Status save(Status status){
        return statusRepository.save(status);
    }

    public void del(String id){
         statusRepository.deleteById(id);
    }

    public List<Status> all(){
        return (List<Status>) statusRepository.findAll();
    }

}
