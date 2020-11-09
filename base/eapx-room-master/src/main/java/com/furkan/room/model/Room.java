package com.furkan.room.model;

import com.furkan.room.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rooms")
public class Room {
    @Id
    private ObjectId id;
    private Type type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> reservedDateList = new ArrayList<>();
    private boolean smoking;
    private boolean parking;
    private double pricePerDay;
}
