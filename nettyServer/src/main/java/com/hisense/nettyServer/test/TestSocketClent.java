package com.hisense.nettyServer.test;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TestSocketClent {
	    public static final String IP_ADDR = "20.0.20.41";//服务器地址 
	    public static final int PORT = 12002;//服务器端口号  
	    
	    public static void main(String[] args) {  
	        System.out.println("客户端启动...");  
	        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n"); 
//	        while (true) {  
	            Socket socket = null;
	            try {
	                //创建一个流套接字并将其连接到指定主机上的指定端口号
	                socket = new Socket(IP_ADDR, PORT);  
	                  
	                //读取服务器端数据  
	              //  DataInputStream input = new DataInputStream(socket.getInputStream());  
	                //向服务器端发送数据  
//	                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
//	                System.out.print("请输入: \t");  
//	                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
//	                out.writeUTF(str);  
	                
	                
	                
	                
	        	//	String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Message><MessageHeader><Version>1.0</Version><From>VRoad</From><System /></MessageHeader><MessageBody Type=\"PlateInfo\"><DeviceId>3013-722003</DeviceId><EPDevId>510100000000A3KBZ7</EPDevId><Location>二环高架路内侧7km+128m处</Location><VehicleType>1</VehicleType><PlateNo>------0</PlateNo><PlateColor>2</PlateColor><Confidence>93.570000</Confidence><Direction>2B</Direction><LaneNo>1</LaneNo><Speed>44</Speed><VehKnd comment=\"车辆种类(0:大客车;1:小轿车;2:面包车;3:小货车;4:客车;5:货车)\">5</VehKnd><VehBrand /><VehColor comment=\"车辆颜色(0:其他(灰);1:白;2:灰;3:黑;4:红;5:紫;6:蓝;7:绿;8:黄;9:棕;10:粉)\">3</VehColor><VehNewEnerg comment=\"新能源标志(0:非新能源;1:新能源小车;2:新能源大车)\">0</VehNewEnerg><BigPlateNo comment=\"放大号牌\" /><CapTime>2017-12-28 10:00:37.586</CapTime></MessageBody></Message>";
	            //过车
	                String s="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Message><MessageHeader><Version>2.0</Version><From>厂商</From><System>业务系统代码</System></MessageHeader><MessageBody Type=\"PlateInfo\"><DeviceId>设备编码</DeviceId><EPDevId>设备编码</EPDevId><Location>设备位置描述</Location><VehicleType>车型</VehicleType><PlateNo>车牌号</PlateNo><PlateColor>号牌颜色</PlateColor><Confidence>置信度</Confidence><Direction>行驶方向</Direction><LaneNo>车道号</LaneNo><Speed>车速</Speed><VehKnd>车种</VehKnd><VehBrand>车标</VehBrand><VehColor>车身颜色</VehColor><VehNewEnerg>新能源标志</ VehNewEnerg ><BigPlateNo >放大号牌</ BigPlateNo ><CapTime>抓拍时间</CapTime></MessageBody></Message>";
	                int slen = s.getBytes().length;
	                
	                
	    			// Alert(slen);
	    			byte[] b = new byte[5];

	    			b[0] = 0x01;
	    			long signed = ((slen & 255) / 128) & 0x0FFFFFFFF;
	    			b[1] = (byte) ((slen & 255) - signed * 256);

	    			signed = (((slen & 65280) >> 8) / 128) & 0x0FFFFFFFF;
	    			b[2] = (byte) ((slen & 65280) >> 8 - signed * 256);
	    			// Alert(b[2]);
	    			signed = (((slen & 16711680) >> 16) / 128) & 0x0FFFFFFFF;
	    			b[3] = (byte) ((slen & 16711680) >> 16 - signed * 256);
	    			// Alert(b[3]);
	    			// long mask = 4278190080l;
	    			signed = (((slen & 4278190080L) >> 24) / 128) & 0x0FFFFFFFF;
	    			b[4] = (byte) ((slen & 4278190080L) >> 24 - signed * 256);
	    			// Alert(b[4]);
	    			// b[FileSize + 5] = 0x0;
	    			
	    			byte[] buf = new byte[(slen + 6)];
	    			System.arraycopy(b, 0, buf, 0, 5);
	    			System.arraycopy(s.getBytes(), 0, buf, 5, slen);

	    			buf[slen + 5] = 0;

	    		
	    			
	    			
	                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
	                
	            	bos.write(buf, 0, slen + 6);
	        		bos.flush();
	              //  String ret = input.readUTF();   
//	                System.out.println("服务器端返回过来的是: " + ret);  
//	                // 如接收到 "OK" 则断开连接  
//	                if ("OK".equals(ret)) {  
//	                    System.out.println("客户端将关闭连接");  
//	                    Thread.sleep(500);  
//	                    break;  
//	                }  
	                Thread.sleep(10000);
//	                out.close();
//	                input.close();
	            } catch (Exception e) {
	                System.out.println("客户端异常:" + e.getMessage()); 
	            } finally {
	                if (socket != null) {
	                    try {
	                        socket.close();
	                        System.out.println("socket is closed");
	                    } catch (IOException e) {
	                        socket = null; 
	                        System.out.println("客户端 finally 异常:" + e.getMessage()); 
	                    }
	                }
	            }
	        }  
	    }  
//	}  


