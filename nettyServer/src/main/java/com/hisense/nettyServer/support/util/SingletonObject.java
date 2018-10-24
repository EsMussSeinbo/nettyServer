package com.hisense.nettyServer.support.util;

import java.text.DateFormat;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.enterprise.inject.New;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import com.hisense.nettyServer.dataformat.DataFormat;
import com.hisense.nettyServer.dataformat.DataFormatSupplement;

/**
 * 全局对象,单例模式创建
 * @author qiush
 *
 */
public class SingletonObject {
	
	
	/**     
	 * 创建一个线程池(完整入参):      
	 * 核心线程数为5 (corePoolSize),      
	 * 最大线程数为10 (maximumPoolSize),
	 * 存活时间为60分钟(keepAliveTime),
	 * 工作队列为LinkedBlockingQueue (workQueue),
	 * 线程工厂为默认的DefaultThreadFactory (threadFactory),
	 * 饱和策略(拒绝策略)为AbortPolicy: 抛出异常(handler).     
	 */    
	private SingletonObject(){}
	
	private static ExecutorService threadPool = new ThreadPoolExecutor(5, 56, 60, TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    private static Enums enums=new Enums();
    private static FtpDownLoadUtil ftpDownLoadUtil=new FtpDownLoadUtil();
    private static DataFormat dataFormat =new DataFormat();
    private static DataFormatSupplement dataFormatSupplement =new DataFormatSupplement();
	public static ExecutorService getThreadPool() {
		return threadPool;
	}
	public static Enums getEnums() {
		return enums;
	}
	public static FtpDownLoadUtil getFtpDownLoadUtil() {
		return ftpDownLoadUtil;
	}
	public static DataFormat getDataFormat() {
		return dataFormat;
	}
	public static DataFormatSupplement getDataFormatSupplement() {
		return dataFormatSupplement;
	}
}
