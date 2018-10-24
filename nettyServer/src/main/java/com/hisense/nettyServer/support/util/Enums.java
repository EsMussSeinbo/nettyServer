package com.hisense.nettyServer.support.util;

import java.util.Map;

import com.hisense.nettyServer.model.dao.TsDevice.TsDeviceMapper;
import com.hisense.nettyServer.model.entity.TsDevice;
import com.sun.tools.extcheck.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author liangshan
**/
public class Enums {
public static Map plateType;
public static Map carColor;
public static Map ipPath;
public static Map devciceNumToDeviceIp;
public static Map devciceNumToServiceIp;
public static Map ServiceIpTodevciceNum;
public Enums() {
	super();
	try{
	Map plateType=new HashMap();
	plateType.put("01","大型汽车");
	plateType.put("02","小型汽车");
	plateType.put("03","使馆汽车");
	plateType.put("04","领馆汽车");
	plateType.put("05","境外汽车");
	plateType.put("06","外籍汽车");
	plateType.put("07","两、三轮摩托车");
	plateType.put("08","轻便摩托车");
	plateType.put("09","使馆摩托车");
	plateType.put("10","领馆摩托车");
	plateType.put("11","境外摩托车");
	plateType.put("12","外籍摩托车");
	plateType.put("13","低速载货汽车");
	plateType.put("14","拖拉机");
	plateType.put("15","挂车");
	plateType.put("16","教练汽车");
	plateType.put("17","教练摩托车");
	plateType.put("18","试验汽车");
	plateType.put("19","试验摩托车");
	plateType.put("20","临时入境汽车");
	plateType.put("21","临时入境摩托车");
	plateType.put("22","临时行驶车");
	plateType.put("23","警用汽车");
	plateType.put("24","警用摩托");
	plateType.put("25","原农机号牌");
	plateType.put("26","香港汽车");
	plateType.put("27","澳门汽车");
	plateType.put("28","军队用大型汽车");
	plateType.put("29","军队用小型汽车");
	plateType.put("30","武警用大型汽车");
	plateType.put("31","武警用小型汽车");
	plateType.put("41","未识别");
	plateType.put("51","大型新能源汽车");
	plateType.put("52","小型新能源汽车");
	plateType.put("99","其他");
	Map carColor=new HashMap();
	carColor.put("A","白");
	carColor.put("B","灰");
	carColor.put("C","黄");
	carColor.put("D","粉");
	carColor.put("E","红");
	carColor.put("F","紫");
	carColor.put("G","绿");
	carColor.put("H","蓝");
	carColor.put("I","棕");
	carColor.put("J","黑");
	carColor.put("Z","其他");
	
	Map ipPath=new HashMap();
	ipPath.put("20.1.218.40","SF_ITS_YQ840");
	ipPath.put("20.1.218.38","SF_ITS_YQ838");
	ipPath.put("20.2.3.26","SF_ITS_YQ326");
	ipPath.put("20.1.218.41","SF_ITS_YQ841");
	ipPath.put("20.2.11.23","SF_ITS_ZH123");
	ipPath.put("20.2.11.22","SF_ITS_ZH122");
	ipPath.put("20.2.4.27","SF_ITS_YHL427");
	ipPath.put("20.2.4.25","SF_ITS_YQ425");
	ipPath.put("20.2.4.23","SF_ITS_YQ423");
	ipPath.put("20.2.4.22","SF_ITS_YQ422");
	ipPath.put("20.2.4.21","SF_ITS_YQ421");
	ipPath.put("20.2.3.27","SF_ITS_YQ327");
	ipPath.put("20.1.218.39","SF_ITS_YQ839");
	ipPath.put("20.2.3.25","SF_ITS_YQ325");
	ipPath.put("20.2.3.20","SF_ITS_YQ320");
	ipPath.put("20.2.4.24","SF_ITS_YQ424");
	ipPath.put("20.2.3.24","SF_ITS_YQ324");
	ipPath.put("20.2.3.23","SF_ITS_YQ323");
	ipPath.put("20.2.3.21","SF_ITS_YQ321");
	ipPath.put("20.2.3.22","SF_ITS_YQ322");
	ipPath.put("20.2.15.34","SF_ITS_TEST");
	
	Map devciceNumToDeviceIp=new HashMap();
	Map devciceNumToServiceIp=new HashMap();
	Map ServiceIpTodevciceNum=new HashMap();
	
	TsDeviceMapper tsDeviceMapper=SpringContextHolder.getBean(TsDeviceMapper.class);
	List<TsDevice> list =tsDeviceMapper.selectByPrimaryKey();
	
	List server840=new ArrayList<String>();
	List server323=new ArrayList<String>();
	List server325=new ArrayList<String>();
	List server424=new ArrayList<String>();
	List server321=new ArrayList<String>();
	List server425=new ArrayList<String>();
	List server841=new ArrayList<String>();
	List server040=new ArrayList<String>();
	List server351=new ArrayList<String>();
	List server422=new ArrayList<String>();
	List server421=new ArrayList<String>();
	List server320=new ArrayList<String>();
	List server322=new ArrayList<String>();
	List server838=new ArrayList<String>();
	List server326=new ArrayList<String>();
	List server133=new ArrayList<String>();
	List server134=new ArrayList<String>();
	List server426=new ArrayList<String>();
	List server334=new ArrayList<String>();
	List server327=new ArrayList<String>();
	List server324=new ArrayList<String>();
	List server427=new ArrayList<String>();
	List server423=new ArrayList<String>();
	List server132=new ArrayList<String>();
	List server839=new ArrayList<String>();
	
	for(TsDevice tsdevice:list){
		devciceNumToDeviceIp.put(tsdevice.getDeviceid(),tsdevice.getDeviceip());
		devciceNumToServiceIp.put(tsdevice.getDeviceid(),tsdevice.getInterfaceip());
		if("20.1.218.40".equals(tsdevice.getInterfaceip())){
			server840.add(tsdevice.getDeviceid());}
		else if("20.2.3.23".equals(tsdevice.getInterfaceip())){
			server323.add(tsdevice.getDeviceid());}
		else if("20.2.3.25".equals(tsdevice.getInterfaceip())){
			server325.add(tsdevice.getDeviceid());}
		else if("20.2.4.24".equals(tsdevice.getInterfaceip())){
			server424.add(tsdevice.getDeviceid());}
		else if("20.2.3.21".equals(tsdevice.getInterfaceip())){
			server321.add(tsdevice.getDeviceid());}
		else if("20.2.4.25".equals(tsdevice.getInterfaceip())){
			server425.add(tsdevice.getDeviceid());}
		else if("20.1.218.41".equals(tsdevice.getInterfaceip())){
			server841.add(tsdevice.getDeviceid());}
		else if("20.0.20.40".equals(tsdevice.getInterfaceip())){
			server040.add(tsdevice.getDeviceid());}
		else if("20.2.3.51".equals(tsdevice.getInterfaceip())){
			server351.add(tsdevice.getDeviceid());}
		else if("20.2.4.22".equals(tsdevice.getInterfaceip())){
			server422.add(tsdevice.getDeviceid());}
		else if("20.2.4.21".equals(tsdevice.getInterfaceip())){
			server421.add(tsdevice.getDeviceid());}
		else if("20.2.3.20".equals(tsdevice.getInterfaceip())){
			server320.add(tsdevice.getDeviceid());}
		else if("20.2.3.22".equals(tsdevice.getInterfaceip())){
			server322.add(tsdevice.getDeviceid());}
		else if("20.1.218.38".equals(tsdevice.getInterfaceip())){
			server838.add(tsdevice.getDeviceid());}
		else if("20.2.3.26".equals(tsdevice.getInterfaceip())){
			server326.add(tsdevice.getDeviceid());}
		else if("20.3.33.133".equals(tsdevice.getInterfaceip())){
			server133.add(tsdevice.getDeviceid());}
		else if("20.3.33.134".equals(tsdevice.getInterfaceip())){
			server134.add(tsdevice.getDeviceid());}
		else if("20.2.4.26".equals(tsdevice.getInterfaceip())){
			server426.add(tsdevice.getDeviceid());}
		else if("20.2.3.34".equals(tsdevice.getInterfaceip())){
			server334.add(tsdevice.getDeviceid());}
		else if("20.2.3.27".equals(tsdevice.getInterfaceip())){
			server327.add(tsdevice.getDeviceid());}
		else if("20.2.3.24".equals(tsdevice.getInterfaceip())){
			server324.add(tsdevice.getDeviceid());}
		else if("20.2.4.27".equals(tsdevice.getInterfaceip())){
			server427.add(tsdevice.getDeviceid());}
		else if("20.2.4.23".equals(tsdevice.getInterfaceip())){
			server423.add(tsdevice.getDeviceid());}
		else if("20.3.33.132".equals(tsdevice.getInterfaceip())){
			server132.add(tsdevice.getDeviceid());}
		else if("20.1.218.39".equals(tsdevice.getInterfaceip())){
			server839.add(tsdevice.getDeviceid());}
	
	}
	ServiceIpTodevciceNum.put("20.1.218.40",server840);
    ServiceIpTodevciceNum.put("20.2.3.23"  ,server323);
    ServiceIpTodevciceNum.put("20.2.3.25"  ,server325);
    ServiceIpTodevciceNum.put("20.2.4.24"  ,server424);
    ServiceIpTodevciceNum.put("20.2.3.21"  ,server321);
    ServiceIpTodevciceNum.put("20.2.4.25"  ,server425);
    ServiceIpTodevciceNum.put("20.1.218.41",server841);
    ServiceIpTodevciceNum.put("20.0.20.40" ,server040);
    ServiceIpTodevciceNum.put("20.2.3.51"  ,server351);
    ServiceIpTodevciceNum.put("20.2.4.22"  ,server422);
    ServiceIpTodevciceNum.put("20.2.4.21"  ,server421);
    ServiceIpTodevciceNum.put("20.2.3.20"  ,server320);
    ServiceIpTodevciceNum.put("20.2.3.22"  ,server322);
    ServiceIpTodevciceNum.put("20.1.218.38",server838);
    ServiceIpTodevciceNum.put("20.2.3.26"  ,server326);
    ServiceIpTodevciceNum.put("20.3.33.133",server133);
    ServiceIpTodevciceNum.put("20.3.33.134",server134);
    ServiceIpTodevciceNum.put("20.2.4.26"  ,server426);
    ServiceIpTodevciceNum.put("20.2.3.34"  ,server334);
    ServiceIpTodevciceNum.put("20.2.3.27"  ,server327);
    ServiceIpTodevciceNum.put("20.2.3.24"  ,server324);
    ServiceIpTodevciceNum.put("20.2.4.27"  ,server427);
    ServiceIpTodevciceNum.put("20.2.4.23"  ,server423);
    ServiceIpTodevciceNum.put("20.3.33.132",server132);
    ServiceIpTodevciceNum.put("20.1.218.39",server839);
    
	this.ServiceIpTodevciceNum=ServiceIpTodevciceNum;
	this.devciceNumToDeviceIp=devciceNumToDeviceIp;
	this.devciceNumToServiceIp=devciceNumToServiceIp;
	this.ipPath=ipPath;
	this.carColor=carColor;
	this.plateType=plateType;
	}catch (Exception e) {
		e.printStackTrace();
	}
}
 public static void main(String[] args) {

	List<String> list=(List<String>) new Enums().ServiceIpTodevciceNum.get("20.2.4.27");
	for (String string :list){
		System.out.println(string);
	}
}
}

