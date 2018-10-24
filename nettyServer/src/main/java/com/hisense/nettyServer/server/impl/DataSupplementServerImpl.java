package com.hisense.nettyServer.server.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.hisense.nettyServer.dataformat.DataFormatSupplement;
import com.hisense.nettyServer.server.DataSupplementServer;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FtpDownLoadUtil;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;
import com.hisense.nettyServer.support.util.XML2String;
import redis.clients.jedis.JedisCluster;

/**
 * 数据补录服务
 * 
 * @author qiush
 *
 */
@Service
public class DataSupplementServerImpl implements DataSupplementServer {

	private static Logger logger = LoggerFactory.getLogger("errorLog");

	private static DataFormatSupplement dataFormat = SingletonObject.getDataFormatSupplement();

	private final static Enums ENUMS = SingletonObject.getEnums();

	@SuppressWarnings("static-access")
	@Override
	public void dataSupplementServer(String time1, String time2, String deviceId, String dataType,JedisCluster jedisCluster) {
		try {
			// TODO Auto-generated method stub
			logger.debug("进入数据补录server");
			//redis锁住状态
			jedisCluster.set("isServerStatus","1");
			String ip = (String) ENUMS.devciceNumToDeviceIp.get(deviceId);
			String username = null;
			String password = null;
			int port = 0;
			if (getCompany(deviceId)) {
				// 川大设备
				username = PropertiesUtil.getName("CDZSFtpUser");
				password = PropertiesUtil.getName("CDZSFtpPwd");
				port = Integer.parseInt(PropertiesUtil.getName("CDZSFtpPort"));
			} else {
				// VRoad设备masterFtpUsr
				username = PropertiesUtil.getName("masterFtpUsr");
				password = PropertiesUtil.getName("masterFtpPwd");
				port = Integer.parseInt(PropertiesUtil.getName("masterFtpPort"));
			}
			Properties properties = new Properties();
			properties.put("bootstrap.servers", "20.2.15.25:9092,20.2.15.26:9092,20.2.15.29:9092");
			properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			Producer<String, String> producer = new KafkaProducer<String, String>(properties);
			// dataType=1.过车记录 2.交通流量 3.一般违法 4.交通事件 5.过车图片
			List<String> files = getFiles(time1, time2, deviceId, dataType, ip, port, username, password);
			logger.error("获取到的文件个数："+files.size());
			if (files.size() <= 0) {
				producer.close();
				return;
			}
			for (String file : files) {
				if (new File(file).exists())
					continue;
				// FTP下载地址暂定/FTPDTemp
				String[] remotePathAndFilename = getRemotePathAndFilename(file);
				if(!new File("/FTPDTemp").exists())
					new File("/FTPDTemp").mkdir();
				logger.error(ip+port+username+password+remotePathAndFilename[0]+"----887___"+remotePathAndFilename[1]);
			//	FtpDownLoadUtil.downloadFtpFile("20.2.35.3", "SD123456", "SD159753", 21, "/WATCH/20181023/14/", "F:/FTPDTemp", "Z_3014-722074_1_20181023140037365_22_川AK2688.XML");

				FtpDownLoadUtil.downloadFtpFile(ip, username, password, port, remotePathAndFilename[0],
						"/FTPDTemp", remotePathAndFilename[1]);
				if (dataType.equals("4") || dataType.equals("1")) {
					String s = XML2String.XMLToString("/FTPDTemp" + "/" + remotePathAndFilename[1]);
					DataFormatResult<Boolean, String, String, String> dataFormatResult = dataFormat
							.dataFormatString(s.replaceAll("\n", ""));
					if (dataFormatResult.getResult()) {
						String message = dataFormatResult.getData();
						producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), message));
						File file1 = new File("/FTPDTemp" + "/" + remotePathAndFilename[1]);
						file1.delete();
					} else {
						logger.info("数据类型:" + dataType + ",标准化失败！");
					}
				} else if (dataType.equals("5") || dataType.equals("2")) {
					String s = XML2String.XMLToString("/FTPDTemp" + "/" + remotePathAndFilename[1]);
					DataFormatResult<Boolean, List<String>, String, String> dataFormatResult = dataFormat
							.dataFormatList(s.replaceAll("\n", ""));
					if (dataFormatResult.getResult()) {
						List<String> messages = dataFormatResult.getData();
						for (String message : messages) {
							producer.send(new ProducerRecord<String, String>(dataFormatResult.getTopic(), message));
							File file2 = new File("/FTPDTemp" + "/" + remotePathAndFilename[1]);
							file2.delete();
						}
					} else {
						logger.info("数据类型:" + dataType + ",标准化失败！");
					}
				} else {
					// 过车图片
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream("/FTPDTemp" + "fileName"));
					String serverIp = (String) ENUMS.devciceNumToServiceIp.get(deviceId);
					String serverFileName = (String) ENUMS.ipPath.get(serverIp);
					// /cifspool/SF_ITS_YQ327/FTPROOT/VEH_PHOTO/3014-290040/20180611/04
					// 20181018031725762
					String hour = getHour(remotePathAndFilename[1], getCompany(deviceId), dataType);
					String adress = "/cifspool/" + serverFileName + "/" + "FTPROOT/VEH_PHOTO/" + deviceId + "/"
							+ time1.subSequence(0, 8) + "/" + hour;
					if (!new File(adress).exists())
						new File(adress).mkdirs();
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(adress + "/" + remotePathAndFilename[1]));
					int b;
					while ((b = bis.read()) != -1) {
						bos.write(b);
					}
					bis.close();
					bos.close();
					File file3 = new File("/FTPDTemp" + "fileName");
					file3.delete();
				}
				producer.close();
			}
			//执行完成服务,解除锁状态
			jedisCluster.set("isServerStatus","0");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jedisCluster.set("isServerStatus","0");
		}
	}

	// 通过传入参数获取文件集合
	private List<String> getFiles(String time1, String time2, String deviceId, String dataType, String ip, int port,
			String userName, String password) {
		List<String> fileList = new ArrayList<String>();
		String adress = null;
		boolean company = true;

		// 获取设备厂商
		company = getCompany(deviceId);

		// 获取前半段地址,/mnt/VideoData/REALTIME/
		adress = getHalfPath(company, dataType);

		int day1 = Integer.parseInt(time1.substring(6, 8));
		int day2 = Integer.parseInt(time2.substring(6, 8));
		int hour1 = Integer.parseInt(time1.substring(8, 10));
		int hour2 = Integer.parseInt(time2.substring(8, 10));
		String yearAndMonth = time1.substring(0, 6);

		if (time1.substring(0, 10).equals(time2.substring(0, 10))) {
			// day与hour相同,在同一个文件夹下
			if (day1 <= 9) {
				adress = adress + yearAndMonth + "0" + day1 + "/";
			} else {
				adress = adress + yearAndMonth + day1 + "/";
			}
			if (hour1 <= 9) {
				adress = adress + "0" + hour1 + "/";
			} else {
				adress = adress + hour1 + "/";
			}

			if (!dataType.equals("5")) {
				// 非图片数据
				List<String> files = getFtpFiles(ip, port, userName, password, adress, "XML");
				for (String string : files) {
					String[] remotePathAndFilename = getRemotePathAndFilename(string);
					if (compareTime(remotePathAndFilename[1], company, dataType, time1, time2))
						fileList.add(string);
				}
			} else {
				// 图片数据
				List<String> files = getFtpFiles(ip, port, userName, password, adress, "JPG");
				for (String string : files) {
					String[] remotePathAndFilename = getRemotePathAndFilename(string);
					if (compareTime(remotePathAndFilename[1], company, dataType, time1, time2))
						fileList.add(string);
				}
			}
			return fileList;
		} else {
			// day与hour不相同,不在同一个文件夹下,需要考虑开头结尾过滤处理
			for (int i = day1; i <= day2; i++) {
				// day遍历day
				for (int j = hour1; j <= hour2; j++) {

					if (i <= 9) {
						adress = adress + yearAndMonth + "0" + i + "/";
					} else {
						adress = adress + yearAndMonth + i + "/";
					}
					if (j <= 9) {
						adress = adress + "0" + j + "/";
					} else {
						adress = adress + j + "/";
					}

					if (i == day1 & j == hour1) {
						// 开头段
						if (!dataType.equals("5")) {
							// 非图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "XML");
							for (String string : files) {
								String[] remotePathAndFilename = getRemotePathAndFilename(string);
								int compareTime = compareTime(remotePathAndFilename[1], company, dataType, time1);
								if (compareTime >= 0)
									fileList.add(string);
							}
						} else {
							// 图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "JPG");
							for (String string : files) {
								String[] remotePathAndFilename = getRemotePathAndFilename(string);
								int compareTime = compareTime(remotePathAndFilename[1], company, dataType, time1);
								if (compareTime >= 0)
									fileList.add(string);
							}
						}
					} else if (i == day2 & j == hour2) {
						// 结尾段
						if (!dataType.equals("5")) {
							// 非图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "XML");
							for (String string : files) {
								String[] remotePathAndFilename = getRemotePathAndFilename(string);
								int compareTime = compareTime(remotePathAndFilename[1], company, dataType, time2);
								if (compareTime <= 0)
									fileList.add(string);
							}
						} else {
							// 图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "JPG");
							for (String string : files) {
								String[] remotePathAndFilename = getRemotePathAndFilename(string);
								int compareTime = compareTime(remotePathAndFilename[1], company, dataType, time2);
								if (compareTime <= 0)
									fileList.add(string);
							}
						}
					} else {
						// 中间段
						if (!dataType.equals("5")) {
							// 非图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "XML");
							fileList.addAll(files);
						} else {
							// 图片数据
							List<String> files = getFtpFiles(ip, port, userName, password, adress, "JPG");
							fileList.addAll(files);
						}
					}

					// 重置地址
					adress = getHalfPath(company, dataType);
				}
			}
			return fileList;
		}

	}

	// 获取厂商
	private boolean getCompany(String deviceId) {
		if ("3014-24".equals(deviceId.substring(0, 7))) {
			return true;
		} else {
			return false;
		}
	}

	// 把地址拆分成两半
	private String[] getRemotePathAndFilename(String adress) {
		String fileName = adress.substring(adress.lastIndexOf("/") + 1);
		String remotePath = adress.replaceAll(fileName, "");
		String[] ss = { null, null };
		ss[0] = remotePath;
		ss[1] = fileName;
		return ss;
	}

	// 获取前半段地址,例如:/mnt/VideoData/REALTIME/
	private String getHalfPath(boolean company, String dataType) {
		if (company) {
			// 川大设备
			if (dataType.equals("1")) {
				// 1.过车记录
				return "/mnt/VideoData/REALTIME/";
			} else if (dataType.equals("2")) {
				// 2.交通流量
				return "/mnt/VideoData/MEASURE/";
			} else if (dataType.equals("3")) {
				// 3.一般违法
				// 川大设备没有一般违法
				return null;
			} else if (dataType.equals("4")) {
				// 4.交通事件
				return "/mnt/VideoData/ALERT/";
			} else {
				// 5.过车图片
				return "/mnt/VideoData/VEH_PHOTO/";
			}
		} else {
			// VRoad设备
			if (dataType.equals("1")) {
				// 1.过车记录
				return "/WATCH/";
			} else if (dataType.equals("2")) {
				// 2.交通流量
				return "/FLOW/";
			} else if (dataType.equals("3")) {
				// 3.一般违法
				return "/PECCANCY/";
			} else if (dataType.equals("4")) {
				// 4.交通事件
				return "/WATCH/";
			} else {
				// 5.过车图片
				return "/WATCH/";
			}
		}
	}

	// 开头与结尾hour过滤,字符串比较分、秒、毫秒大小,返回结果大于0则是传入时间之后,该方法用于day1不等于day2
	private int compareTime(String fileName, boolean company, String dataType, String time) {
		if (company) {
			// 川大设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-243063_5_20181018031725762_川_1A328.XML
				String time1 = fileName.substring(16, 33);
				return time1.compareTo(time);
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-243063_201810170700.XML
				String time1 = fileName.substring(15, 27);
				return time1.compareTo(time);
			} else if (dataType.equals("3")) {
				// 3.一般违法
				// 川大设备没有一般违法
				return 0;
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3014-243063_20181017114347963.XML
				String time1 = fileName.substring(15, 32);
				return time1.compareTo(time);
			} else {
				// 5.过车图片,Z_3014-243063_5_20181016012650447_21_川Q39016.JPG
				String time1 = fileName.substring(16, 33);
				return time1.compareTo(time);
			}
		} else {
			// VRoad设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-721050_1_20180927230035040_22_川CLL379.XML
				String time1 = fileName.substring(16, 33);
				return time1.compareTo(time);
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-721050_20171001010400.XML
				String time1 = fileName.substring(15, 29);
				return time1.compareTo(time);
			} else if (dataType.equals("3")) {
				// 3.一般违法,I_3014-721050_2_20180126232206119.XML
				String time1 = fileName.substring(16, 33);
				return time1.compareTo(time);
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3050-721062_20140909095910815.XML
				String time1 = fileName.substring(15, 32);
				return time1.compareTo(time);
			} else {
				// 5.过车图片,Z_3014-721050_1_20180927230035040_21_川CLL379.JPG
				String time1 = fileName.substring(16, 33);
				return time1.compareTo(time);
			}
		}
	}

	// 开头与结尾hour过滤,字符串比较分、秒、毫秒大小,返回结果大于0则是传入时间之后,该方法用于day1不等于day2
	private boolean compareTime(String fileName, boolean company, String dataType, String time1, String time2) {
		if (company) {
			// 川大设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-243063_5_20181018031725762_川_1A328.XML
				String time = fileName.substring(16, 33);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-243063_201810170700.XML
				String time = fileName.substring(15, 27);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else if (dataType.equals("3")) {
				// 3.一般违法
				// 川大设备没有一般违法
				return false;
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3014-243063_20181017114347963.XML
				String time = fileName.substring(15, 32);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else {
				// 5.过车图片,Z_3014-243063_5_20181016012650447_21_川Q39016.JPG
				String time = fileName.substring(16, 33);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			}
		} else {
			// VRoad设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-721050_1_20180927230035040_22_川CLL379.XML
				String time = fileName.substring(16, 33);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-721050_20171001010400.XML
				String time = fileName.substring(15, 29);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else if (dataType.equals("3")) {
				// 3.一般违法,I_3014-721050_2_20180126232206119.XML
				String time = fileName.substring(16, 33);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3050-721062_20140909095910815.XML
				String time = fileName.substring(15, 32);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			} else {
				// 5.过车图片,Z_3014-721050_1_20180927230035040_21_川CLL379.JPG
				String time = fileName.substring(16, 33);
				return time.compareTo(time1) >= 0 & time.compareTo(time2) <= 0;
			}
		}
	}

	// 获取到小时的文件目录
	private String getHour(String fileName, boolean company, String dataType) {
		if (company) {
			// 川大设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-243063_5_20181018031725762_川_1A328.XML
				return fileName.substring(24, 26);
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-243063_201810170700.XML
				return fileName.substring(23, 25);
			} else if (dataType.equals("3")) {
				// 3.一般违法
				// 川大设备没有一般违法
				return null;
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3014-243063_20181017114347963.XML
				return fileName.substring(23, 25);
			} else {
				// 5.过车图片,Z_3014-243063_5_20181016012650447_21_川Q39016.JPG
				return fileName.substring(24, 26);
			}
		} else {
			// VRoad设备
			if (dataType.equals("1")) {
				// 1.过车记录 ,Z_3014-721050_1_20180927230035040_22_川CLL379.XML
				return fileName.substring(24, 26);
			} else if (dataType.equals("2")) {
				// 2.交通流量,TM_3014-721050_20171001010400.XML
				return fileName.substring(23, 25);
			} else if (dataType.equals("3")) {
				// 3.一般违法,I_3014-721050_2_20180126232206119.XML
				return fileName.substring(24, 26);
			} else if (dataType.equals("4")) {
				// 4.交通事件,AL_3050-721062_20140909095910815.XML
				return fileName.substring(23, 25);
			} else {
				// 5.过车图片,Z_3014-721050_1_20180927230035040_21_川CLL379.JPG
				return fileName.substring(24, 26);
			}
		}
	}

	// 该方法获取FTP下的文件,并过滤掉图片
	private List<String> getFtpFiles(String ip, int port, String username, String password, String remotePath,
			String fileType) {
		try {
			List<String> list = new ArrayList<String>();
			FTPClient ftp = new FTPClient();
			ftp.connect(ip, port);
			ftp.login(username, password);
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				String fileName = new String(ff.getName().getBytes("ISO8859-1"), "GBK");
				if (fileType.equals("XML")) {
					if (fileName.contains("XML")) {
						logger.debug("FTP文件名:" + remotePath + fileName);
						list.add(remotePath + fileName);
					}
				} else {
					if (fileName.contains("JPG")) {
						logger.debug("FTP文件名:" + remotePath + fileName);
						list.add(remotePath + fileName);
					}
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

}
