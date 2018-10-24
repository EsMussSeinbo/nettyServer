package com.hisense.nettyServer.support.job;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisense.nettyServer.dataformat.DataFormat;
import com.hisense.nettyServer.model.dao.GeneralIllegal.TdIllegaleventMapper;
import com.hisense.nettyServer.model.entity.TdIllegalevent;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FileScanUtil;
import com.hisense.nettyServer.support.util.IPUtils;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;
import com.hisense.nettyServer.support.util.StringUtil;
import com.hisense.nettyServer.support.util.XML2String;

/**
 * 一般违法
 * 
 * @author qiush
 *
 */
public class GeneralIllegal {
	private final static Enums ENUMS = SingletonObject.getEnums();
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	private static DataFormat dataFormat= SingletonObject.getDataFormat();
	private static ExecutorService threadPool;
	private static PropertiesUtil propertiesUtil;

	public static void d(TdIllegaleventMapper tdIllegaleventMapper, Producer<String, String> producer) {
		logger.debug("进入一般违法解析类！");
		// 获取线程池
		threadPool = SingletonObject.getThreadPool();
		// 通过本机IP地址获取到对应的设备ID,用于扫描文件夹
		// String localIP = IPUtils.getLocalIp();
		String IP = propertiesUtil.getName("SERVER_IP");
		// 通过主机IP获取对应文件夹
		//String ipPath = (String) ENUMS.ipPath.get(IP);
		String ipPath=propertiesUtil.getName("FTP_PATH");
		// 获取到对应设备ID
		List<String> ID = (List<String>) ENUMS.ServiceIpTodevciceNum.get(IP);
		for (int i = 0; i < ID.size(); i++) {
			String adress = File.separator + "cifspool" + File.separator + ipPath + File.separator + "FTPROOT"
					+ File.separator + "VIOLATION" + File.separator + ID.get(i);
			File file = new File(adress);
			if (!file.exists() && !file.isDirectory()) {
				logger.error(IP + adress + "该文件目录不存在,跳过此次循环！");
				continue;
			}
			// 每个设备目录创建一个线程处理
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try{

						logger.debug("进入run方法！");
						// TODO Auto-generated method stub
						List<String> scanFilesWithRecursion = new FileScanUtil().scanFilesWithRecursion(adress);
	
						logger.debug("数据长度："+scanFilesWithRecursion.size());
						if(scanFilesWithRecursion.size()<=0)
							return;
						//for (Object object : scanFilesWithRecursion) {
						for (int i=0;i< scanFilesWithRecursion.size();i++) {
							String string=scanFilesWithRecursion.get(i);
//							if(new File(object.toString()).exists())
//								continue;
							logger.debug("文件路径为:"+string);
							String s = XML2String.XMLToString(string);
							logger.debug("解析后字符串为:" + s);
							// DataFormatResult<Boolean, List<String>, String,
							// String>
							// dataFormatResult=dataFormat1.dataFormatList(s.replaceAll("\n",
							// ""));
							DataFormatResult<Boolean, String, String, String> dataFormatResult = dataFormat
									.dataFormatString(s.replaceAll("\n", ""));
							if (dataFormatResult.getResult()) {
								// 数据标准化成功
								// 写入kafka
								// List<String> message =
								// dataFormatResult.getData();
								// logger.debug("遍历标准化数据ls:"+message.get(0));
								// for (String string : message) {
								// logger.debug("遍历标准化数据:"+string);
								// }
								logger.error("一般违法数据标准化成功,标准化数据为:" + dataFormatResult.getData());
								String messge = dataFormatResult.getData();
								producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), messge));
								// 写入数据库
								TdIllegalevent tdIllegalevent = new TdIllegalevent();
								loadObj(tdIllegalevent, messge);
								tdIllegaleventMapper.insert(tdIllegalevent);
								File file = new File(string);
								boolean delete = file.delete();
								logger.error("文件:"+string+"删除"+delete);
							} else {
								// 数据标准化失败
								logger.info("一般违法数据标准化失败！");
							}
						}
					
					}catch(Exception e){
						logger.error("run方法内部错误"+e.getMessage(),e);
					}
				}

				private void loadObj(TdIllegalevent tdIllegalevent, String message) {
					try {
						logger.debug("违法传入数据："+message);
						logger.debug("违法传入数据个数："+message.split(",",-1).length);
						tdIllegalevent.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						tdIllegalevent.setDeviceid(message.split(",",-1)[6]);
						tdIllegalevent.setDevicepos(message.split(",",-1)[8]);
						tdIllegalevent.setLaneno(StringUtil.parseShort(message.split(",",-1)[18]));
						tdIllegalevent.setEventtype(message.split(",",-1)[4]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[40])))
						tdIllegalevent.setEventtime(StringUtil.parseDate(message.split(",",-1)[40]));
					//	tdIllegalevent.setEventtime(new Date());
						tdIllegalevent.setPlateno(message.split(",",-1)[11]);
						tdIllegalevent.setPlatetype(message.split(",",-1)[10]);
						tdIllegalevent.setPlatecolor(message.split(",",-1)[12]);
						tdIllegalevent.setVehicletype(message.split(",",-1)[13]);
						tdIllegalevent.setVehiclelogo(message.split(",",-1)[15]);
						tdIllegalevent.setConfidence(
								StringUtil.parseBigDecimal(message.split(",",-1)[19], 2, BigDecimal.ROUND_HALF_DOWN));
						tdIllegalevent.setDirection(message.split(",",-1)[17]);
						tdIllegalevent.setSpeed(StringUtil.parseShort(message.split(",",-1)[20]));
						if(null!=StringUtil.parseDate((message.split(",",-1)[23])))
						tdIllegalevent.setRlontime(StringUtil.parseDate((message.split(",",-1)[23])));
