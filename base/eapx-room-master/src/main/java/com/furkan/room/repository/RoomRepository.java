package com.furkan.room.repository;

import com.furkan.room.model.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, ObjectId> {
    List<Room> findByType(String roomType);
    //List<Room> findByReservedDateListNotContains(LocalDate date);
}
