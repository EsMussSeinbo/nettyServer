package com.hisense.nettyServer.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiu 
**/
public class NettyServerListenner implements ServletContextListener{

	private static Logger logger = LoggerFactory.getLogger("errorLog");
	
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("321321");
		System.out.println("NettyServerListener!!!!"+Thread.currentThread().getName());
		// TODO Auto-generated method stub
		NettyServer nettyServer = new NettyServer(12001);
		nettyServer.startServer();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