//						tdIllegalevent.setRlontime(new Date());
						if(null!=StringUtil.parseDate((message.split(",",-1)[24])))
						tdIllegalevent.setRlofftime(StringUtil.parseDate((message.split(",",-1)[24])));
//						tdIllegalevent.setRlofftime(new Date());
						tdIllegalevent.setPiccount(StringUtil.parseShort(message.split(",",-1)[25]));
						tdIllegalevent.setIsvalid("0");
						// tdIllegalevent.setInserttime(new Date());入库时间缺省
						tdIllegalevent.setIllegalid(message.split(",",-1)[5]);
						tdIllegalevent.setPolicedevid(message.split(",",-1)[7]);
						tdIllegalevent.setVehiclecolor(message.split(",",-1)[16]);
						tdIllegalevent.setVehicleclass(message.split(",",-1)[14]);
						tdIllegalevent.setLimitspeed(StringUtil.parseShort(message.split(",",-1)[21]));
						tdIllegalevent.setMaxlimitspeed(StringUtil.parseShort(message.split(",",-1)[22]));
						tdIllegalevent.setVehnewenerg(message.split(",",-1)[42]);
						tdIllegalevent.setBigplateno(message.split(",",-1)[43]);
						tdIllegalevent.setIsAudit("0");
						tdIllegalevent.setDatasource(message.split(",",-1)[2]);
						tdIllegalevent.setClassification(message.split(",",-1)[3]);
						tdIllegalevent.setPicurl1(message.split(",",-1)[26]);
						tdIllegalevent.setPicname1(message.split(",",-1)[27]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[28])))
						tdIllegalevent.setPictime1(StringUtil.parseDate((message.split(",",-1)[28])));
//						tdIllegalevent.setPictime1(new Date());
						tdIllegalevent.setPicurl2(message.split(",",-1)[29]);
						tdIllegalevent.setPicname2(message.split(",",-1)[30]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[31])))
						tdIllegalevent.setPictime2(StringUtil.parseDate(message.split(",",-1)[31]));
//						tdIllegalevent.setPictime2(new Date());
						tdIllegalevent.setPicurl3(message.split(",",-1)[32]);
						tdIllegalevent.setPicname3(message.split(",",-1)[33]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[34])))
						tdIllegalevent.setPictime3(StringUtil.parseDate((message.split(",",-1)[34])));
//						tdIllegalevent.setPictime3(new Date());
						tdIllegalevent.setPicurl4(message.split(",",-1)[35]);
						tdIllegalevent.setPicname4(message.split(",",-1)[36]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[37])))
						tdIllegalevent.setPictime4(StringUtil.parseDate((message.split(",",-1)[37])));
//						tdIllegalevent.setPictime4(new Date());
						tdIllegalevent.setVideourl(message.split(",",-1)[38]);
						tdIllegalevent.setAudiourl(message.split(",",-1)[39]);
					} catch (Exception e) {
						logger.error("一般违法对象解析异常！", e);
					}
				}
			});
		}
	}

}
