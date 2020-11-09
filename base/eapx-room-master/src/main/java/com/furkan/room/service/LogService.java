package com.furkan.room.service;

import com.furkan.room.model.Log;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    void write(String message);

    List<Log> findAll();

    List<Log> findByDay(String time);

}
