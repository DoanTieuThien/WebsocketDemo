package com.its.web.socket.configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;

import com.its.web.socket.models.ChannelModel;
import com.its.web.socket.models.TextMessageModel;
import com.its.web.socket.thread.ManagerConnectionThread;
import com.its.web.socket.thread.ThreadAbstractBase;
import com.its.web.socket.thread.TransferDataThread;

@Configuration
public class AppConfiguration {

	@Bean("threadManager")
	public ConcurrentHashMap<Long, ThreadAbstractBase> threadManager() {
		ConcurrentHashMap<Long, ThreadAbstractBase> sessionManager = new ConcurrentHashMap<Long, ThreadAbstractBase>();
		
		ThreadAbstractBase thread1 = managerConnectioThread();
		thread1.setThreadName("managerConnectioThread");
		sessionManager.put(thread1.getThreadId(), thread1);
		ThreadAbstractBase thread2 = transferDataThread();
		thread1.setThreadName("transferDataThread");
		sessionManager.put(thread2.getThreadId(), thread1);
		return sessionManager;
	}
	
	@Bean("sessionManagerMap")
	public ConcurrentHashMap<String, ChannelModel> sessionManagerMap() {
		return new ConcurrentHashMap<String, ChannelModel>();
	}

	@Bean("managerConnectioThread")
	public ThreadAbstractBase managerConnectioThread() {
		ManagerConnectionThread managerConnectionThread = new ManagerConnectionThread();

		managerConnectionThread.start();
		return managerConnectionThread;
	}
	
	@Bean("transferDataThread")
	public ThreadAbstractBase transferDataThread() {
		TransferDataThread transferDataThread = new TransferDataThread();

		transferDataThread.start();
		return transferDataThread;
	}

	@Bean("queueNewConnection")
	public LinkedBlockingDeque<ChannelModel> queueNewConnection() {
		return new LinkedBlockingDeque<ChannelModel>();
	}
	
	@Bean("queueTransferEvenetData")
	public LinkedBlockingDeque<TextMessageModel> queueTransferEvenetData() {
		return new LinkedBlockingDeque<TextMessageModel>();
	}
}
