package com.hisense.nettyServer.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisense.nettyServer.dataformat.DataFormat;
import com.hisense.nettyServer.support.util.DataFormatResult;
import com.hisense.nettyServer.support.util.SingletonObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author qiu
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger("errorLog");

	Producer<String, String> producer = null;

	public NettyServerHandler(Producer<String, String> producer) {
		this.producer = producer;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取数据
		// ByteBuf buf = (ByteBuf) msg
		// byte[] req = new byte[buf.readableBytes()];

		// buf.readBytes(req);
		logger.debug("进入一次handler-----------------------");
		String message = (String)msg;
		byte[] bytes = message.getBytes();
		if(bytes.length<=0)
			return;
		if(bytes[0]==0x00){
			logger.debug("接收到的数据第一位是0x00,需要把字符串转化为二进制数组出掉0x00再转化为字符串");
			byte[] bytes2=new byte[bytes.length-1];
			System.arraycopy(bytes, 1, bytes2, 0, bytes.length-1);
			String f_line =new String(bytes2,"utf-8");
			if(f_line.contains("<")){
				logger.debug("该数据是正常数据,打印出来");
				logger.info("client data :" +"\n"+ f_line);
				message = f_line;
			}else{
				logger.debug("该数据是不是正常数据,直接丢弃");
				return;
			}
		}else{
			logger.debug("接收到的数据第一位不是0x00,无需转化");
			if(message.contains("<")){
				logger.debug("该数据是正常数据,打印出来");
				logger.info("client data :" +"\n"+ message);
			}else{
				logger.debug("该数据是不是正常数据,直接丢弃");
				return;
			}
		}
		if(message!=null){
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(message);
			message = m.replaceAll("");
		}
		if(!message.endsWith(">")){
			message = message +">";
		}
//		if(!message.endsWith(">")){
//			if(message.endsWith("\n")){
//				message = message.substring(0, message.length()-1);
//				if(!message.endsWith(">")){
//					message = message +">";
//				}
//			}else{
//				message = message +">";
//			}
//		}
		
		logger.debug("标准化结束位,标准化结果为:"+"\n"+message);
		// 判断是过车数据还是流量数据,在数据标准化
		DataFormat dataFormat = SingletonObject.getDataFormat();
		logger.debug("过车标准化对象"+dataFormat);
		logger.debug("是否为过车:"+message.contains("Type=\"" + "PlateInfo" + "\""));
		// 过车记录TD_VEHRECORD
		if (message.contains("Type=\"" + "PlateInfo" + "\"")) {
			logger.debug("进入过车if");
			DataFormatResult<Boolean, String, String, String> dataFormatResult //
					= dataFormat.dataFormatString(message.replaceAll("\n", ""));
			Boolean result = dataFormatResult.getResult();
			if (result) {
				// 数据标准化成功,写入kafka
				logger.error("过车数据标准化成功,标准化数据为:"+dataFormatResult.getData());
				String topic = dataFormatResult.getTopic();
				producer.send(new ProducerRecord<String, String>(topic, dataFormatResult.getData()));
			} else {
				// 数据标准化失败
				logger.info("过车数据标准化失败,原始数据为:"+message);
			}
		} else if (message.contains("Type=\"" + "TrafficMeasure" + "\"")) {
			// 交通流量TD_TRAFFIC
			//logger.info("client data :" + message);
			DataFormatResult<Boolean, List<String>, String, String> dataFormatResult1
					= dataFormat.dataFormatList(message.replaceAll("\n", ""));
			Boolean result = dataFormatResult1.getResult();
			if (result) {
				// 数据标准化成功,写入kafka
				String topic = dataFormatResult1.getTopic();
				List<String> data = dataFormatResult1.getData();
				for (String string : data) {
					logger.error("流量数据标准化成功,标准化数据为:"+string);
					producer.send(new ProducerRecord<String, String>(topic, string));
				}
			} else {
				// 数据标准化失败
				logger.info("流量数据标准化失败,原始数据为:"+message);
			}
		}

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();// 刷新后才将数据发出到SocketChannel
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
