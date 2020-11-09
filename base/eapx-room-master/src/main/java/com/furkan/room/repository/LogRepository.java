package com.furkan.room.repository;

import com.furkan.room.model.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, ObjectId> {
    //List<Log> findAllByDateTimeAfterAndDateTimeBefore(LocalDateTime startDateTime, LocalDateTime endDateTime);
    //List<Log> findAllByDateTimeAfterAndDateTimeBefore(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Log> findAllByDateTimeAfter(LocalDateTime dateTime);
    List<Log> findAllByDateTimeBefore(LocalDateTime dateTime);
}
