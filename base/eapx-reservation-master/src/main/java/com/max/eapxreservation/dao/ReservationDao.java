package com.max.eapxreservation.dao;


import com.max.eapxreservation.domain.Reservation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReservationDao extends ReactiveMongoRepository<Reservation, ObjectId> {

    Flux<Reservation> findAllByClientId(String clientId);


    Flux<Reservation> findAllByStatus(String status);

    Flux<Reservation> findAllByRoomId(String roomId);
}
