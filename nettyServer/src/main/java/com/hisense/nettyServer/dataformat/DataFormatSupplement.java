package com.hisense.nettyServer.dataformat;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.DomParse;
import com.hisense.nettyServer.support.util.Enums;
import com.hisense.nettyServer.support.util.FtpDownLoadUtil;
import com.hisense.nettyServer.support.util.ImageParseStorageUtil;
import com.hisense.nettyServer.support.util.PropertiesUtil;
import com.hisense.nettyServer.support.util.SingletonObject;

/**
 * <p>Title: DataFormat</p>
 * <p>Description:将xml格式的数据转换为字符串</p>
 * <p>Company:青岛海信网络科技有限公司</p>
 * @author liangshan
 */

public class DataFormatSupplement {
	private static DomParse domParse;
	private static Logger logger=LoggerFactory.getLogger("errorLog");
	private final static Enums ENUMS=SingletonObject.getEnums();
	private static ExecutorService threadPool= SingletonObject.getThreadPool();;
	private static ImageParseStorageUtil imageUtil=new ImageParseStorageUtil();
	/**
	 * 数据标准化，包括过车，违法，外场设备日志数据文件
	 * @param xmlString
	 * @return
	 */
	public DataFormatResult<Boolean, String, String, String> dataFormatString(String xmlString) {
		 xmlString = StringUtils.trim(xmlString).replace("/r/n", "").replaceAll(">		<", "><").replaceAll(">        <", "><").replaceAll(">		<", "><").replaceAll(">    <", "><").replaceAll(">	<", "><").replaceAll(">          <", "><").replaceAll(">  <", "><");
		 try {
			Document document = domParse.parseXmlToDocument(xmlString);
			NodeList nodeList = document.getElementsByTagName("Message");
			Node item = nodeList.item(0);
			NodeList childNodes = item.getChildNodes();
			Node item2 = childNodes.item(1);
			NamedNodeMap attributes2 = item2.getAttributes();
			Node namedItem2 = attributes2.getNamedItem("Type");
			String nodeValue2 = namedItem2.getNodeValue();
			if(nodeValue2!=null&&!nodeValue2.isEmpty()) {
				if("PlateInfo".equals(nodeValue2)) {
					return formatPlateInfo(document);
				}else if("DeviceLog".equals(nodeValue2)) {
					if(isVRoad(xmlString)) {
						return formatDeviceLogVRoad(document);
					}else{
						return formatDeviceLog(document);
					}
				}else if("ViolationInfo".equals(nodeValue2)) {
					return formatViolationInfoVRoad(document);
				}else{
					return new DataFormatResult<Boolean, String, String, String>(false, "", "Type类型不在指定类型范围中","");
				}
			}else {
				return new DataFormatResult<Boolean, String, String, String>(false, "", "Type类型为空","");
			}
		} catch (Exception e) {
			logger.error("标准化报错了,原始数据为："+xmlString);
			e.printStackTrace();
		}
		return new DataFormatResult<Boolean, String, String, String>(false, "", "xml格式错误，解析失败","");
	}
	/**
	 * 数据标准化，包括流量，事件,前端检测主机运行日志
	 * @param xmlString
	 * @return
	 */
	public DataFormatResult<Boolean, List<String>, String, String> dataFormatList(String xmlString) {
		 xmlString = StringUtils.trim(xmlString).replace("/r/n", "").replaceAll(">		<", "><").replaceAll(">        <", "><").replaceAll(">		<", "><").replaceAll(">    <", "><").replaceAll(">	<", "><").replaceAll(">          <", "><").replaceAll(">  <", "><"); 
		 try {
			Document document = domParse.parseXmlToDocument(xmlString);
			NodeList nodeList = document.getElementsByTagName("Message");
			Node item = nodeList.item(0);
			NodeList childNodes = item.getChildNodes();
			Node item2 = childNodes.item(1);
			NamedNodeMap attributes2 = item2.getAttributes();
			Node namedItem2 = attributes2.getNamedItem("Type");
			String nodeValue2 = namedItem2.getNodeValue();
			if(nodeValue2!=null&&!nodeValue2.isEmpty()) {
				if("TrafficMeasure".equals(nodeValue2)) {
					if(isVRoad(xmlString)) {
						return formatTrafficMeasureVRoad(document);
					}else {
						return formatTrafficMeasure(document);
					}
				}else if("TrafficAlert".equals(nodeValue2)) {
					if(isVRoad(xmlString)) {
						return formatTrafficAlertVRoad(document);
					}else{
						return formatTrafficAlert(document);
					}
					
				}else if("DeviceOperationLog".equals(nodeValue2)) {
					return formatDeviceOperationLog(document);
				}else{
					return new DataFormatResult<Boolean, List<String>, String, String>(false, null, "Type类型不在指定类型范围中","");
				}
			}else {
				return new DataFormatResult<Boolean,List<String>, String, String>(false, null, "Type类型为空","");
			}
		} catch (Exception e) {
			logger.error("标准化报错了,原始数据为："+xmlString);
			e.printStackTrace();
		}
		return new DataFormatResult<Boolean, List<String>, String, String>(false, null, "xml格式错误，解析失败","");
	}
	public static String dayOrNight(String time) {
		String hour =time.substring(11, 13);
		if(8<=Integer.parseInt(hour)&&Integer.parseInt(hour)<20){
			return "1";
		}else{
			return "2";
		}
		
	}
	public static Boolean isVRoad(String xmlString) {
		try {
			Document document = domParse.parseXmlToDocument(xmlString);
			NodeList nodeList = document.getElementsByTagName("Message");
			Node item = nodeList.item(0);
			NodeList childNodes = item.getChildNodes();
			Node item2 = childNodes.item(0);
			Node item6 = childNodes.item(1);
			NodeList childNodes2 = item2.getChildNodes();
			NodeList childNodes3 = item6.getChildNodes();
			//MessageHeader
			Node item3 = childNodes2.item(0);
			Node item4 = childNodes2.item(1);
			Node item5 = childNodes2.item(2);
			
			String textContent = item4.getTextContent();
			if("VRoad".equals(textContent)||"vroad".equals(textContent)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
			return false;
	}
	public String getXmlType(String xmlString) {
		try {
		Document document = domParse.parseXmlToDocument(xmlString);
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(1);
		NamedNodeMap attributes2 = item2.getAttributes();
		Node namedItem2 = attributes2.getNamedItem("Type");
		String nodeValue2 = namedItem2.getNodeValue();
		return nodeValue2;
	} catch (Exception e) {
		e.printStackTrace();	
	}
		return "";
	}
	/**
	 * 标准化过车数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, String, String, String>  formatPlateInfo(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);
		Node item13 = childNodes3.item(6);
		Node item14 = childNodes3.item(7);
		Node item15 = childNodes3.item(8);
		Node item16 = childNodes3.item(9);
		
		Node item17 = childNodes3.item(10);
		Node item18 = childNodes3.item(11);
		Node item19 = childNodes3.item(12);
		Node item20 = childNodes3.item(13);
		Node item21 = childNodes3.item(14);
		Node item22 = childNodes3.item(15);
		
		Boolean newEnerg=true;
		if (item22==null)
		newEnerg=false;
		//数据拼接
		StringBuffer stringBuffer=new StringBuffer();
		//接口版本
		stringBuffer.append("1.0").append(",");
		//设备ID
		stringBuffer.append(item7.getTextContent()).append(",");
		//电警设备ID
		stringBuffer.append(item8.getTextContent()).append(",");
		//采集地址
		stringBuffer.append(item9.getTextContent()).append(",");
		//类型
		stringBuffer.append("").append(",");
		//号牌号码
		stringBuffer.append(item11.getTextContent()).append(",");
		//置信度
		stringBuffer.append(item13.getTextContent()).append(",");
		//号牌种类
		stringBuffer.append("").append(",");
		//号牌颜色,枚举转换
		stringBuffer.append((ENUMS.carColor.get(item12.getTextContent())!=null?ENUMS.carColor.get(item12.getTextContent())!=null:"")).append(",");
		//车标
		stringBuffer.append(item18.getTextContent()).append(",");
		//车身颜色
		stringBuffer.append(item19.getTextContent()).append(",");
		//车辆类型
		stringBuffer.append(item10.getTextContent()).append(",");
		//车种
		stringBuffer.append(item17.getTextContent()).append(",");
		//新能源标志
		stringBuffer.append((newEnerg?item20.getTextContent():"")).append(",");
		//放大号牌
		stringBuffer.append((newEnerg?item21.getTextContent():"")).append(",");
		//车道号
		stringBuffer.append(item15.getTextContent()).append(",");
		//车速
		stringBuffer.append(item16.getTextContent()).append(",");
		//行驶方向
		stringBuffer.append(item14.getTextContent()).append(",");
		//拼接 图片1路径
		//获取时间参数 yyyy-mm-dd HH:MM:SS.zzz
		String date="";
		String hour="";
		String picUrl="";

		if(newEnerg){
			date=item22.getTextContent().substring(0, 10).replaceAll("-", "");
			hour=item22.getTextContent().substring(11, 13);
			picUrl="Z_"+item7.getTextContent()+"_"+item15.getTextContent()+"_"+item22.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")+"_"+"21"+"_"+(!"".equals(item11.getTextContent())?item11.getTextContent():"--------")+".JPG";

		}else{
			date=item20.getTextContent().substring(0, 10).replaceAll("-", "");
			hour=item20.getTextContent().substring(11, 13);
			picUrl="Z_"+item7.getTextContent()+"_"+item15.getTextContent()+"_"+item20.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")+"_"+"21"+"_"+(!"".equals(item11.getTextContent())?item11.getTextContent():"--------")+".JPG";

		}
		
		stringBuffer.append("/cifspool/"+PropertiesUtil.getName("FTP_PATH")+PropertiesUtil.getName("CIFSPOOL")+PropertiesUtil.getName("VEH_PHOTO")+"/"+item7.getTextContent()+"/"
				+date+"/"+hour+"/"+picUrl).append(",");
		//Z_3014-140001_1_20120321101234023_21_川A12345.JPG
		//完成川大设备的图片下载
		String textContent = item4.getTextContent();
		if("VRoad".equals(textContent)||"vroad".equals(textContent)){
			
		}else{
		
		//拼接图片下载的参数
		//ftp的ip
		final String ftpHost=(String) ENUMS.devciceNumToDeviceIp.get(item7.getTextContent());
		//用户名
		final String ftpUserName=PropertiesUtil.getName("CDZSFtpUsr");
		//密码
		final String ftpPassword=PropertiesUtil.getName("CDZSFtpPwd");
		//接口
		Integer parseInt = Integer.parseInt(PropertiesUtil.getName("CDZSFtpPort"));
		final int ftpPort=parseInt!=null?parseInt:21;
		//文件原路径，判断何种设备，ftp根目录路径不同
		StringBuffer ftpOriginPath=new StringBuffer();
		//目录
		ftpOriginPath.append("/mnt/VideoData/VEH_PHOTO/");
		//日期
		String picdate=item22.getTextContent().substring(0, 10).replaceAll("-", "");
		ftpOriginPath.append(picdate).append("/");
		//小时
		String pichour=item22.getTextContent().substring(11, 13);
		ftpOriginPath.append(pichour).append("/");
		String ftpPath=ftpOriginPath.toString();
		//文件新路径
		final StringBuffer newLocalPath=new StringBuffer();
		//根目录
		newLocalPath.append("/cifspool");
		//服务路径
		newLocalPath.append("/").append(PropertiesUtil.getName("FTP_PATH"));
		//ftp目录
		newLocalPath.append("/FTPROOT");
		//数据种类
		newLocalPath.append("/").append("VEH_PHOTO");
		//设备编号
		newLocalPath.append("/").append(item7.getTextContent());
		//日期
		newLocalPath.append("/").append(picdate);
		//小时
		newLocalPath.append("/").append(pichour);
		final String localPath=newLocalPath.toString();
		File file =new File(localPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		final String finalPicUrl = picUrl;
		threadPool.execute(new Runnable() {
				@Override
				public void run() {
				       FtpDownLoadUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath,finalPicUrl ); 
				}
			});
		}
		//图片2路径
		stringBuffer.append("").append(",");
		//图片3路径
		stringBuffer.append("").append(",");
		//采集时间
		stringBuffer.append((newEnerg?item22.getTextContent():item20.getTextContent())).append(",");
		//上传时间
		stringBuffer.append((newEnerg?item22.getTextContent().substring(0, 19):item20.getTextContent().substring(0, 19)));
		return new DataFormatResult<Boolean, String, String, String>(true, stringBuffer.toString(), "xml解析成功",PropertiesUtil.getName("PASS_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化流量数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean,List<String>, String, String>  formatTrafficMeasure(Document document){
		
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);

		NamedNodeMap attributes = item12.getAttributes();
		Node namedItem = attributes.getNamedItem("num");
		String nodeValue = namedItem.getNodeValue();
		
		NodeList childNodes4 = item12.getChildNodes();
		List list=new ArrayList();
		for(int i=0;i<Integer.parseInt(nodeValue);i++) {
			Node item13 = childNodes4.item(i);
			NodeList childNodes5 = item13.getChildNodes();
			Node item14 = childNodes5.item(0);
			Node item15 = childNodes5.item(1);
			Node item16 = childNodes5.item(2);
			Node item17 = childNodes5.item(3);
			Node item18 = childNodes5.item(4);
			Node item19 = childNodes5.item(5);
			Node item20 = childNodes5.item(6);
			Node item21 = childNodes5.item(7);
	
			NodeList childNodes6 = item17.getChildNodes();
			int length = childNodes6.getLength();
			List<String> list2=new ArrayList<>();
			
			for(int j=0;j<length;j++) {
				Node item22 = childNodes6.item(j);
				list2.add(item22.getTextContent());
			}
			StringBuffer stringBuffer=new StringBuffer();
			
			//接口类型
			stringBuffer.append("VOLUME").append(",");
			//接口版本
			stringBuffer.append("1.0").append(",");
			//设备ID
			stringBuffer.append(item7.getTextContent()).append(",");
			//电警设备ID
			stringBuffer.append(item8.getTextContent()).append(",");
			//设备ip
			stringBuffer.append("").append(",");
			//采集地址
			stringBuffer.append(item9.getTextContent()).append(",");
			//开始检测时间
			stringBuffer.append(item10.getTextContent()).append(",");
			//检测间隔
			stringBuffer.append(item11.getTextContent()).append(",");
			//总车道数
			stringBuffer.append(nodeValue).append(",");
			//车道号
			stringBuffer.append(item14.getTextContent()).append(",");
			//方向
			stringBuffer.append(item15.getTextContent()).append(",");
			//总流量
			stringBuffer.append(item16.getTextContent()).append(",");
			if(list2.size()>0) {
				//车辆类型1流量
				stringBuffer.append(list2.get(0)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>1) {
				//车辆类型2流量
				stringBuffer.append(list2.get(1)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>2) {
				//车辆类型3流量
				stringBuffer.append(list2.get(2)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>3) {
				//车辆类型4流量
				stringBuffer.append(list2.get(3)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>4) {
				//车辆类型5流量
				stringBuffer.append(list2.get(4)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			//平均速度
			stringBuffer.append(item18.getTextContent()).append(",");
			//排队长度
			stringBuffer.append(item19.getTextContent()).append(",");
			//平均占有率
			stringBuffer.append(item20.getTextContent()).append(",");
			//平均车辆间距
			stringBuffer.append(item21.getTextContent()).append(",");
			//上传时间
			stringBuffer.append("");
			list.add(stringBuffer.toString());
		}
		return new DataFormatResult<Boolean, List<String>, String, String>( true , list , "xml解析成功",PropertiesUtil.getName("FLOW_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化流量数据多个Volumes格式
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean,List<String>, String, String>  formatTrafficMeasureVRoad(Document document){
		
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);

		NamedNodeMap attributes = item12.getAttributes();
		Node namedItem = attributes.getNamedItem("num");
		String nodeValue = namedItem.getNodeValue();
		
		NodeList childNodes4 = item12.getChildNodes();
		
		List list=new ArrayList();
		for(int i=0;i<Integer.parseInt(nodeValue);i++) {
			Node item13 = childNodes4.item(i);
			NodeList childNodes5 = item13.getChildNodes();
			int length = childNodes5.getLength();
			List<String> list2=new ArrayList<>();
			
			for(int j=0;j<length;j++) {
					Node item14 = childNodes5.item(j);
					list2.add(item14.getTextContent());
			}
			StringBuffer stringBuffer=new StringBuffer();
			
			//接口类型
			stringBuffer.append("VOLUME").append(",");
			//接口版本
			stringBuffer.append("1.0").append(",");
			//设备ID
			stringBuffer.append(item7.getTextContent()).append(",");
			//电警设备ID
			stringBuffer.append(item8.getTextContent()).append(",");
			//设备ip
			stringBuffer.append("").append(",");
			//采集地址
			stringBuffer.append(item9.getTextContent()).append(",");
			//开始检测时间
			stringBuffer.append(item10.getTextContent()).append(",");
			//检测间隔
			stringBuffer.append(item11.getTextContent()).append(",");
			//总车道数
			stringBuffer.append(nodeValue).append(",");
			//车道号
			stringBuffer.append(list2.get(0)).append(",");
			//方向
			stringBuffer.append(list2.get(1)).append(",");
			//总流量
			stringBuffer.append(list2.get(2)).append(",");
			if(list2.size()>7) {
				//车辆类型1流量
				stringBuffer.append(list2.get(3)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>8) {
				//车辆类型2流量
				stringBuffer.append(list2.get(4)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>9) {
				//车辆类型3流量
				stringBuffer.append(list2.get(5)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>10) {
				//车辆类型4流量
				stringBuffer.append(list2.get(6)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}
			if(list2.size()>11) {
				//车辆类型5流量
				stringBuffer.append(list2.get(7)).append(",");
			}else {
				stringBuffer.append("").append(",");
			}

			//平均速度
			stringBuffer.append(list2.get(list2.size()-4)).append(",");
			//排队长度
			stringBuffer.append(list2.get(list2.size()-3)).append(",");
			//平均占有率
			stringBuffer.append(list2.get(list2.size()-2)).append(",");
			//平均车辆间距
			stringBuffer.append(list2.get(list2.size()-1)).append(",");
			//上传时间
			stringBuffer.append(item10.getTextContent().substring(0, 19));
			list.add(stringBuffer.toString());
		}
		return new DataFormatResult<Boolean, List<String>, String, String>( true , list , "xml解析成功",PropertiesUtil.getName("FLOW_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化交通事件数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, List<String>, String, String>  formatTrafficAlertVRoad(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(2);
		Node item9 = childNodes3.item(3);
		
		NodeList childNodes4 = item9.getChildNodes();
		List list=new ArrayList();
		for(int i=0;i<childNodes4.getLength();i++) {
			Node item10 = childNodes4.item(i);
			NodeList childNodes5 = item10.getChildNodes();
			Node item11 = childNodes5.item(0);			
			Node item12 = childNodes5.item(1);
			Node item13 = childNodes5.item(2);
			Node item14 = childNodes5.item(3);
			Node item15 = childNodes5.item(4);
			Node item16 = childNodes5.item(5);
			Node item17 = childNodes5.item(6);
			Node item18 = childNodes5.item(7);
			Node item19 = childNodes5.item(8);
			Node item20 = childNodes5.item(9);
			Node item21 = childNodes5.item(10);
			Node item22 = childNodes5.item(11);
			
			
			StringBuffer stringBuffer=new StringBuffer();
			//接口类型
			stringBuffer.append("EVENT").append(",");
			//接口版本
			stringBuffer.append("1.0").append(",");
			//数据来源
			stringBuffer.append("0").append(",");
			//设备ID
			stringBuffer.append(item7.getTextContent()).append(",");
			//设备位置
			stringBuffer.append(item8.getTextContent()).append(",");
			//事件记录生成时间
			stringBuffer.append(item11.getTextContent()).append(",");
			//事件开始时间
			stringBuffer.append(item12.getTextContent()).append(",");
			//事件结束时间
			stringBuffer.append(item13.getTextContent()).append(",");
			//车道号
			stringBuffer.append(item14.getTextContent()).append(",");
			//事件类型
			stringBuffer.append(item15.getTextContent()).append(",");
			//行驶方向
			stringBuffer.append(item16.getTextContent()).append(",");
			//事件发生的图像位置x坐标
			stringBuffer.append(item17.getTextContent()).append(",");
			//事件发生的图像位置y坐标
			stringBuffer.append(item18.getTextContent()).append(",");
			
			NamedNodeMap attributes = item19.getAttributes();
			Node namedItem = attributes.getNamedItem("num");
			String nodeValue = namedItem.getNodeValue();
			
			NodeList childNodes6 = item19.getChildNodes();
			//图片数
			stringBuffer.append(nodeValue).append(",");
			//图片url1
			//图片名称1
			//图片url2
			//图片名称2
			//图片url3
			//图片名称3
			//图片url4
			//图片名称4
			for(int j=0;j<4;j++) {
				if(j<Integer.parseInt(nodeValue)) {
					Node item23 = childNodes6.item(j);
					NamedNodeMap attributes2 = item23.getAttributes();
					Node namedItem2 = attributes2.getNamedItem("name");
					//拼接url和将图片入库
					StringBuffer stringBuffer2=new StringBuffer();
					//根目录
					stringBuffer2.append("/cifspool");
					//服务路径
					stringBuffer2.append("/").append(PropertiesUtil.getName("FTP_PATH"));
					//数据种类
					stringBuffer2.append("/").append("ALERT_PIC");
					//设备编号
					stringBuffer2.append("/").append(item7.getTextContent());
					//获取时间参数 yyyy-mm-dd HH:MM:SS.zzz
					String date=item11.getTextContent().substring(0, 10).replaceAll("-", "");
					String hour=item11.getTextContent().substring(11, 13);
					//日期
					stringBuffer2.append("/").append(date);
					//小时
					stringBuffer2.append("/").append(hour);
					
					//创建文件夹
					File file=new File(stringBuffer2.toString());
					if(!file.exists()){
						file.mkdirs();
					}
					//图片名称
					StringBuffer picUrl =new StringBuffer();
					//AL_3014-140001_20120321101234023_21.JPG
					picUrl.append("AL_");
					picUrl.append(item7.getTextContent()).append("_");
					picUrl.append(item11.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")).append("_");
					picUrl.append(nodeValue);
					picUrl.append(j+1);
					stringBuffer2.append("/").append(picUrl.toString()).append(".jpg");
					String path=stringBuffer2.toString();
					stringBuffer.append(path).append(",");
					imageUtil.generateImage(item23.getTextContent(),path);
					//图片名称
					stringBuffer.append(picUrl.toString()).append(".jpg").append(",");
				}else {
					stringBuffer.append("").append(",");
					stringBuffer.append("").append(",");
				}
			}

			//录像开始时间
			stringBuffer.append(item20.getTextContent()).append(",");
			//录像结束时间
			stringBuffer.append(item21.getTextContent()).append(",");
			//视频地址
			//ftp的ip
			String ftpHost=(String) ENUMS.devciceNumToDeviceIp.get(item7.getTextContent());
			//用户名
			String ftpUserName=PropertiesUtil.getName("masterFtpUsr");
			//密码
			String ftpPassword=PropertiesUtil.getName("masterFtpPwd");
			//接口
			Integer parseInt = Integer.parseInt(PropertiesUtil.getName("masterFtpPort"));
			int ftpPort=parseInt!=null?parseInt:21;
			//文件原路径，判断何种设备，ftp根目录路径不同
			StringBuffer ftpOriginPath=new StringBuffer();
			//目录
			ftpOriginPath.append("/mnt/VideoData/ALERT_VIDEO/");
			//日期
			String date=item11.getTextContent().substring(0, 10).replaceAll("-", "");
			ftpOriginPath.append(date).append("/");
			//小时
			String hour=item11.getTextContent().substring(11, 13);
			ftpOriginPath.append(hour).append("/");
			String ftpPath=ftpOriginPath.toString();
			//文件新路径
			StringBuffer newLocalPath=new StringBuffer();
			//根目录
			newLocalPath.append("/cifspool");
			//服务路径
			newLocalPath.append("/").append(PropertiesUtil.getName("FTP_PATH"));
			//数据种类
			newLocalPath.append("/").append("ALERT_VIDEO");
			//设备编号
			newLocalPath.append("/").append(item7.getTextContent());
			//日期
			newLocalPath.append("/").append(date);
			//小时
			newLocalPath.append("/").append(hour);
			String localPath=newLocalPath.toString();
			File file =new File(localPath);
			if(!file.exists()){
				file.mkdirs();
			}
			//拼接视频路径
			//视频名称
			StringBuffer videoName=new StringBuffer();
			videoName.append("AV_");
			//设备编号
			videoName.append(item7.getTextContent()).append("_");
			//车道号
			videoName.append(item14.getTextContent()).append("_");
			//时间
			videoName.append(item11.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")).append("_");
			//事件类型
			videoName.append(item15.getTextContent()).append(".avi");
			String fileName=videoName.toString();
			//创建下载任务
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
			       FtpDownLoadUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName); 
				}
			});
			stringBuffer.append(localPath+fileName);
			list.add(stringBuffer.toString());
		}
		return new DataFormatResult<Boolean, List<String>, String, String>( true , list , "xml解析成功",PropertiesUtil.getName("TRAFFIC_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化交通事件数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, List<String>, String, String>  formatTrafficAlert(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		
		NodeList childNodes4 = item9.getChildNodes();
		List list=new ArrayList();
		for(int i=0;i<childNodes4.getLength();i++) {
			Node item10 = childNodes4.item(i);
			NodeList childNodes5 = item10.getChildNodes();
			Node item11 = childNodes5.item(0);			
			Node item12 = childNodes5.item(1);
			Node item13 = childNodes5.item(2);
			Node item14 = childNodes5.item(3);
			Node item15 = childNodes5.item(4);
			Node item16 = childNodes5.item(5);
			Node item17 = childNodes5.item(6);
			Node item18 = childNodes5.item(7);
			Node item19 = childNodes5.item(8);
			Node item20 = childNodes5.item(9);
			Node item21 = childNodes5.item(10);
			Node item22 = childNodes5.item(11);
			
			
			StringBuffer stringBuffer=new StringBuffer();
			//接口类型
			stringBuffer.append("EVENT").append(",");
			//接口版本
			stringBuffer.append("1.0").append(",");
			//数据来源
			stringBuffer.append("0").append(",");
			//设备ID
			stringBuffer.append(item7.getTextContent()).append(",");
			//设备位置
			stringBuffer.append(item8.getTextContent()).append(",");
			//事件记录生成时间
			stringBuffer.append(item11.getTextContent()).append(",");
			//事件开始时间
			stringBuffer.append(item12.getTextContent()).append(",");
			//事件结束时间
			stringBuffer.append(item13.getTextContent()).append(",");
			//车道号
			stringBuffer.append(item14.getTextContent()).append(",");
			//事件类型
			stringBuffer.append(item15.getTextContent()).append(",");
			//行驶方向
			stringBuffer.append(item16.getTextContent()).append(",");
			//事件发生的图像位置x坐标
			stringBuffer.append(item17.getTextContent()).append(",");
			//事件发生的图像位置y坐标
			stringBuffer.append(item18.getTextContent()).append(",");
			
			NamedNodeMap attributes = item19.getAttributes();
			Node namedItem = attributes.getNamedItem("num");
			String nodeValue = namedItem.getNodeValue();
			
			NodeList childNodes6 = item19.getChildNodes();
			//图片数
			stringBuffer.append(nodeValue).append(",");
			//图片url1
			//图片名称1
			//图片url2
			//图片名称2
			//图片url3
			//图片名称3
			//图片url4
			//图片名称4
			for(int j=0;j<4;j++) {
				if(j<Integer.parseInt(nodeValue)) {
					Node item23 = childNodes6.item(j);
					NamedNodeMap attributes2 = item23.getAttributes();
					Node namedItem2 = attributes2.getNamedItem("name");
					//拼接url和将图片入库
					StringBuffer stringBuffer2=new StringBuffer();
					//根目录
					stringBuffer2.append("/cifspool");
					//服务路径
					stringBuffer2.append("/").append(PropertiesUtil.getName("FTP_PATH"));
					//数据种类
					stringBuffer2.append("/").append("ALERT_PIC");
					//设备编号
					stringBuffer2.append("/").append(item7.getTextContent());
					//获取时间参数 yyyy-mm-dd HH:MM:SS.zzz
					String date=item11.getTextContent().substring(0, 10).replaceAll("-", "");
					String hour=item11.getTextContent().substring(11, 13);
					//日期
					stringBuffer2.append("/").append(date);
					//小时
					stringBuffer2.append("/").append(hour);
					
					//创建文件夹
					File file=new File(stringBuffer2.toString());
					if(!file.exists()){
						file.mkdirs();
					}
					//图片名称
					StringBuffer picUrl =new StringBuffer();
					//AL_3014-140001_20120321101234023_21.JPG
					picUrl.append("AL_");
					picUrl.append(item7.getTextContent()).append("_");
					picUrl.append(item11.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")).append("_");
					picUrl.append(nodeValue);
					picUrl.append(j+1);
					stringBuffer2.append("/").append(picUrl.toString()).append(".jpg");
					String path=stringBuffer2.toString();
					stringBuffer.append(path).append(",");
					imageUtil.generateImage(item23.getTextContent(),path);
					//图片名称
					stringBuffer.append(picUrl.toString()).append(".jpg").append(",");
				}else {
					stringBuffer.append("").append(",");
					stringBuffer.append("").append(",");
				}
			}

			//录像开始时间
			stringBuffer.append(item20.getTextContent()).append(",");
			//录像结束时间
			stringBuffer.append(item21.getTextContent()).append(",");
			//视频地址
			
			//ftp的ip
			String ftpHost=(String) ENUMS.devciceNumToDeviceIp.get(item7.getTextContent());
			//用户名
			String ftpUserName=PropertiesUtil.getName("CDZSFtpUsr");
			//密码
			String ftpPassword=PropertiesUtil.getName("CDZSFtpPwd");
			//接口
			Integer parseInt = Integer.parseInt(PropertiesUtil.getName("CDZSFtpPort"));
			int ftpPort=parseInt!=null?parseInt:21;
			//文件原路径，判断何种设备，ftp根目录路径不同
			StringBuffer ftpOriginPath=new StringBuffer();
			//目录
			ftpOriginPath.append("/mnt/VideoData/ALERT_VIDEO/");
			//日期
			String date=item11.getTextContent().substring(0, 10).replaceAll("-", "");
			ftpOriginPath.append(date).append("/");
			//小时
			String hour=item11.getTextContent().substring(11, 13);
			ftpOriginPath.append(hour).append("/");
			String ftpPath=ftpOriginPath.toString();
			//文件新路径
			StringBuffer newLocalPath=new StringBuffer();
			//根目录
			newLocalPath.append("/cifspool");
			//服务路径
			newLocalPath.append("/").append(PropertiesUtil.getName("FTP_PATH"));
			//数据种类
			newLocalPath.append("/").append("ALERT_VIDEO");
			//设备编号
			newLocalPath.append("/").append(item7.getTextContent());
			//日期
			newLocalPath.append("/").append(date);
			//小时
			newLocalPath.append("/").append(hour);
			
			
			String localPath=newLocalPath.toString();
			File file =new File(localPath);
			if(!file.exists()){
				file.mkdirs();
			}
			//拼接视频路径
			//视频名称
			StringBuffer videoName=new StringBuffer();
			videoName.append("AV_");
			//设备编号
			videoName.append(item7.getTextContent()).append("_");
			//车道号
			videoName.append(item14.getTextContent()).append("_");
			//时间
			videoName.append(item11.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")).append("_");
			//事件类型
			videoName.append(item15.getTextContent()).append(".avi");
			String fileName=videoName.toString();
			//创建下载任务
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
			       FtpDownLoadUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName); 
				}
			});
			stringBuffer.append(localPath+fileName);
			list.add(stringBuffer.toString());
		}
		return new DataFormatResult<Boolean, List<String>, String, String>( true , list , "xml解析成功",PropertiesUtil.getName("TRAFFIC_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化违法事件数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, String, String, String>  formatViolationInfoVRoad(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);
		Node item13 = childNodes3.item(6);
		Node item14 = childNodes3.item(7);
		Node item15 = childNodes3.item(8);
		Node item16 = childNodes3.item(9);
		
		Node item17 = childNodes3.item(10);
		Node item18 = childNodes3.item(11);
		Node item19 = childNodes3.item(12);
		Node item20 = childNodes3.item(13);
		Node item21 = childNodes3.item(14);
		Node item22 = childNodes3.item(15);
		Node item23 = childNodes3.item(16);
		//新能源标志
		Node item24 = childNodes3.item(17);
		//记录生成时间
		Node item25 = childNodes3.item(18);
		//图片
		Node item26 = childNodes3.item(19);
		StringBuffer stringBuffer=new StringBuffer();
		//接口类型
		stringBuffer.append("").append(",");
		//接口版本
		stringBuffer.append("").append(",");
		//数据来源
		stringBuffer.append("").append(",");
		//违法数据分类
		stringBuffer.append("").append(",");
		//违法类型
		stringBuffer.append(item10.getTextContent()).append(",");
		//违法行为代码
		stringBuffer.append(item11.getTextContent()).append(",");
		//设备编号
		stringBuffer.append(item7.getTextContent()).append(",");
		//电警设备编号
		stringBuffer.append(item8.getTextContent()).append(",");
		//设备位置
		stringBuffer.append(item9.getTextContent()).append(",");
		//采集地点编号
		stringBuffer.append("").append(",");
		//号牌种类
		stringBuffer.append("").append(",");
		//号牌号码
		stringBuffer.append(item13.getTextContent()).append(",");
		//号牌颜色
		stringBuffer.append((ENUMS.carColor.get(item14.getTextContent())!=null?ENUMS.carColor.get(item14.getTextContent())!=null:"")).append(",");
		//车型
		stringBuffer.append(item12.getTextContent()).append(",");
		//车种
		stringBuffer.append(item21.getTextContent()).append(",");
		//车标
		stringBuffer.append(item22.getTextContent()).append(",");
		//车身颜色
		stringBuffer.append(item23.getTextContent()).append(",");
		//行驶方向
		stringBuffer.append(item16.getTextContent()).append(",");
		//车道号
		stringBuffer.append(item17.getTextContent()).append(",");
		//置信度
		stringBuffer.append(item15.getTextContent()).append(",");
		//车速
		stringBuffer.append(item18.getTextContent()).append(",");
		//限速最低值
		stringBuffer.append(item19.getTextContent()).append(",");
		//限速最高值
		stringBuffer.append(item20.getTextContent()).append(",");
		//红灯亮起时间
		stringBuffer.append("").append(",");
		//红灯结束时间
		stringBuffer.append("").append(",");
		
		NamedNodeMap attributes = item26.getAttributes();
		Node namedItem = attributes.getNamedItem("num");
		String nodeValue = namedItem.getNodeValue();
		//图片总数
		stringBuffer.append(nodeValue).append(",");
		NodeList childNodes4 = item26.getChildNodes();
		Node firstChild = item26.getFirstChild();
		//证据图片1
		//图片1名称
		//图片1抓拍时间
		//证据图片2
		//图片2名称
		//图片2抓拍时间
		//证据图片3
		//图片3名称
		//图片3抓拍时间
		//证据图片4
		//图片4名称
		//图片4抓拍时间
		for(int i=0;i<4;i++) {
			if(i<Integer.parseInt(nodeValue)) {
				Node item28 = childNodes4.item(0);
				NamedNodeMap attributes2 = item28.getAttributes();
				Node namedItem2 = attributes2.getNamedItem("name");
				Node namedItem3 = attributes2.getNamedItem("capTime");
				Node namedItem4 = attributes2.getNamedItem("pos");
				
				StringBuffer stringBuffer2=new StringBuffer();
				//拼接图片路径
				
				//根目录
				stringBuffer2.append("/cifspool");
				//服务路径
				stringBuffer2.append("/").append(PropertiesUtil.getName("FTP_PATH"));
				//数据种类
				stringBuffer2.append("/").append("VIOLATION_PIC");
				//设备编号
				stringBuffer2.append("/").append(item7.getTextContent());
				//获取时间参数 yyyy-mm-dd HH:MM:SS.zzz
				String date=item25.getTextContent().substring(0, 10).replaceAll("-", "");
				String hour=item25.getTextContent().substring(11, 13);
				//日期
				stringBuffer2.append("/").append(date);
				//小时
				stringBuffer2.append("/").append(hour);
				
				//创建文件夹
				File file=new File(stringBuffer2.toString());
				if(!file.exists()){
					file.mkdirs();
				}
				//图片名称
				StringBuffer picUrl=new StringBuffer(); 
				//3014285001_050814213359714_12132.JPG
				//最好改为2018年，格式统一
				//3014285001_20180814213359714_12132.JPG
				//设备编号10位+"_"
				picUrl.append(item7.getTextContent().replace("_","")).append("_");
				//时间+"_"
				picUrl.append(item25.getTextContent().replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","")).append("_");
				//违法类型
				picUrl.append(item10.getTextContent());
				//日夜  8点为日夜区分时间
				picUrl.append(dayOrNight(item25.getTextContent()));
			    //车道  
				picUrl.append(item17.getTextContent());
				//连续数 
				picUrl.append(nodeValue);
				//本续数 
				picUrl.append(i+1);
				stringBuffer2.append("/").append(picUrl.toString()).append(".jpg");
				String path=stringBuffer2.toString();
				stringBuffer.append(path).append(",");
				imageUtil.generateImage(item28.getTextContent(),path);
				stringBuffer.append(picUrl.toString()).append(".jpg").append(",");
				stringBuffer.append(namedItem3.getNodeValue()).append(",");
			}else {
				stringBuffer.append("").append(",");
				stringBuffer.append("").append(",");
				stringBuffer.append("").append(",");
			}
		}
		
		//证据视频
		stringBuffer.append("").append(",");
		//证据音频
		stringBuffer.append("").append(",");
		//采集时间
		stringBuffer.append(item25.getTextContent()).append(",");
		//数据上传时间
		stringBuffer.append("").append(",");
		//新能源标识
		stringBuffer.append("").append(",");
		//放大号牌		
		stringBuffer.append("");
		return new DataFormatResult<Boolean, String, String, String>( true , stringBuffer.toString() , "xml解析成功",PropertiesUtil.getName("VIOLATION_TOPIC_SUPPLE"));
	}
	/**
	 * 标准化违法事件数据
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, String, String, String>  formatViolationInfo(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);
		Node item13 = childNodes3.item(6);
		Node item14 = childNodes3.item(7);
		Node item15 = childNodes3.item(8);
		Node item16 = childNodes3.item(9);
		
		Node item17 = childNodes3.item(10);
		Node item18 = childNodes3.item(11);
		Node item19 = childNodes3.item(12);
		Node item20 = childNodes3.item(13);
		Node item21 = childNodes3.item(14);
		Node item22 = childNodes3.item(15);
		Node item23 = childNodes3.item(16);
		//新能源标志
		Node item24 = childNodes3.item(17);
		//记录生成时间
		Node item25 = childNodes3.item(18);
		//图片
		Node item26 = childNodes3.item(19);
		//无
		Node item27 = childNodes3.item(20);

		StringBuffer stringBuffer=new StringBuffer();
		
		//接口类型
		stringBuffer.append("").append(",");
		//接口版本
		stringBuffer.append("").append(",");
		//数据来源
		stringBuffer.append("").append(",");
		//违法数据分类
		stringBuffer.append("").append(",");
		//违法类型
		stringBuffer.append(item10.getTextContent()).append(",");
		//违法行为代码
		stringBuffer.append(item11.getTextContent()).append(",");
		//设备编号
		stringBuffer.append(item7.getTextContent()).append(",");
		//电警设备编号
		stringBuffer.append(item8.getTextContent()).append(",");
		//设备位置
		stringBuffer.append(item9.getTextContent()).append(",");
		//采集地点编号
		stringBuffer.append("").append(",");
		//号牌种类
		stringBuffer.append("").append(",");
		//号牌号码
		stringBuffer.append(item13.getTextContent()).append(",");
		//号牌颜色
		stringBuffer.append((ENUMS.carColor.get(item14.getTextContent())!=null?ENUMS.carColor.get(item14.getTextContent())!=null:"")).append(",");
		//车型
		stringBuffer.append(item12.getTextContent()).append(",");
		//车种
		stringBuffer.append(item21.getTextContent()).append(",");
		//车标
		stringBuffer.append(item22.getTextContent()).append(",");
		//车身颜色
		stringBuffer.append(item23.getTextContent()).append(",");
		//行驶方向
		stringBuffer.append(item16.getTextContent()).append(",");
		//车道号
		stringBuffer.append(item17.getTextContent()).append(",");
		//置信度
		stringBuffer.append(item15.getTextContent()).append(",");
		//车速
		stringBuffer.append(item18.getTextContent()).append(",");
		//限速最低值
		stringBuffer.append(item19.getTextContent()).append(",");
		//限速最高值
		stringBuffer.append(item20.getTextContent()).append(",");
		//红灯亮起时间
		stringBuffer.append(item25.getTextContent()).append(",");
		//红灯结束时间
		stringBuffer.append(item26.getTextContent()).append(",");
		
		NamedNodeMap attributes = item27.getAttributes();
		Node namedItem = attributes.getNamedItem("num");
		String nodeValue = namedItem.getNodeValue();
		//图片总数
		stringBuffer.append(nodeValue).append(",");
		NodeList childNodes4 = item27.getChildNodes();
		
		//证据图片1
		//图片1名称
		//图片1抓拍时间
		//证据图片2
		//图片2名称
		//图片2抓拍时间
		//证据图片3
		//图片3名称
		//图片3抓拍时间
		//证据图片4
		//图片4名称
		//图片4抓拍时间
		for(int i=0;i<4;i++) {
			if(i<Integer.parseInt(nodeValue)) {
				Node item28 = childNodes4.item(i);
				NamedNodeMap attributes2 = item28.getAttributes();
				Node namedItem2 = attributes.getNamedItem("name");
				Node namedItem3 = attributes.getNamedItem("capTime");
				Node namedItem4 = attributes.getNamedItem("pos");
				
				stringBuffer.append(item28.getTextContent()).append(",");
				stringBuffer.append(namedItem2.getNodeValue()).append(",");
				stringBuffer.append(namedItem3.getNodeValue()).append(",");
			}else {
				stringBuffer.append("").append(",");
				stringBuffer.append("").append(",");
				stringBuffer.append("").append(",");
			}
		}
		
		//证据视频
		stringBuffer.append("").append(",");
		//证据音频
		stringBuffer.append("").append(",");
		//采集时间
		stringBuffer.append(item25.getTextContent()).append(",");
		//数据上传时间
		stringBuffer.append("").append(",");
		//新能源标识
		stringBuffer.append(item24.getTextContent()).append(",");
		//放大号牌		
		stringBuffer.append("").append(",");
		
		return new DataFormatResult<Boolean, String, String, String>( true , stringBuffer.toString() , "xml解析成功",PropertiesUtil.getName("VIOLATION_TOPIC"));
	}
	/**
	 * 标准化外场设备日志数据文件
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, String, String, String>  formatDeviceLogVRoad(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(2);
		Node item9 = childNodes3.item(3);
		Node item10 = childNodes3.item(4);
		Node item11 = childNodes3.item(5);
		Node item12 = childNodes3.item(6);
		Node item13 = childNodes3.item(7);
		Node item14 = childNodes3.item(8);
		Node item15 = childNodes3.item(9);
		Node item16 = childNodes3.item(10);
		
		Node item17 = childNodes3.item(11);
		Node item18 = childNodes3.item(12);
		Node item19 = childNodes3.item(13);
		Node item20 = childNodes3.item(14);
		Node item21 = childNodes3.item(15);
		//电警设备编号
		Node item28 = childNodes3.item(1);
		
		String nodeValue="";
		String textContent="";
		if(item16.hasChildNodes()){
			NodeList childNodes4 = item16.getChildNodes();
			Node item22 = childNodes4.item(0);
			textContent = item22.getTextContent();
			NamedNodeMap attributes = item22.getAttributes();
			Node namedItem = attributes.getNamedItem("id");
			nodeValue = namedItem.getNodeValue();
		}
		
		String nodeValue2="";
		String textContent2="";
		if(item17.hasChildNodes()){
			NodeList childNodes5 = item17.getChildNodes();
			Node item23 = childNodes5.item(0);
			NamedNodeMap attributes2 = item23.getAttributes();
			Node namedItem2 = attributes2.getNamedItem("id");

			nodeValue2 = namedItem2.getNodeValue();
			textContent2 =item23.getTextContent();
		}
		
		String nodeValue3="";
		String textContent3="";
		if(item18.hasChildNodes()){
			
			NodeList childNodes6 = item18.getChildNodes();
			Node item24 = childNodes6.item(0);
			NamedNodeMap attributes3 = item24.getAttributes();
			Node namedItem3 = attributes3.getNamedItem("id");
			
			nodeValue3 = namedItem3.getNodeValue();
			textContent3 =item24.getTextContent();
		}

		String nodeValue4="";
		String textContent4="";
		if(item19.hasChildNodes()){
			NodeList childNodes7 = item19.getChildNodes();
			Node item25 = childNodes7.item(0);
			NamedNodeMap attributes4 = item25.getAttributes();
			Node namedItem4 = attributes4.getNamedItem("id");
			
			nodeValue3 = namedItem4.getNodeValue();
			textContent3 =item25.getTextContent();
		}
	
		
		StringBuffer stringBuffer=new StringBuffer();
		
		//日志文件名称
		stringBuffer.append("").append(",");
		//设备自编号
		stringBuffer.append(item7.getTextContent()).append(",");
		//电警设备编号
		stringBuffer.append(item28.getTextContent()).append(",");
		//记录生成时间
		stringBuffer.append(item8.getTextContent()).append(",");
		//IP地址
		stringBuffer.append(item9.getTextContent()).append(",");
		//CPU温度
		stringBuffer.append(item10.getTextContent()).append(",");
		//CPU风扇速度
		stringBuffer.append(item11.getTextContent()).append(",");
		//剩余物理内存
		stringBuffer.append(item12.getTextContent()).append(",");
		//外场设备线程计数
		stringBuffer.append(item13.getTextContent()).append(",");
		//磁盘空间
		stringBuffer.append(item14.getTextContent()).append(",");
		//未上传违法数据量
		stringBuffer.append(item15.getTextContent()).append(",");
		//相机编号
		stringBuffer.append(nodeValue).append(",");
		//相机状态
		stringBuffer.append(textContent).append(",");
		//相机状态描述
		stringBuffer.append("").append(",");
		//摄像机编号
		stringBuffer.append(nodeValue2).append(",");
		//摄像机状态
		stringBuffer.append(textContent2).append(",");
		//摄像机状态描述
		stringBuffer.append("").append(",");
		//触发设备编号
		stringBuffer.append(nodeValue3).append(",");
		//触发设备状态
		stringBuffer.append(textContent3).append(",");
		//触发设备状态描述
		stringBuffer.append("").append(",");
		//补光设备编号
		stringBuffer.append(nodeValue4).append(",");
		//补光设备状态
		stringBuffer.append(textContent4).append(",");
		//补光设备状态描述
		stringBuffer.append("").append(",");
		//上一次通信是否正常
		stringBuffer.append(item20.getTextContent()).append(",");
		//文件大小
		stringBuffer.append("").append(",");
		//日志文件创建时间
		stringBuffer.append("").append(",");
		//日志文件分析时间
		stringBuffer.append("").append(",");
		//接收服务器IP
		stringBuffer.append("").append(",");
		//备注信息
		stringBuffer.append(item21.getTextContent());

		return new DataFormatResult<Boolean, String, String, String>( true , stringBuffer.toString() , "xml解析成功",PropertiesUtil.getName("LOG_TOPIC"));
	}
	/**
	 * 标准化外场设备日志数据文件
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, String, String, String>  formatDeviceLog(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		Node item10 = childNodes3.item(3);
		Node item11 = childNodes3.item(4);
		Node item12 = childNodes3.item(5);
		Node item13 = childNodes3.item(6);
		Node item14 = childNodes3.item(7);
		Node item15 = childNodes3.item(8);
		Node item16 = childNodes3.item(9);
		
		Node item17 = childNodes3.item(10);
		Node item18 = childNodes3.item(11);
		Node item19 = childNodes3.item(12);
		Node item20 = childNodes3.item(13);
		Node item21 = childNodes3.item(14);
		
		String nodeValue="";
		String textContent="";
		if(item16.hasChildNodes()){
			NodeList childNodes4 = item16.getChildNodes();
			Node item22 = childNodes4.item(0);
			textContent = item22.getTextContent();
			NamedNodeMap attributes = item22.getAttributes();
			Node namedItem = attributes.getNamedItem("id");
			nodeValue = namedItem.getNodeValue();
		}
		
		String nodeValue2="";
		String textContent2="";
		if(item17.hasChildNodes()){
			NodeList childNodes5 = item17.getChildNodes();
			Node item23 = childNodes5.item(0);
			NamedNodeMap attributes2 = item23.getAttributes();
			Node namedItem2 = attributes2.getNamedItem("id");

			nodeValue2 = namedItem2.getNodeValue();
			textContent2 =item23.getTextContent();
		}
		
		String nodeValue3="";
		String textContent3="";
		if(item18.hasChildNodes()){
			
			NodeList childNodes6 = item18.getChildNodes();
			Node item24 = childNodes6.item(0);
			NamedNodeMap attributes3 = item24.getAttributes();
			Node namedItem3 = attributes3.getNamedItem("id");
			
			nodeValue3 = namedItem3.getNodeValue();
			textContent3 =item24.getTextContent();
		}

		String nodeValue4="";
		String textContent4="";
		if(item19.hasChildNodes()){
			NodeList childNodes7 = item19.getChildNodes();
			Node item25 = childNodes7.item(0);
			NamedNodeMap attributes4 = item25.getAttributes();
			Node namedItem4 = attributes4.getNamedItem("id");
			
			nodeValue3 = namedItem4.getNodeValue();
			textContent3 =item25.getTextContent();
		}
		
		StringBuffer stringBuffer=new StringBuffer();
		
		//日志文件名称
		stringBuffer.append("").append(",");
		//设备自编号
		stringBuffer.append(item7.getTextContent()).append(",");
		//电警设备编号
		stringBuffer.append("").append(",");
		//记录生成时间
		stringBuffer.append(item8.getTextContent()).append(",");
		//IP地址
		stringBuffer.append(item9.getTextContent()).append(",");
		//CPU温度
		stringBuffer.append(item10.getTextContent()).append(",");
		//CPU风扇速度
		stringBuffer.append(item11.getTextContent()).append(",");
		//剩余物理内存
		stringBuffer.append(item12.getTextContent()).append(",");
		//外场设备线程计数
		stringBuffer.append(item13.getTextContent()).append(",");
		//磁盘空间
		stringBuffer.append(item14.getTextContent()).append(",");
		//未上传违法数据量
		stringBuffer.append(item15.getTextContent()).append(",");
		//相机编号
		stringBuffer.append(nodeValue).append(",");
		//相机状态
		stringBuffer.append(textContent).append(",");
		//相机状态描述
		stringBuffer.append("").append(",");
		//摄像机编号
		stringBuffer.append(nodeValue2).append(",");
		//摄像机状态
		stringBuffer.append(textContent2).append(",");
		//摄像机状态描述
		stringBuffer.append("").append(",");
		//触发设备编号
		stringBuffer.append(nodeValue3).append(",");
		//触发设备状态
		stringBuffer.append(textContent3).append(",");
		//触发设备状态描述
		stringBuffer.append("").append(",");
		//补光设备编号
		stringBuffer.append(nodeValue4).append(",");
		//补光设备状态
		stringBuffer.append(textContent4).append(",");
		//补光设备状态描述
		stringBuffer.append("").append(",");
		//上一次通信是否正常
		stringBuffer.append(item20.getTextContent()).append(",");
		//文件大小
		stringBuffer.append("").append(",");
		//日志文件创建时间
		stringBuffer.append("").append(",");
		//日志文件分析时间
		stringBuffer.append("").append(",");
		//接收服务器IP
		stringBuffer.append("").append(",");
		//备注信息
		stringBuffer.append(item21.getTextContent());
		return new DataFormatResult<Boolean, String, String, String>( true , stringBuffer.toString() , "xml解析成功",PropertiesUtil.getName("LOG_TOPIC"));
	}
	/**
	 * 标准化前端检测主机运行日志
	 * @param document
	 * @return
	 */
	public static DataFormatResult<Boolean, List<String>, String, String>  formatDeviceOperationLog(Document document){
		NodeList nodeList = document.getElementsByTagName("Message");
		Node item = nodeList.item(0);
		NodeList childNodes = item.getChildNodes();
		Node item2 = childNodes.item(0);
		Node item6 = childNodes.item(1);
		NodeList childNodes2 = item2.getChildNodes();
		NodeList childNodes3 = item6.getChildNodes();
		//MessageHeader
		Node item3 = childNodes2.item(0);
		Node item4 = childNodes2.item(1);
		Node item5 = childNodes2.item(2);
		//MessageBody
		Node item7 = childNodes3.item(0);
		Node item8 = childNodes3.item(1);
		Node item9 = childNodes3.item(2);
		
		NamedNodeMap attributes = item9.getAttributes();
		Node namedItem = attributes.getNamedItem("num");
		String nodeValue = namedItem.getNodeValue();
		
	    NodeList childNodes4 = item9.getChildNodes();
		List list=new ArrayList();
		for(int i=0;i<Integer.parseInt(nodeValue);i++) {
			Node item10 = childNodes4.item(i);
			NodeList childNodes5 = item10.getChildNodes();
			Node item11 = childNodes5.item(0);
			Node item12 = childNodes5.item(1);
			
			StringBuffer stringBuffer=new StringBuffer();
			
			//Version（2.0）
			stringBuffer.append(item3.getTextContent()).append(",");
			//A公司
			stringBuffer.append(item4.getTextContent()).append(",");
			//业务系统代码
			stringBuffer.append(item5.getTextContent()).append(",");
			//设备编码
			stringBuffer.append(item7.getTextContent()).append(",");
			//事件日志文件的生成时间
			stringBuffer.append(item8.getTextContent()).append(",");
			//记录数
			stringBuffer.append(nodeValue).append(",");
			//事件日志的发生时间
			stringBuffer.append(item11.getTextContent()).append(",");
			//对事件日志的描述信息	
			stringBuffer.append(item12.getTextContent());
			
			list.add(stringBuffer.toString());
		}
		
		return new DataFormatResult<Boolean, List<String>, String, String>( true , list , "xml解析成功","");
		
	}
}


