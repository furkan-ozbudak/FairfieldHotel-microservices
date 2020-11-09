package edu.mum.chatserver.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Status")
public class Status  {
    private String id;
    private User user;

    public Status(){}

    public Status(String id, User user) {
        this.id = id;
        this.user = user;
    }
}
