package edu.mum.chatserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    public void test1() throws JsonProcessingException {
        Message m = new Message();
        m.setType(Message.Type.On);
        m.setFromId("1241242134214");
        m.setToId("980979875463");
        m.setContent("HI there!!!");
        m.getUsers().add(new Status());

        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(m);
        System.out.println(str);
        Message m2 = objectMapper.readValue(str,Message.class);
        assert(m.getContent().equals(m2.getContent()));
    }

}