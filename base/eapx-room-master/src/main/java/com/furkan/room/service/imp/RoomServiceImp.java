package com.furkan.room.service.imp;

import com.furkan.room.enums.Status;
import com.furkan.room.model.Response;
import com.furkan.room.model.ResponseData;
import com.furkan.room.repository.RoomRepository;
import com.furkan.room.model.Room;
import com.furkan.room.service.LogService;
import com.furkan.room.service.RoomService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;



@Service
public class RoomServiceImp implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    LogService logService;

    @Override
    public Room save(Room room) {
        setPricePerDay(room);
        return roomRepository.save(room);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            logService.write("Wrong ID format has given for a room.");
            return null;
        }
        return roomRepository.findById(objectId).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        ObjectId objectId = null;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            logService.write("Deleting the room with ID: " + id + " was not successful.");
        }
        roomRepository.deleteById(objectId);
    }

    @Override
    public Room update(Room room) {
        setPricePerDay(room);
        return roomRepository.save(room);
    }

    @Override
    public Response reserveRoom(String id, String startDayLiteral, String endDayLiteral) {
        Response response = new Response();
        LocalDate startDay = LocalDate.parse(startDayLiteral);
        LocalDate endDay = LocalDate.parse(endDayLiteral);
        if (startDay.isAfter(endDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservation end date can not be earlier than start date.");
            logService.write("Reservation end date can not be earlier than start date.");
            return response;
        }
        if (startDay.getYear() != endDay.getYear()) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservation date interval should be in the same year.");
            logService.write("Reservation date interval should be in the same year.");
            return response;
        }
        if (LocalDate.now().isAfter(startDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Bad reservation date. Requested reservation date is in the past.");
            logService.write("Bad reservation date. Requested reservation date is in the past.");
            return response;
        }
        Room room = findById(id);
        if (room == null) {
            response.setStatus(Status.ERROR);
            response.setMessage("No room found with the given id: " + id);
            logService.write("No room found with the given id: " + id);
            return response;
        }

        Period intervalPeriod = Period.between(startDay, endDay);
        if (intervalPeriod.getMonths() >= 1) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservations can't be longer than a month.");
            logService.write("Reservations can't be longer than a month.");
            return response;
        }

        for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
            if (room.getReservedDateList().contains(startDay.plusDays(i))) {
                response.setStatus(Status.NEGATIVE);
                response.setMessage("This " + room.getType() + " room is already reserved for the date: " + startDay.plusDays(i));
                return response;
            }
        }

        for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
            addReservationDay(startDay.plusDays(i), room);
        }

        roomRepository.save(room);
        response.setStatus(Status.SUCCESS);
        response.setMessage("This " + room.getType()
                + " room is now reserved from: " + startDay + " to " + endDay);
        return response;
    }

    @Override
    public Response cancelReservation(String id, String startDayLiteral, String endDayLiteral) {
        Response response = new Response();
        LocalDate startDay = LocalDate.parse(startDayLiteral);
        LocalDate endDay = LocalDate.parse(endDayLiteral);
        if (startDay.isAfter(endDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservation end date can not be earlier than the start date.");
            logService.write("Reservation end date can not be earlier than the start date.");
            return response;
        }
        if (startDay.getYear() != endDay.getYear()) {
            response.setStatus(Status.ERROR);
            response.setMessage("Given reservation interval is in different years.");
            logService.write("Given reservation interval is in different years.");
            return response;
        }
        if (LocalDate.now().isAfter(startDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Past reservations can't be cancelled.");
            logService.write("Past reservations can't be cancelled.");
            return response;
        }
        Room room = findById(id);
        if (room == null) {
            response.setStatus(Status.ERROR);
            response.setMessage("No room exists with the given id: " + id);
            logService.write("No room exists with the given id: " + id);
            return response;
        }
        Period intervalPeriod = Period.between(startDay, endDay);
        if (intervalPeriod.getMonths() >= 1) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservations can't be longer than a month.");
            logService.write("Reservations can't be longer than a month.");
            return response;
        }

        for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
            if (!(room.getReservedDateList().contains(startDay.plusDays(i)))) {
                response.setStatus(Status.NEGATIVE);
                response.setMessage("Wrong reservation period has given. This " + room.getType() + " room does not have any reservation at " + startDay.plusDays(i));
                return response;
            }
        }
        for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
            removeReservationDay(startDay.plusDays(i), room);
        }
        roomRepository.save(room);
        response.setStatus(Status.SUCCESS);
        response.setMessage("This " + room.getType()
                 + " room's reservation from: " + startDay + " to " + endDay + " is canceled.");
        return response;
    }

    @Override
    public ResponseData findRoomsAvailableForGivenDate(String startDayLiteral, String endDayLiteral) {
        ResponseData<List<Room>> responseData = new ResponseData<>();
        LocalDate startDay = LocalDate.parse(startDayLiteral);
        LocalDate endDay = LocalDate.parse(endDayLiteral);

        if (startDay.isAfter(endDay)) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservation end date can not be earlier than start date.");
            logService.write("Reservation end date can not be earlier than start date.");
            return responseData;
        }
        if (startDay.getYear() != endDay.getYear()) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservation date interval should be in the same year.");
            logService.write("Reservation date interval should be in the same year.");
            return responseData;
        }
        if (LocalDate.now().isAfter(startDay)) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Bad reservation date. Requested reservation date is in the past.");
            logService.write("Bad reservation date. Requested reservation date is in the past.");
            return responseData;
        }

        Period intervalPeriod = Period.between(startDay, endDay);
        if (intervalPeriod.getMonths() >= 1) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservations can't be longer than a month.");
            logService.write("Reservations can't be longer than a month.");
            return responseData;
        }
        List<Room> roomList = findAll();
        List<Room> roomListByDate = new ArrayList<>();
        boolean reservationAvailable = true;
        for (Room room : roomList) {
            for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
                if (room.getReservedDateList().contains(startDay.plusDays(i))) {
                    reservationAvailable = false;
                    break;
                }
            }
            if (reservationAvailable) {
                roomListByDate.add(room);
            }
        }
        responseData.setStatus(Status.SUCCESS);
        responseData.setMessage("Available rooms are listed.");
        responseData.setData(roomListByDate);
        return responseData;
    }

    /**
     * Room type is case sensitive!
     */
    @Override
    public ResponseData findRoomsAvailableForGivenDateAndRoomType(String startDayLiteral, String endDayLiteral, String roomType) {
        ResponseData<List<Room>> responseData = new ResponseData<>();
        LocalDate startDay = LocalDate.parse(startDayLiteral);
        LocalDate endDay = LocalDate.parse(endDayLiteral);
        if (startDay.isAfter(endDay)) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservation end date can not be earlier than start date.");
            logService.write("Reservation end date can not be earlier than start date.");
            return responseData;
        }
        if (startDay.getYear() != endDay.getYear()) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservation date interval should be in the same year.");
            logService.write("Reservation date interval should be in the same year.");
            return responseData;
        }
        if (LocalDate.now().isAfter(startDay)) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Bad reservation date. Requested reservation date is in the past.");
            logService.write("Bad reservation date. Requested reservation date is in the past.");
            return responseData;
        }

        Period intervalPeriod = Period.between(startDay, endDay);
        if (intervalPeriod.getMonths() >= 1) {
            responseData.setStatus(Status.ERROR);
            responseData.setMessage("Reservations can't be longer than a month.");
            logService.write("Reservations can't be longer than a month.");
            return responseData;
        }

        List<Room> roomListByType = findByRoomType(roomType);
        List<Room> roomListByTypeAndDate = new ArrayList<>();
        boolean reservationAvailable = true;
        for (Room room : roomListByType) {
            for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
                if (room.getReservedDateList().contains(startDay.plusDays(i))) {
                    reservationAvailable = false;
                    break;
                }
            }
            if (reservationAvailable) {
                roomListByTypeAndDate.add(room);
            }
        }
        responseData.setStatus(Status.SUCCESS);
        responseData.setMessage("Available rooms are listed.");
        responseData.setData(roomListByTypeAndDate);
        return responseData;
    }

    @Override
    public Response checkRoomIfAvailable(String id, String startDayLiteral, String endDayLiteral) {
        Response response = new Response();
        LocalDate startDay = LocalDate.parse(startDayLiteral);
        LocalDate endDay = LocalDate.parse(endDayLiteral);
        if (startDay.isAfter(endDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservation end date can not be earlier than the start date.");
            logService.write("Reservation end date can not be earlier than the start date.");
            return response;
        }
        if (startDay.getYear() != endDay.getYear()) {
            response.setStatus(Status.ERROR);
            response.setMessage("Given reservation interval is in different years.");
            logService.write("Given reservation interval is in different years.");
            return response;
        }
        if (LocalDate.now().isAfter(startDay)) {
            response.setStatus(Status.ERROR);
            response.setMessage("Past reservation date is inputted.");
            logService.write("Past reservation date is inputted.");
            return response;
        }
        Room room = findById(id);
        if (room == null) {
            response.setStatus(Status.ERROR);
            response.setMessage("No room exists with the given id: " + id);
            logService.write("No room exists with the given id: " + id);
            return response;
        }
        Period intervalPeriod = Period.between(startDay, endDay);
        if (intervalPeriod.getMonths() >= 1) {
            response.setStatus(Status.ERROR);
            response.setMessage("Reservations can't be longer than a month.");
            logService.write("Reservations can't be longer than a month.");
            return response;
        }

        for (int i = 0; i <= endDay.getDayOfYear() - startDay.getDayOfYear(); i++) {
            if ((room.getReservedDateList().contains(startDay.plusDays(i)))) {
                response.setStatus(Status.NEGATIVE);
                response.setMessage("not available at: " + startDay.plusDays(i));
                return response;
            }
        }

        response.setStatus(Status.SUCCESS);
        response.setMessage("room is available from " + startDay + " to " + endDay);
        return response;
    }

    @Override
    public List<Room> findByRoomType(String roomType) {
        return roomRepository.findByType(roomType);
    }

    @Override
    public void addReservationDay(LocalDate date, Room room) {
        room.getReservedDateList().add(date);
        save(room);
    }

    @Override
    public void removeReservationDay(LocalDate date, Room room) {
        room.getReservedDateList().remove(date);
        save(room);
    }

    public void setPricePerDay(Room room) {
        double pricePerDay = 0;
        switch (room.getType()) {
            case SINGLE:
                pricePerDay = 75;
                break;
            case DOUBLE:
                pricePerDay = 90;
                break;
            case TRIPLE:
                pricePerDay = 105;
                break;
            case QUAD:
                pricePerDay = 120;
                break;
            case STUDIO:
                pricePerDay = 60;
                break;
            case SUITE:
                pricePerDay = 150;
                break;
        }

        if (room.isSmoking()) {
            pricePerDay += 100;
        }
        if (room.isParking()) {
            pricePerDay += 10;
        }
        room.setPricePerDay(pricePerDay);
    }

}
