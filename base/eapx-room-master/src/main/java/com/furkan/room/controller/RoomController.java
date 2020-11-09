package com.furkan.room.controller;

import com.furkan.room.model.Log;
import com.furkan.room.model.Response;
import com.furkan.room.model.ResponseData;
import com.furkan.room.model.Room;
import com.furkan.room.service.LogService;
import com.furkan.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @Autowired
    LogService logService;

    @GetMapping(value = "list")
    public List<Room> roomList() {
        return roomService.findAll();
    }

    @GetMapping(value = "findRoom")
    public Room findRoom(@RequestParam String id) {
        return roomService.findById(id);
    }

    @GetMapping(value = "roomsWithDate")
    public ResponseData findRoomsForGivenDate(@RequestParam Map<String, String> json) {
        return roomService.findRoomsAvailableForGivenDate(json.get("firstDay"), json.get("lastDay"));
    }

    @GetMapping(value = "roomsWithDateAndType")
    public ResponseData findRoomsForGivenDateAndRoomType(@RequestParam Map<String, String> json) {
        return roomService.findRoomsAvailableForGivenDateAndRoomType(json.get("firstDay"), json.get("lastDay"), json.get("roomType"));
    }

    @GetMapping(value = "checkRoomIfAvailable")
    public Response checkRoomIfAvailable(@RequestParam Map<String, String> json) {
        return roomService.checkRoomIfAvailable(json.get("id"), json.get("firstDay"), json.get("lastDay"));
    }

    @PostMapping(value = "save")
    public Room saveRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PostMapping(value = "delete")
    public void deleteRoom(@RequestBody String id) {
        roomService.deleteById(id);
    }

    @PostMapping(value = "update")
    public Room updateRoom(@RequestBody Room room) {
        return roomService.update(room);
    }

    @PostMapping(value = "reserve")
    public Response reserveRoom(@RequestBody Map<String, String> json) {
        return roomService.reserveRoom(json.get("id"), json.get("firstDay"), json.get("lastDay"));
    }

    @PostMapping(value = "cancelReservation")
    public Response cancelReservation(@RequestBody Map<String, String> json) {
        return roomService.cancelReservation(json.get("id"), json.get("firstDay"), json.get("lastDay"));
    }

    @GetMapping(value = "errors")
    public List<Log> getErrors() {
        return logService.findAll();
    }

    @GetMapping(value = "errorsWithDate")
    public List<Log> getErrorsGivenDate(@RequestParam String day) {
        return logService.findByDay(day);
    }


}
