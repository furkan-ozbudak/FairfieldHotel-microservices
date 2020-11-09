package com.furkan.room.service.imp;

import com.furkan.room.model.Log;
import com.furkan.room.repository.LogRepository;
import com.furkan.room.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImp implements LogService {
    @Autowired
    LogRepository logRepository;

    @Override
    public void write(String message) {
        Log log = new Log();
        log.setMessage(message);
        log.setDateTime(LocalDateTime.now());
        logRepository.save(log);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public List<Log> findByDay(String dayLiteral) {
        LocalDate day = LocalDate.parse(dayLiteral);
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(23, 59, 59);

        List<Log> errorsByDate = new ArrayList<>();

        for (Log log : logRepository.findAll()) {
            if (log.getDateTime().isAfter(startOfDay) && log.getDateTime().isBefore(endOfDay)) {
                errorsByDate.add(log);
            }
        }

        return errorsByDate;
    }


}
