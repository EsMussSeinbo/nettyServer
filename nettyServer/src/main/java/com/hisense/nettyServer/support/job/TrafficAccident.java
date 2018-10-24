package com.hisense.nettyServer.support.job;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hisense.nettyServer.dataformat.DataFormat;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventMapper;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventpicMapper;
import com.hisense.nettyServer.model.dao.TrafficAccident.TdTrafficeventvideoMapper;
import com.hisense.nettyServer.model.entity.TdTrafficevent;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FileScanUtil;
import com.hisense.nettyServer.support.util.IPUtils;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;
import com.hisense.nettyServer.support.util.StringUtil;
import com.hisense.nettyServer.support.util.XML2String;

/**
 * 交通事件
 * 
 * @author qiush
 *
 */
public class TrafficAccident {
	private final static Enums ENUMS = SingletonObject.getEnums();
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	private static DataFormat dataFormat= SingletonObject.getDataFormat();
	private static ExecutorService threadPool;
	private static PropertiesUtil propertiesUtil;

	public static void d(TdTrafficeventMapper tdTrafficeventMapper, TdTrafficeventpicMapper tdTrafficeventpicMapper,
			TdTrafficeventvideoMapper tdTrafficeventvideoMapper, Producer<String, String> producer) {
		logger.debug("进入交通事件解析类！");
		// 获取线程池
		threadPool = SingletonObject.getThreadPool();
		// 通过本机IP地址获取到对应的设备ID,用于扫描文件夹
		//String localIP = IPUtils.getLocalIp();
		String IP = propertiesUtil.getName("SERVER_IP");
		// 通过主机IP获取对应文件夹
		//String ipPath = (String) ENUMS.ipPath.get(IP);
		String ipPath=propertiesUtil.getName("FTP_PATH");
		// 获取到对应设备ID
		List<String> ID = (List<String>) ENUMS.ServiceIpTodevciceNum.get(IP);
		for (int i = 0; i < ID.size(); i++) {
			logger.debug("asdssssss"+ i );
			String adress = File.separator + "cifspool" + File.separator + ipPath + File.separator + "FTPROOT"
					+ File.separator + "ALERT" + File.separator + ID.get(i);
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

						// TODO Auto-generated method stub
						List<String> scanFilesWithRecursion = new FileScanUtil().scanFilesWithRecursion(adress);
						if(scanFilesWithRecursion.size()<=0)
							return;
						for (String string : scanFilesWithRecursion) {
//							if(new File(object.toString()).exists())
//								continue;
							logger.debug("文件路径为:"+string);
							String s = XML2String.XMLToString(string);
							logger.debug("交通事件原始数据为:" + s);
							DataFormatResult<Boolean, List<String>, String, String> dataFormatResult = dataFormat
									.dataFormatList(s.replaceAll("\n", ""));
							if (dataFormatResult.getResult()) {
								// 数据标准化成功

								List<String> messges = dataFormatResult.getData();
								for (String messge : messges) {
									// 写入kafka
									logger.error("交通事件数据标准化成功,标准化数据为:" + dataFormatResult.getData());
									producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), messge));
									// 写入数据库
									TdTrafficevent tdTrafficevent = new TdTrafficevent();
									loadObj(tdTrafficevent, messge);
									tdTrafficeventMapper.insert(tdTrafficevent);
								}
								File file = new File(string);
								boolean delete = file.delete();
								logger.error("文件:"+string+"删除"+delete);

							} else {
								logger.error("交通事件数据标准化异常！");
							}
						}
					
					}catch(Exception e){
						logger.error("run方法内部错误"+e.getMessage(),e);
					}
				}

				private void loadObj(TdTrafficevent tdTrafficevent, String messge) {

					try {
						tdTrafficevent.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						tdTrafficevent.setDatasource(messge.split(",",-1)[2]);
						tdTrafficevent.setDeviceid(messge.split(",",-1)[3]);
						tdTrafficevent.setDevicepos(messge.split(",",-1)[4]);
						tdTrafficevent.setLaneno(StringUtil.parseShort(messge.split(",",-1)[8]));
						tdTrafficevent.setEventtype(messge.split(",",-1)[9]);
						tdTrafficevent.setEventtime(StringUtil.parseDate1(messge.split(",",-1)[5]));
						if(null!=StringUtil.parseDate((messge.split(",",-1)[6])))
						tdTrafficevent.setStarttime(StringUtil.parseDate((messge.split(",",-1)[6])));
						if(null!=StringUtil.parseDate((messge.split(",",-1)[7])))
						tdTrafficevent.setEndtime(StringUtil.parseDate((messge.split(",",-1)[7])));
						tdTrafficevent.setVideostarttime(StringUtil.parseDate1(messge.split(",",-1)[22]));
						tdTrafficevent.setVideoendtime(StringUtil.parseDate1(messge.split(",",-1)[23]));
						tdTrafficevent.setDirection(messge.split(",",-1)[10]);
						tdTrafficevent.setPiccount(StringUtil.parseShort(messge.split(",",-1)[13]));
						tdTrafficevent.setPicx("");
						tdTrafficevent.setPicy("");
						tdTrafficevent.setIsvalid("0");
						tdTrafficevent.setIsabnormal("0");
						// tdTrafficevent.setInserttime(inserttime);入库时间缺省
					} catch (Exception e) {
						logger.error("交通事件对象解析异常！", e);
					}
				}
			});
		}
	}

}
