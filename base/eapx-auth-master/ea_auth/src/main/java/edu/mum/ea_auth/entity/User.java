package edu.mum.ea_auth.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String email;
    private String role;
}
