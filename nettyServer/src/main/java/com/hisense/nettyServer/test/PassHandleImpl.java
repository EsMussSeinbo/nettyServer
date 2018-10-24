package com.hisense.nettyServer.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PassHandleImpl {

	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(Integer.valueOf("500"), Integer.valueOf("500"), 0L,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	public static void main(String[] args) {
		PassHandleImpl handleImpl = new PassHandleImpl();
		for (int i = 0; i < 2; i++) {
			WorkerImpl work = new WorkerImpl();
			handleImpl.threadPool.submit(work);
		}
	}
}
