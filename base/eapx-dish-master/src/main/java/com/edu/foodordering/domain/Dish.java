package com.edu.foodordering.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@Setter
@Getter
//@Entity
@Document
public class Dish {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    double price;
}
