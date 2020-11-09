package edu.mum.chat.repository;

import edu.mum.chat.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, String> {
}
