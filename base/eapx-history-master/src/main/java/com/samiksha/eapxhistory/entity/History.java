package com.samiksha.eapxhistory.entity;

import lombok.Data;
import java.util.List;

@Data
public class History {
    private int status;
    private String message;
    private List<Reservation> reservations;
    private Pagination pagination;
}
