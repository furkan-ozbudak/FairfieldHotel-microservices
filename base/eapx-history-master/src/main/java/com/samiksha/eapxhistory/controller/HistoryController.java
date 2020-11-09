package com.samiksha.eapxhistory.controller;

import com.samiksha.eapxhistory.entity.Action;
import com.samiksha.eapxhistory.entity.History;
import com.samiksha.eapxhistory.entity.Reservation;
import com.samiksha.eapxhistory.entity.Room;
import com.samiksha.eapxhistory.repository.ReservationRepository;
import com.samiksha.eapxhistory.service.ActionService;
import com.samiksha.eapxhistory.service.HistoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ActionService actionService;
//
//    @Autowired
//    TestEntityRepository testEntityRepository;

    @Autowired
    private HistoryService service;

    @GetMapping("")
    public History getHistory() {
        return service.getAllReservations(1);
    }

    @GetMapping("{page}")
    public History getHistory(@PathVariable("page") int page) {
        return service.getAllReservations(page);
    }

    @GetMapping("/user/{id}")
    public History getHistoryByUser(@PathVariable("id") ObjectId userId) {
        return service.getReservationsByUser(userId, 1);
    }

    @GetMapping("/user/{id}/{page}")
    public History getHistoryByUser(@PathVariable("page") int page, @PathVariable("id") ObjectId userId) {
        return service.getReservationsByUser(userId, page);
    }

//    @PostMapping(value = "saveReservation")
//    public Reservation reservation(@RequestBody Reservation reservation) {
//        return reservationRepository.save(reservation);
//    }
//
//    @PostMapping(value = "testSave")
//    public TestEntity save(@RequestBody TestEntity testEntity) {
//        return testEntityRepository.save(testEntity);
//    }

    // ---------------------------------------------------------------------------------

    @PostMapping(value = "/write")
    public void write(@RequestBody Map<String, String> json) {
        actionService.write(json.get("userId"), json.get("message"));
    }

    @GetMapping(value = "/read")
    public List<Action> read() {
        return actionService.readAll();
    }

    @GetMapping(value = "/readWithDate")
    public List<Action> readWithDate(@RequestParam String date) {
        return actionService.readByDate(date);
    }

    @PostMapping(value = "/deleteWithDate")
    public void deleteWithDate(@RequestBody Map<String,String> json) {
        actionService.deleteAllActionsBeforeTheGivenDate(json.get("date"));
    }


}
