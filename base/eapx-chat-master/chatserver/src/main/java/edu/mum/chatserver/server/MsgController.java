package edu.mum.chatserver.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mum.chatserver.model.Message;
import edu.mum.chatserver.service.StatusService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


@Component
public class MsgController {
    @Autowired
    private  StatusService statusService;
    @Autowired
    private  MQController mqController;

    private  ObjectMapper objectMapper = new ObjectMapper();
    private  Map<String,Channel>  channels = new HashMap();
    private  Map<ChannelId,String> ids = new HashMap<>();

    public  void logic(String msg,Channel channel) throws IOException {
        Message m = objectMapper.readValue(msg,Message.class);
        if (m.getType()== Message.Type.On){
            ids.put(channel.id(),m.getFromId());
            channels.put(m.getFromId(),channel);
            statusService.save(m.getFromId());
            m.getUsers().add(statusService.getById(m.getFromId()));
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(m)));
            mqController.bindKey(m.getFromId());
        }else if (m.getType()==Message.Type.Off){
            remove(channel.id());
            channel.close();
        }else if (m.getType()==Message.Type.Msg){
            String toId = m.getToId();
            System.out.println("to id "+ toId);
            if (channels.containsKey(m.getToId())){
                channels.get(m.getToId()).writeAndFlush(new TextWebSocketFrame(msg));
            }else {
                System.out.println("publish to rabbitMQ "+ m.getToId());
                mqController.publish(m.getToId(),msg);
            }
//            mqController.publish(m.getToId(),msg);
//            mqController.publish(m.getFromId(),msg);

        }else if (m.getType()==Message.Type.All){
            Message message = new Message();
            message.setToId(m.getFromId());
            message.setType(Message.Type.All);
            message.setUsers(statusService.all());

            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(message)));
        }
    }

    public  void remove(ChannelId channelId) throws IOException {
        if (ids.containsKey(channelId)){
            if (channels.containsKey(ids.get(channelId))){
                channels.remove(ids.get(channelId));
                mqController.unbindKey(ids.get(channelId));
                statusService.del(ids.get(channelId));
            }
            ids.remove(channelId);
        }
    }
}
