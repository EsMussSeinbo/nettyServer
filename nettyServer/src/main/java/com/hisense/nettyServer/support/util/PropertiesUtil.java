package com.hisense.nettyServer.support.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
 
/**
 * 配置文件工具类
 * @author liangshan
 *
 */
public class PropertiesUtil {
	private static File  basePathFile  = new File(PropertiesUtil.class.getResource("/").getPath());
  private static String basePath =basePathFile.getPath()  + "/config.properties";
  private static String name = "";
  private static String nickname = "";
  private static String password = "";
  private static Properties pro =new Properties();
  static{
	  try {
		pro.load(new FileInputStream(new File(new File(PropertiesUtil.class.getResource("/").getPath())+ "/config.properties")));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  
  public static String getName(String paramsName) {
//	    try {
//	      Properties prop = new Properties();
//	      InputStream is = new FileInputStream(basePath);
//	      prop.load(is);
//	      return prop.getProperty(paramsName);
//	    } catch (FileNotFoundException e) {
//	      e.printStackTrace();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    return null;
	    return pro.getProperty(paramsName);
	  }
  /**
   * 一、 使用java.util.Properties类的load(InputStream in)方法加载properties文件
   * 
   */
  public static String getName1() {
    try {
      Properties prop = new Properties();
      InputStream is = new FileInputStream(basePath);
      prop.load(is);
      name = prop.getProperty("LOG_TOPIC");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return name;
  }
 
  /**
   * 二、 使用class变量的getResourceAsStream()方法
   * 注意：getResourceAsStream()读取路径是与本类的同一包下
   * 
   */
  public static String getName2() {
    Properties prop = new Properties();
    InputStream is = PropertiesUtil.class
        .getResourceAsStream("/com/util/prop.properties");
    try {
      prop.load(is);
      name = prop.getProperty("username");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return name;
  }
 
  /**
   * 三、
   * 使用class.getClassLoader()所得到的java.lang.ClassLoader的getResourceAsStream()方法
   * getResourceAsStream(name)方法的参数必须是包路径+文件名+.后缀 否则会报空指针异常
   * 
   */
  public static String getName3() {
    Properties prop = new Properties();
    InputStream is = PropertiesUtil.class.getClassLoader()
        .getResourceAsStream("com/util/prop.properties");
    try {
      prop.load(is);
 
    } catch (IOException e) {
      e.printStackTrace();
    }
    return name;
  }
 
  /**
   * 四、 使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法
   * getSystemResourceAsStream()方法的参数格式也是有固定要求的
   * 
   */
  public static String getName4() {
    Properties prop = new Properties();
    InputStream is = ClassLoader
        .getSystemResourceAsStream("com/util/prop.properties");
    try {
      prop.load(is);
      name = prop.getProperty("username");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return name;
  }
 
  /**
   * 五、 使用java.util.ResourceBundle类的getBundle()方法
   * 注意：这个getBundle()方法的参数只能写成包路径+properties文件名，否则将抛异常
   * 
   */
  public static String getName5() {
    ResourceBundle rb = ResourceBundle.getBundle("com/util/prop");
    password = rb.getString("password");
    return password;
  }
 
  /**
   * 六、 使用java.util.PropertyResourceBundle类的构造函数
   * 
   */
  public static String getName6() {
    try {
      InputStream is = new FileInputStream(basePath);
      ResourceBundle rb = new PropertyResourceBundle(is);
      nickname = rb.getString("nickname");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
 
    return nickname;
  }
 
  /**
   * 测试
   * 
   */
  public static void main(String[] args) {
    System.out.println("name1:" + PropertiesUtil.getName("masterFtpUsr"));
//    System.out.println("name2:" + PropertiesUtil.getName2());
//    System.out.println("name3:" + PropertiesUtil.getName3());
//    System.out.println("name4:" + PropertiesUtil.getName4());
//    System.out.println("password:" + PropertiesUtil.getName5());
//    System.out.println("nickname:" + PropertiesUtil.getName6());
  }
}
