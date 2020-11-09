package edu.mum.chatserver.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

@Data
@Document
public class User {
    private String id;
    private String name;
}
