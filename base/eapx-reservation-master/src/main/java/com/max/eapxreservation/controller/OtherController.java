package com.max.eapxreservation.controller;

import com.max.eapxreservation.VO.ResultVO;
import com.max.eapxreservation.domain.Reservation;
import com.max.eapxreservation.handler.ReservationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/other")
public class OtherController {
    @Autowired
    private ReservationHandler reservationHandler;
    @PostMapping("/")
    public Mono<?> update(@RequestBody Reservation reservation) {
        System.out.println(reservation);

        Mono<Reservation> reservationMono = reservationHandler.findReservationById(reservation.getId()).map(res ->
                {
                    res.setStatus(reservation.getStatus());
                    return reservationHandler.updateReservation(res);
                }
        ).flatMap(res -> res);

        reservationMono.subscribe();

        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setData("{}");

        resultVO.setMsg("success");
        return Mono.just(resultVO);


//        return RoomVOTools.returnRoomVoMo(reservationMono, "update reservation");
    }
}
