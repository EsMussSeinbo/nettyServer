package com.hisense.nettyServer.test;

import java.util.Timer;
import java.util.TimerTask;

import com.hisense.nettyServer.support.job.DeviceLog;
import com.hisense.nettyServer.support.job.GeneralIllegal;
import com.hisense.nettyServer.support.job.OperaLog;
import com.hisense.nettyServer.support.job.TrafficAccident;

public class TestTimerTask {
public static void main(String[] args) {
	Timer t1 = new Timer();
	t1.schedule(new TimerTask() {
		public void run() {

System.out.println("i'm first Thread. ");
try {
	Thread.sleep(200000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		}
	}, 200, 1000); // start第一次删除时间，delay重复删除间隔时间
	t1.schedule(new TimerTask() {
		
		@Override
		public void run() {
			System.out.println("i'm second Thread. ");
			
		}
	}, 100, 1000);
}
}
