package com.hisense.nettyServer.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisense.nettyServer.support.util.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author qiu 
**/
public class NettyServerDecoder extends ByteToMessageDecoder{

	private static Logger logger = LoggerFactory.getLogger("errorLog");
	
	//拆包标志位
	private boolean chaibao = false;
	//粘包标志位
	private boolean zhanbao = false;
	
	List buff = new ArrayList();
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		ByteBufToBytes read = new ByteBufToBytes();
        byte[] bytes = read.read(in);
//        String ss = new String(bytes,"utf-8");
//        logger.debug("压力测试字符串:"+ss);
//        Object obj = ByteObjConverter.byteToObject(bytes);
//        out.add(obj);

        //起始标志位,固定为0X01,1Byte
        //消息长度,实际消息内容长度(按最低位字节的顺序存放,little-endian)
        //消息内容,实际消息(以XML格式表示)
        //结束标志位:0X00
        //数据有粘包现象,需要粘包拆包
        
        //首先判断各各标志位
        if(chaibao){
        	logger.error("上条数据是拆包数据");
        	for(int i =0; i< bytes.length;i++){
        		if(bytes[i]!=0x00){
        			buff.add(bytes[i]);
        		}else{
        			byte[] bytes3=new byte[buff.size()];
        			for(int j=0;j<buff.size();j++){
        				bytes3[j] = (byte) buff.get(j);
        			}
        			buff.clear();
        			chaibao=false;
        			logger.debug("粘数据完成,清除集合数据,标志位复位成false,并发送数据出去");
        			String f_line =new String(bytes3,"utf-8");
			        out.add(f_line);
        		}
        	}
        }else{
        	logger.debug("上条数据不是拆包数据");
        }
        
        for(int i =0; i < bytes.length; i++){
        	if(bytes[i] == 0x01){
				logger.debug("找到开始标志位,下标为:"+i);
				if(bytes.length-i-1>=4){
					logger.debug("判断之后数组长度达到够4个字节以上,用来判断内容长度");
					
					int j = (int)(bytes[i+1]& 0xFF) + ((int)(bytes[i+2]& 0xFF)) * 256
			        		+((int)(bytes[i+3]& 0xFF)) * 256*256+((int)(bytes[i+4]& 0xFF)) * 256*256*256;
					logger.debug("数据长度为:"+j);
					if(bytes.length-i-5<j){
						logger.debug("剩余长度为:"+(bytes.length-i-5)+",没有达到数组长度,说明拆包了,保留该部分数据设置拆包标志位,并跳出此次函数");
						chaibao = true;
						for(int k = i + 4;k<bytes.length;k++){
							buff.add(bytes[k]);
						}
						return;
					}else{
						logger.debug("包含了完整数据,复制到新的数组,并发送出去");
						byte[] bytes2=new byte[j];
						System.arraycopy(bytes, i+4, bytes2, 0, j);
						String f_line =new String(bytes2,"utf-8");
				        out.add(f_line);
				        //
					}
				}else{
					logger.debug("判断之后数组长度没有达到够4个字节以上,无法判断内容长度,用临时变量存储");
					//
				}
				
			}
//        	if(i==bytes.length-1 && bytes[i] != 0x01){
//        		logger.debug("遍历完数组还是没有开始标志位,丢弃该数组");
//        		return;
//        	}
        }
        
//		byte[] bytes2=new byte[bytes.length-6];
//		System.arraycopy(bytes, 5, bytes2, 0, bytes.length-6);
//		String f_line =new String(bytes2,"utf-8");
//        out.add(f_line);
       
	}

}
