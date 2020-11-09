package com.furkan.room.service;

import com.furkan.room.model.Response;
import com.furkan.room.model.ResponseData;
import com.furkan.room.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Room save(Room room);
    List<Room> findAll();
    Room findById(String id);
    void deleteById(String id);
    Room update(Room room);
    Response reserveRoom(String id, String startDay, String endDay);
    Response cancelReservation(String id, String startDay, String endDay);
    ResponseData findRoomsAvailableForGivenDate(String firstDay, String lastDay);
    ResponseData findRoomsAvailableForGivenDateAndRoomType(String firstDay, String endDay, String roomType);
    Response checkRoomIfAvailable(String id, String firstDay, String endDay);
    List<Room> findByRoomType(String roomType);
    void addReservationDay(LocalDate date, Room room);
    void removeReservationDay(LocalDate date, Room room);

}
