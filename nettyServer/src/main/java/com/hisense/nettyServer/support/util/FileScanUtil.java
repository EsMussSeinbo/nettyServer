package com.hisense.nettyServer.support.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hisense.nettyServer.support.util.XML2String; 

/**
 * 递归扫描文件夹
 * @author qiush
 *
 */
public class FileScanUtil {
	public List<String> scanFiles = new ArrayList<String>();
	public List<String> scanFilesWithRecursion(String folderPath) {
		
		List<String> dirctorys = new ArrayList<String>();
		File directory = new File(folderPath);
//		if(!directory.isDirectory()){
//			//throw new ScanFilesException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
//		}
		if(directory.isDirectory()){
			File [] filelist = directory.listFiles();
			for(int i = 0; i < filelist.length; i ++){
				/**如果当前是文件夹，进入递归扫描文件夹**/
				if(filelist[i].isDirectory()){
					dirctorys.add(filelist[i].getAbsolutePath());
					/**递归扫描下面的文件夹**/
					scanFilesWithRecursion(filelist[i].getAbsolutePath());
				}
				/**非文件夹**/
				else{
					scanFiles.add(filelist[i].getAbsolutePath());
				}
			}
		}
		return scanFiles;
	}
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		new Runnable() {
			public void run() {
				Thread.currentThread();
			}
		};
		List<String> scanFilesWithRecursion = new FileScanUtil().scanFilesWithRecursion("D://FTPSite");
		for (String  string : scanFilesWithRecursion) {
		//	String s = XML2String.XMLToString(object.toString());
		//	System.out.println("ls:"+s.replaceAll("\n", ""));
        System.out.println(string);
		}
	}


}
