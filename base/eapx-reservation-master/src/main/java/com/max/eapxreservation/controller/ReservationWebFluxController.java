package com.max.eapxreservation.controller;

import com.google.gson.Gson;
import com.max.eapxreservation.VO.ResultVO;
import com.max.eapxreservation.VO.RoomVO;
import com.max.eapxreservation.VO.RoomVOTools;
import com.max.eapxreservation.domain.Reservation;
import com.max.eapxreservation.handler.ReservationHandler;
import com.max.eapxreservation.tools.RestUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;


@RestController
@RequestMapping("/reservation")
public class ReservationWebFluxController {

    @Autowired
    private WebClient.Builder webClient;


    //    @Value("${room.check-room-available}")
//    private String roomAvailableUrl;
    @Value("${room.room-reserve}")
    private String roomReserveUrl;
    @Value("${room.room-cancel}")
    private String roomCancelUrl;

    private Gson gson = new Gson();

    @Autowired
    private ReservationHandler reservationHandler;

    @GetMapping(value = "/{id}")
    public Mono<?> findReservationById(@PathVariable("id") ObjectId id) {
        return RoomVOTools.returnRoomVoMo(reservationHandler.findReservationById(id), "success");
    }

    @GetMapping(value = "/all")
    public Mono<?> findAllReservation() {
        return RoomVOTools.returnRoomVo(reservationHandler.findAllReservation());
    }

    @GetMapping(value = "/client/{id}")
    public Mono<?> findAllReservationByClientId(@PathVariable("id") String id) {
        return RoomVOTools.returnRoomVo(reservationHandler.findAllReservationByClientId(id));
    }

    @GetMapping(value = "/room/{id}")
    public Mono<?> findAllReservationByRoomId(@PathVariable("id") String id) {
        return RoomVOTools.returnRoomVo(reservationHandler.findAllReservationByRoomId(id));
    }

    @GetMapping(value = "/status/{status}")
    public Mono<?> findAllReservationByStatus(@PathVariable("status") String status) {
        return RoomVOTools.returnRoomVo(reservationHandler.findAllReservationByStatus(status));
    }


    @PostMapping(value = "/")
    public Mono<?> findAllReservationByClient() {

        return RoomVOTools.returnRoomVo(reservationHandler.findAllReservation());
    }

    @PostMapping("/other")
    public Mono<?> update(@RequestBody Reservation reservation) {
//        System.out.println(reservation);


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

    @PostMapping("/save")
    public Mono<?> saveReservation(@RequestBody Reservation reservation) {

        System.out.println("reservation");
        System.out.println(reservation);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", reservation.getRoomId());
        map.put("firstDay", reservation.getFirstDay());
        map.put("lastDay", reservation.getLastDay());

        RoomVO res = RestUtils.requestRoom(roomReserveUrl, gson.toJson(map));
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setData("{}");
        System.out.println(res.getStatus());
        if (!res.getStatus().equals("SUCCESS")) {
            resultVO.setMsg(res.getMessage());
            return Mono.just(resultVO);
        }


        return RoomVOTools.returnRoomVoMo(reservationHandler.saveReservation(reservation), res.getMessage());

    }


    @PostMapping("/update")
    public Mono<?> updateReservation(@RequestBody Reservation reservation) {
//        System.out.println(reservation);


        Mono<Reservation> reservationMono = reservationHandler.findReservationById(reservation.getId()).map(res ->
                {
                    res.setStatus(reservation.getStatus());
                    return reservationHandler.updateReservation(res);
                }
        ).flatMap(res -> res);
        return RoomVOTools.returnRoomVoMo(reservationMono, "update reservation");
    }


    @PostMapping("/cancel")
    public Mono<?> cancelReservation(@RequestBody Reservation reservation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", reservation.getRoomId());
        map.put("firstDay", reservation.getFirstDay());
        map.put("lastDay", reservation.getLastDay());


        RoomVO res = RestUtils.requestRoom(roomCancelUrl, gson.toJson(map));
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setData("{}");

        if (!res.getStatus().equals("SUCCESS")) {
            resultVO.setMsg(res.getMessage());
            return Mono.just(resultVO);
        }

        return RoomVOTools.returnRoomVoMo(reservationHandler.cancelReservation(reservation), res.getMessage());
    }

}
