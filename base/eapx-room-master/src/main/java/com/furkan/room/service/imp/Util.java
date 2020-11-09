package com.furkan.room.service.imp;

import java.time.LocalDate;

public class Util {
    public static LocalDate stringToLocalDate(String date) {
        String[] datePieces = date.split("-");
        LocalDate reservationDate = LocalDate.of(Integer.parseInt(datePieces[0]), Integer.parseInt(datePieces[1]), Integer.parseInt(datePieces[2]));
        return reservationDate;
    }
}
