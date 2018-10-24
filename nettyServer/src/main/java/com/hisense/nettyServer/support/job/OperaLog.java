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
import com.hisense.nettyServer.model.dao.OperaLog.TlMasterMapper;
import com.hisense.nettyServer.model.entity.TlMaster;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FileScanUtil;
import com.hisense.nettyServer.support.util.IPUtils;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;
import com.hisense.nettyServer.support.util.StringUtil;
import com.hisense.nettyServer.support.util.XML2String;

/**
 * 操作日志
 * 
 * @author qiush
 *
 */
public class OperaLog {
	private final static Enums ENUMS = SingletonObject.getEnums();
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	private static DataFormat dataFormat= SingletonObject.getDataFormat();
	private static ExecutorService threadPool;
	private static PropertiesUtil propertiesUtil;

	public static void d(TlMasterMapper tlMasterMapper, Producer<String, String> producer) {
		logger.debug("进入操作日志解析类！");
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
					+ File.separator + "OPLOG" + File.separator + ID.get(i);
			File file = new File(adress);
			if (!file.exists() && !file.isDirectory()) {
				logger.error(IP + adress + "该文件目录不存在,跳过此次循环！");
				continue;
			}
			logger.error(IP + adress + "设备主机日志该文件目录存在");
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
							logger.debug("设备主机日志原数据:"+s);
							DataFormatResult<Boolean, List<String>, String, String> dataFormatResult = dataFormat
									.dataFormatList(s.replaceAll("\n", ""));
							if (dataFormatResult.getResult()) {
								// 数据标准化成功

								List<String> messges = dataFormatResult.getData();
								
								for (String messge : messges) {
									logger.error("设备主机日志数据标准化成功："+messge);
									// 写入kafka
									//producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), messge));
									// 写入数据库
									TlMaster tlMaster = new TlMaster();
									loadObj(tlMaster, messge);
									tlMasterMapper.insert(tlMaster);
								}
								File file = new File(string);
								boolean delete = file.delete();
								logger.error("文件:"+string+"删除"+delete);

							} else {
								// 数据标准化失败
								logger.error("操作日志数据标准化失败!");
							}
						}
					
					}catch(Exception e){
						logger.error("run方法内部错误"+e.getMessage(),e);
					}
				}

				private void loadObj(TlMaster tlMaster, String message) {
					try {
						tlMaster.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						tlMaster.setDeviceid(message.split(",",-1)[3]);
						if(null!=StringUtil.parseDate1((message.split(",",-1)[4])))
						tlMaster.setCreationtime(StringUtil.parseDate1(message.split(",",-1)[4]));
						if(null!=StringUtil.parseDate((message.split(",",-1)[6])))
						tlMaster.setEventtime(StringUtil.parseDate((message.split(",",-1)[6])));
						tlMaster.setEventdesc(message.split(",",-1)[7]);
						tlMaster.setNote("");
						// tlMaster.setInserttime(inserttime);入库时间缺省
					} catch (Exception e) {
						logger.error("操作日志对象解析异常！", e);
					}

				}
			});
		}

	}
}
