package com.max.eapxreservation.VO;

import com.max.eapxreservation.domain.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class RoomVOTools {

    public static Mono<?> returnRoomVoMo(Mono<Reservation> mono, String message) {
        return mono.map(res1 -> {
            ResultVO<Reservation> resultVO1 = new ResultVO<>();
            resultVO1.setCode(0);
            resultVO1.setMsg(message);
            resultVO1.setData(res1);
            return resultVO1;
        });
    }



    public static Mono<?> returnRoomVo(Flux<Reservation> flux) {
        Mono<List<Reservation>> listMono = flux.collectList();

        return listMono.map(reservations -> {
            ResultVO<List<Reservation>> resultVO1 = new ResultVO<>();
            resultVO1.setCode(0);
            resultVO1.setMsg("success");
            resultVO1.setData(reservations);
            return  resultVO1;
        });
    }
}
