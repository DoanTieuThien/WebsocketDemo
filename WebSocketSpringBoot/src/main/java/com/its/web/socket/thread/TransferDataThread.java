package com.its.web.socket.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.its.web.socket.models.ChannelModel;
import com.its.web.socket.models.TextMessageModel;

public class TransferDataThread extends ThreadAbstractBase {

	private static final Logger log = LoggerFactory.getLogger(TransferDataThread.class);
	@Autowired
	@Qualifier("sessionManagerMap")
	public ConcurrentHashMap<String, ChannelModel> sessionManagerMap = null;
	@Autowired
	@Qualifier("queueTransferEvenetData")
	public LinkedBlockingDeque<TextMessageModel> queueTransferEvenetData = null;

	@Override
	public void init() throws Exception {

	}

	@Override
	public void processing() throws Exception {
		while (this.miThreadState != 0) {
			try {
				TextMessageModel textMessageModel = this.queueTransferEvenetData == null
						|| this.queueTransferEvenetData.isEmpty() ? null : this.queueTransferEvenetData.poll();

				if (textMessageModel != null) {
					String sesionId = textMessageModel.getChannelId() == null ? ""
							: textMessageModel.getChannelId().trim();

					if (sesionId.equals("")) {
						// public ban tin den tat ca cac kenh
						this.sessionManagerMap.forEach((channelId, channelModel) -> {
							if (channelModel.isOpen()) {
								channelModel.sendData(textMessageModel.getPayload());
							} else {
								channelModel.close();
							}
						});
					} else {
						// public for only one channel
						ChannelModel channelModel = this.sessionManagerMap.get(sesionId);

						if (channelModel == null) {
							log.info("Thread " + this.getThreadId() + ": channel not found");
						} else {
							channelModel.sendData(textMessageModel.getPayload());
						}
					}
					Thread.sleep(10);
					continue;
				}
			} catch (Exception exp) {
				log.error("Thread " + this.getThreadId() + " process error", exp);
			}
			Thread.sleep(100);
		}
	}

	@Override
	public void end() throws Exception {

	}

}
