package com.samiksha.eapxhistory.service;

import com.samiksha.eapxhistory.entity.Action;

import java.util.List;

public interface ActionService {
    void write(String userId, String message);
    List<Action> readAll();
    List<Action> readByDate(String date);
    void deleteAllActionsBeforeTheGivenDate(String date);
}
