package com.samiksha.eapxhistory.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "actions")
@Getter
@Setter
@NoArgsConstructor
public class Action {
    @Id
    private ObjectId id;
    private String message;
    private String userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
