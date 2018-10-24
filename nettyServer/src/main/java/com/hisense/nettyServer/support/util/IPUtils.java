package com.hisense.nettyServer.support.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author qiush
 *
 */
public class IPUtils {
	
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getParameter("ip");
		if(StringUtils.isBlank(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		return ip;
	}
	
	public static String getLocalIp() {
		Collection noLoopbackAddresses = getAllNoLoopbackAddresses();
		if(noLoopbackAddresses.size()>0) {
			return noLoopbackAddresses.iterator().next().toString();
		} else {
			return "";
		}
	}
	
	public static Collection<InetAddress> getAllHostAddress() {
		Collection addresses = new ArrayList();
		try {
			Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
				Enumeration inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) inetAddresses
							.nextElement();
					addresses.add(inetAddress);
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return addresses;
	}
	
	public static Collection<String> getAllNoLoopbackAddresses() {
		Collection noLoopbackAddresses = new ArrayList();
		Collection<InetAddress> allInetAddresses = getAllHostAddress();

		for (InetAddress address : allInetAddresses) {
			if ((!address.isLoopbackAddress()) && ((address instanceof Inet4Address))) {
				noLoopbackAddresses.add(address.getHostAddress());
			}
		}

		return noLoopbackAddresses;
	}
	
	public static boolean isIp(String str) {
		String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";   
        Pattern pattern = Pattern.compile(ip);   
        Matcher matcher = pattern.matcher(str);   
        return matcher.matches();
	}
	public static void main(String[] args) {
		System.out.println(IPUtils.getLocalIp());
	}
}
