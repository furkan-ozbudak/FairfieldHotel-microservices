package com.samiksha.eapxhistory.service.imp;

import com.samiksha.eapxhistory.entity.Action;
import com.samiksha.eapxhistory.repository.ActionRepository;
import com.samiksha.eapxhistory.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ActionServiceImp implements ActionService {
    @Autowired
    ActionRepository actionRepository;

    @Override
    public void write(String userId, String message) {
        Action action = new Action();
        action.setUserId(userId);
        action.setMessage(message);
        action.setDate(LocalDate.now());
        actionRepository.save(action);
    }

    @Override
    public List<Action> readAll() {
        return actionRepository.findAll();
    }

    @Override
    public List<Action> readByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        return actionRepository.findByDate(date);
    }

    @Override
    public void deleteAllActionsBeforeTheGivenDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        actionRepository.deleteByDateBefore(date);
    }


}
