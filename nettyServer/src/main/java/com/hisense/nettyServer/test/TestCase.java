package com.hisense.nettyServer.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hisense.nettyServer.support.util.FtpDownLoadUtil;
import com.hisense.nettyServer.support.util.StringUtil;

import redis.clients.jedis.JedisCluster;

public class TestCase {

	public static void main(String[] args) throws IOException {
//		String utf = "UTF-8";
//		String ISO8859  = "ISO8859-1";
//		String gbk  = "GBK";
		
//		String s = ",,,,I,1345,3014-722012,510100000000A9KB13,二环高架路外侧0km+769m处,,,川A5BN36,,3,1,,3,1B,2,96.070000,27,0,120,,,1,/cifspool/SF_ITS_YQ839/VIOLATION_PIC/3014-722012/20180821/10/7ff59e75-47e4-4eb5-9fe7-f59f447234ef.jpg,7ff59e75-47e4-4eb5-9fe7-f59f447234ef.jpg,2018-08-21 10:31:39.473,,,,,,,,,,,,2018-08-21 10:31:41.873,,,1";
//		String ss ="2018-09-16 20:39:33";
//		System.out.println(StringUtil.parseDate(ss));
//		String sss = "Z_3014-243063_5_20181018031725762_川_1A328.XML";
//		System.out.println(sss.subSequence(24, 26));
//		FTPClient ftp = new FTPClient();
//		ftp.connect("20.2.35.3", 21);
//		ftp.login("SD123456", "SD159753");
//		ftp.changeWorkingDirectory("/WATCH/20181023/14/");
//		FTPFile[] fs = ftp.listFiles();
//		for (FTPFile ff : fs) {
//			System.out.println(new String(ff.getName().getBytes(ISO8859),gbk));
//		}
//		FTPClient ftp = new FTPClient();
//		ftp.connect("20.1.3.137", 21);
//		ftp.login("root", "its@Lpr~jtxxgP@ssword");
//		ftp.changeWorkingDirectory("/mnt/VideoData/REALTIME/20181023/06/");
//		FTPFile[] fs = ftp.listFiles();
//		for (FTPFile ff : fs) {
//		//	System.out.println(new String(ff.getName().getBytes(),"UTF-8"));
//			System.out.println(new String(ff.getName().getBytes(ISO8859),gbk));
//			
//		}
//		String s = "20181018031725760";
//		String ss = "20181018031725760";
//		String sss = "20181018031725765";
//		System.out.println(s.substring(0, 10));
//		System.out.println(s.compareTo(ss));
//		if(sss.compareTo(s)>1&sss.compareTo(ss)<1)
//			System.out.println("！！");;
		//20.2.35.3 21 SD123456SD159753/WATCH/20181023/14/Z_3014-722074_1_20181023140037365_22_川AK2688.XML

		//	FtpUtil.downFile("20.2.35.3", 21, "SD123456", "SD159753", "/WATCH/20181023/14/", "Z_3014-722074_1_20181023140037365_22_川AK2688.XML",
		//		"F:/FTPDTemp");
		FtpDownLoadUtil.downloadFtpFile("20.2.35.3", "SD123456", "SD159753", 21, "/WATCH/20181023/14/", "F:/FTPDTemp", "Z_3014-722074_1_20181023140037365_22_川AK2688.XML");
		
	}
	
}
