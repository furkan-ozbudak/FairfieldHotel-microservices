package edu.mum.chatserver.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Message {
    public enum Type{ On, Off, Msg, All };
    private Type type;
    private String fromId;
    private String toId;
    private String content;
    private List<Status> users = new ArrayList<>();
}
