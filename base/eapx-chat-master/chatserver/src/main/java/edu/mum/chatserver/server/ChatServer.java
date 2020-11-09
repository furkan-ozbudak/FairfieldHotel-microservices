package edu.mum.chatserver.server;

import edu.mum.chatserver.model.Status;
import edu.mum.chatserver.service.StatusService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ChatServer {
    @Autowired
    private MQController mqController;
    @Autowired
    private ChatChannelInitializer chatChannelInitializer;

    public void start() throws InterruptedException {
        mqController.startRcv();


        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(chatChannelInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(10087)).sync();
        channelFuture.channel().closeFuture().sync();
        mainGroup.shutdownGracefully();
        subGroup.shutdownGracefully();
    }
}
