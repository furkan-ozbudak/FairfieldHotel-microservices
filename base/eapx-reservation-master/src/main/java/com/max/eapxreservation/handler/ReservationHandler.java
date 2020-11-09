package com.max.eapxreservation.handler;

import com.max.eapxreservation.dao.ReservationDao;
import com.max.eapxreservation.domain.Reservation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class ReservationHandler {

    @Autowired
    private ReservationDao reservationDao;

    public Mono<Reservation> findReservationById(ObjectId id) {
        return reservationDao.findById(id);
    }

    @Transactional
    public Mono<Reservation> cancelReservation(Reservation reservation) {
        reservation.setStatus("cancel");
        System.out.println("cancel");
        HashMap<String, Integer> map = new HashMap<>();
        return reservationDao.save(reservation);
    }

    @Transactional
    public Mono<Reservation> updateReservation(Reservation reservation) {
        reservation.setStatus("update");
        return reservationDao.save(reservation);
    }

    @Transactional
    public Mono<Reservation> saveReservation(Reservation reservation) {
        reservation.setStatus("create");
        return reservationDao.save(reservation);
    }

    @Transactional
    public Flux<Reservation> findAllReservation() {
        return reservationDao.findAll();
    }

    @Transactional
    public Flux<Reservation> findAllReservationByClientId(String clientId) {
        return reservationDao.findAllByClientId(clientId);
    }

    @Transactional
    public Flux<Reservation> findAllReservationByStatus(String status) {
        return reservationDao.findAllByStatus(status);
    }

    @Transactional
    public Flux<Reservation> findAllReservationByRoomId(String roomId) {
        return reservationDao.findAllByRoomId(roomId);
    }

}
