package com.hisense.nettyServer.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hisense.nettyServer.server.DataSupplementServer;
import redis.clients.jedis.JedisCluster;

/**
 * 数据补录
 * @author qiush
 *
 */
@Controller
@RequestMapping({ "/" })
public class DataSupplementController {
	
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	
	@Autowired
	private DataSupplementServer dataSupplementServer;
	
	@SuppressWarnings("resource")
	@RequestMapping(value = { "/DataSupplement" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String DataSupplement(HttpServletRequest request){
		//"0"这次服务执行完成    "1"上次服务没有执行完    "2"输入时间区间不对
		logger.debug("进入数据补录controller");
		//判断服务状态 
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("jedisCluster");
		String serverStatus = (String)jedisCluster.get("isServerStatus");
		if("0".equals(serverStatus)){
			//服务未执行,可调用
			String time1 = request.getParameter("time1").replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","");
			String time2 = request.getParameter("time2").replaceAll("-","").replaceAll(":","").replace(" ","").replace(".","");
			String deviceId = request.getParameter("deviceId");
			String dataType = request.getParameter("dataType");
			//20181018031725762
			//判断时间区间,时间区间单位为天数以内
			if(time1.substring(0, 6).equals(time2.substring(0, 6))){
				dataSupplementServer.dataSupplementServer(time1, time2, deviceId, dataType,jedisCluster);
				return "0";
			}
			return "2";
		}else{
			return "1";
		}
		
//		FtpUtil.downFile("20.2.35.3", 21, "SD123456", "SD159753", "/WATCH/20181023/04/", "Z_3014-722074_2_20181023040404367_22_--------.XML",
//				"/FTPDTemp");
//		return "0";
	}
}
