package com.samiksha.eapxhistory.repository;

import com.samiksha.eapxhistory.entity.Reservation;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReservationRepository extends MongoRepository<Reservation, ObjectId> {
    long countAllByUserId(ObjectId userId);

    Page<Reservation> findAllByUserId(ObjectId userId, Pageable pageable);
}
