package com.hisense.nettyServer.controller;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hisense.nettyServer.model.dao.DeviceLog.TlDeviceMapper;
import com.hisense.nettyServer.model.dao.GeneralIllegal.TdIllegaleventMapper;
import com.hisense.nettyServer.model.dao.OperaLog.TlMasterMapper;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventMapper;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventpicMapper;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventvideoMapper;
import com.hisense.nettyServer.support.job.DeviceLog;
import com.hisense.nettyServer.support.job.GeneralIllegal;
import com.hisense.nettyServer.support.job.OperaLog;
import com.hisense.nettyServer.support.job.TrafficAccident;
import com.hisense.nettyServer.support.util.SpringContextHolder;

/**
 * FTP数据传输
 * @author qiush
 *
 */
public class TimerListenner implements ServletContextListener{

	private static Logger logger = LoggerFactory.getLogger("errorLog");
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		new Thread(){
			@Override
			public void run() {
				logger.debug("当前线程名:"+Thread.currentThread().getName());
				logger.debug("进入定时器子线程！");
				Properties properties = new Properties();
		        properties.put("bootstrap.servers", "20.2.15.25:9092,20.2.15.26:9092,20.2.15.29:9092");
		        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				Producer<String,String> producer = new KafkaProducer<String,String>(properties);
				TdIllegaleventMapper tdIllegaleventMapper = SpringContextHolder.getBean(TdIllegaleventMapper.class);
				TdTrafficeventMapper tdTrafficeventMapper = SpringContextHolder.getBean(TdTrafficeventMapper.class);
				TlMasterMapper tlMasterMapper = SpringContextHolder.getBean(TlMasterMapper.class);
				TlDeviceMapper tlDeviceMapper = SpringContextHolder.getBean(TlDeviceMapper.class);
				TdTrafficeventpicMapper tdTrafficeventpicMapper = SpringContextHolder.getBean(TdTrafficeventpicMapper.class);
				TdTrafficeventvideoMapper tdTrafficeventvideoMapper = SpringContextHolder.getBean(TdTrafficeventvideoMapper.class);
				//一般违法
				logger.debug("!!!!!");
				Timer t1 = new Timer();
				t1.schedule(new TimerTask() {
					public void run() {
						logger.error("违法扫描开始");
						GeneralIllegal.d(tdIllegaleventMapper,producer);
						logger.error("交通事件扫描开始");
						TrafficAccident.d(tdTrafficeventMapper,tdTrafficeventpicMapper,tdTrafficeventvideoMapper,producer);
						logger.error("日志扫描开始");
						DeviceLog.d(tlDeviceMapper,producer);
						
					}
				}, 1000, 3600*1000); // start第一次删除时间，delay重复删除间隔时间
				Timer t2 = new Timer();
				t2.schedule(new TimerTask() {
					
					@Override
					public void run() {
						logger.error("设备主机日志扫描开始");
						OperaLog.d(tlMasterMapper,producer);
						
					}
				}, 1000, 24*3600*1000);
				//交通事件
				/*Timer t2 = new Timer();
				t2.schedule(new TimerTask() {
					public void run() {
						TrafficAccident.d(tdTrafficeventMapper,tdTrafficeventpicMapper,tdTrafficeventvideoMapper,producer);
					}
				}, 1000, 600*1000); // start第一次删除时间，delay重复删除间隔时间
				
				//OperaLog
				Timer t3 = new Timer();
				t3.schedule(new TimerTask() {
					public void run() {
						OperaLog.d(tlMasterMapper,producer);
					}
				}, 1000, 1000); // start第一次删除时间，delay重复删除间隔时间
				
				//DeviceLog
				Timer t4 = new Timer();
				t4.schedule(new TimerTask() {
					public void run() {
						DeviceLog.d(tlDeviceMapper,producer);
					}
				}, 1000, 1000); // start第一次删除时间，delay重复删除间隔时间*/
			}
		}.start();
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
