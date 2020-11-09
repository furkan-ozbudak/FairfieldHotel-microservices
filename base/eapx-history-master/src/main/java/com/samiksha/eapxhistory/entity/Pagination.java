package com.samiksha.eapxhistory.entity;

import lombok.Data;

@Data
public class Pagination {
    private long pages;
    private int current;
    private int perPage;
}
