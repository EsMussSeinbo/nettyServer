package com.hisense.nettyServer.controller;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hisense.nettyServer.support.util.IPUtils;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author qiu 
**/
public class NettyServer {
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	private final int port;

	public NettyServer(int port) {
		this.port = port;
	}

	public void startServer() {
		new Thread(){
			@Override
			public void run(){


				System.out.println("netty进入！");
				// 处理TCP请求
				EventLoopGroup bossGroup = null;
				// 处理IO事件
				EventLoopGroup workerGroup = null;
				try {
					
					Properties properties = new Properties();
			        properties.put("bootstrap.servers", "20.2.15.25:9092,20.2.15.26:9092,20.2.15.29:9092");
			        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
					final Producer<String,String> producer = new KafkaProducer<String,String>(properties);
					//Producer<String,String> producer = SingletonObject.getProducer();
					// server端引导类
					ServerBootstrap serverBootstrap = new ServerBootstrap();
					//处理TCP请求
					bossGroup = new NioEventLoopGroup();
					//处理IO事件
					workerGroup = new NioEventLoopGroup();
					serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)// 指定通道类型为NioServerSocketChannel，一种异步模式，OIO阻塞模式为OioServerSocketChannel
		  .localAddress(PropertiesUtil.getName("SERVER_IP"), port)// 设置InetSocketAddress让服务器监听某个端口已等待客户端连接。

			  .childHandler(new ChannelInitializer<Channel>() {// 设置childHandler执行所有的连接请求
								@Override
								protected void initChannel(Channel ch) throws Exception {
									ch.pipeline().addLast(new NettyServerDecoder());// 解码器
									ch.pipeline().addLast(new NettyServerHandler(producer));// 注册handler
								}
							});
					// 最后绑定服务器等待直到绑定完成，调用sync()方法会阻塞直到服务器完成绑定,然后服务器等待通道关闭，因为使用sync()，所以关闭操作也会被阻塞。
					ChannelFuture channelFuture = serverBootstrap.bind().sync();
					logger.info("开始监听，端口为：" + channelFuture.channel().localAddress());
					channelFuture.channel().closeFuture().sync();
					logger.info("结束！！！");
				} catch (Exception e) {
					logger.error("NettyServer_startServer_exception", e);
				} finally {
					try {
						bossGroup.shutdownGracefully().sync();
						workerGroup.shutdownGracefully().sync();
					} catch (Exception e) {
						logger.error("NettyServer_shutdown_exception", e);
					}
				}
			}
		}.start();;
	}
}
