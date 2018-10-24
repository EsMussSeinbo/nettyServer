package com.hisense.nettyServer.support.job;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisense.nettyServer.dataformat.DataFormat;
import com.hisense.nettyServer.model.dao.DeviceLog.TlDeviceMapper;
import com.hisense.nettyServer.model.entity.TdIllegalevent;
import com.hisense.nettyServer.model.entity.TlDevice;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FileScanUtil;
import com.hisense.nettyServer.support.util.IPUtils;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;
import com.hisense.nettyServer.support.util.StringUtil;
import com.hisense.nettyServer.support.util.XML2String;

/**
 * 设备日志
 * @author qiush
 *
 */
public class DeviceLog {
	private final static Enums ENUMS = SingletonObject.getEnums();
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	private static DataFormat dataFormat= SingletonObject.getDataFormat();
	private static ExecutorService threadPool;
	private static PropertiesUtil propertiesUtil;

	public static void d(TlDeviceMapper tlDeviceMapper, Producer<String, String> producer) {
		logger.debug("进入设备日志解析类！");
		// 获取线程池
		threadPool = SingletonObject.getThreadPool();
		// 通过本机IP地址获取到对应的设备ID,用于扫描文件夹
		// String localIP = IPUtils.getLocalIp();
		String IP = propertiesUtil.getName("SERVER_IP");
		// 通过主机IP获取对应文件夹
	//	String ipPath = (String) ENUMS.ipPath.get(IP);
		String ipPath=propertiesUtil.getName("FTP_PATH");
		// 获取到对应设备ID
		List<String> ID = (List<String>) ENUMS.ServiceIpTodevciceNum.get(IP);
		for (int i = 0; i < ID.size(); i++) {
			String adress = File.separator + "cifspool" + File.separator + ipPath + File.separator + "FTPROOT"
					+ File.separator + "LOG" + File.separator + ID.get(i);
			File file = new File(adress);
			if (!file.exists() && !file.isDirectory()) {
				logger.error(IP + adress + "该文件目录不存在,跳过此次循环！");
				continue;
			}
			logger.error(IP + adress + "日志该文件目录存在");

			// 每个设备目录创建一个线程处理
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					try{

						// TODO Auto-generated method stub
						List<String> scanFilesWithRecursion = new FileScanUtil().scanFilesWithRecursion(adress);
						if(scanFilesWithRecursion.size()<=0)
							return;
						for (String string : scanFilesWithRecursion) {
							
							String s = XML2String.XMLToString(string);
							logger.debug("日志原数据:"+s);
							DataFormatResult<Boolean, String, String, String> dataFormatResult = dataFormat
									.dataFormatString(s.replaceAll("\n", ""));
							if (dataFormatResult.getResult()) {
								// 数据标准化成功
								
								// 写入kafka
								String messge = dataFormatResult.getData();
								logger.error("日志数据标准化成功："+messge);
								producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), messge));
								// 写入数据库
								TlDevice tlDevice = new TlDevice();
								loadObj(tlDevice, messge);
								tlDeviceMapper.insert(tlDevice);
								File file = new File(string);
								boolean delete = file.delete();
								logger.error("文件:"+string+"删除"+delete);
							} else {
								// 数据标准化失败
								logger.error("设备日志数据标准化失败！");
							}
						}
					
					}catch(Exception e){
						logger.error("run方法内部错误"+e.getMessage(),e);
					}
				}

				// 给实体类设置值
				private void loadObj(TlDevice tlDevice, String message) {

					try {
						tlDevice.setFileName(message.split(",",-1)[0]);
						tlDevice.setDeviceid(message.split(",",-1)[1]);
						tlDevice.setEpdeviceid(message.split(",",-1)[2]);
						if(null!=StringUtil.parseDate((message.split(",",-1)[3])))
						tlDevice.setRecordTime(StringUtil.parseDate(message.split(",",-1)[3]));
						tlDevice.setIpAddress(message.split(",",-1)[4]);
						tlDevice.setCpuTemp(
								StringUtil.parseBigDecimal(message.split(",",-1)[5], 2, BigDecimal.ROUND_HALF_DOWN));
						tlDevice.setCpuFanSpeed(
								StringUtil.parseBigDecimal(message.split(",",-1)[6], 2, BigDecimal.ROUND_HALF_DOWN));
						tlDevice.setFreeMemory(message.split(",",-1)[7]);
						tlDevice.setThreadCount(StringUtil.parseLong(message.split(",",-1)[8]));
						tlDevice.setDiskSpace(message.split(",",-1)[9]);
						tlDevice.setNonuploadnum(StringUtil.parseShort(message.split(",",-1)[10]));
						tlDevice.setCamid(message.split(",",-1)[11]);
						tlDevice.setCameraState(message.split(",",-1)[12]);
						tlDevice.setCameraStateDes(message.split(",",-1)[13]);
						tlDevice.setVideoid(message.split(",",-1)[14]);
						tlDevice.setVideoState(message.split(",",-1)[15]);
						tlDevice.setVideoStateDes(message.split(",",-1)[16]);
						tlDevice.setTriggerid(message.split(",",-1)[17]);
						tlDevice.setTriggerState(message.split(",",-1)[18]);
						tlDevice.setTriggerStateDes(message.split(",",-1)[19]);
						tlDevice.setLightid(message.split(",",-1)[20]);
						tlDevice.setLightState(message.split(",",-1)[21]);
						tlDevice.setLightStateDes(message.split(",",-1)[22]);
						tlDevice.setIsNormal(message.split(",",-1)[23]);
						tlDevice.setFileSize(StringUtil.parseLong((message.split(",",-1)[24])));
						if(null!=StringUtil.parseDate((message.split(",",-1)[25])))
						tlDevice.setFileCreatetime(StringUtil.parseDate((message.split(",",-1)[25])));
						if(null!=StringUtil.parseDate((message.split(",",-1)[26])))
						tlDevice.setFileAnalyseTime(StringUtil.parseDate((message.split(",",-1)[26])));
						tlDevice.setServerip(message.split(",",-1)[27]);
						tlDevice.setNote(message.split(",",-1)[28]);
					} catch (Exception e) {
						logger.error("设备日志对象解析异常！", e);
					}

				}

			});
		}
	}
}
