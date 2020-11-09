package edu.mum.chatserver.repository;

import edu.mum.chatserver.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, String> {

}
