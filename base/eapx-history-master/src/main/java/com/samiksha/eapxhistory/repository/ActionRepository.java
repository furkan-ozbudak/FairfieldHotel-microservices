package com.samiksha.eapxhistory.repository;

import com.samiksha.eapxhistory.entity.Action;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActionRepository extends MongoRepository<Action, ObjectId> {
    List<Action> findByDate(LocalDate date);
    void deleteByDateBefore(LocalDate date);
}
