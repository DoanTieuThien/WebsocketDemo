package com.its.web.socket.controller;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.web.socket.models.ResponseModel;
import com.its.web.socket.models.TextMessageModel;

@RestController
@RequestMapping("/web-socket")
public class TestPublicMessageController {
	@Autowired
	@Qualifier("queueTransferEvenetData")
	public LinkedBlockingDeque<TextMessageModel> queueTransferEvenetData = null;

	@PostMapping("/public-message")
	public ResponseModel publicMessgae(@RequestBody TextMessageModel textMessage) {
		ResponseModel response = new ResponseModel();

		try {
			if (textMessage == null) {
				throw new Exception("No data input found");
			}
			if (textMessage.getPayload() == null) {
				throw new Exception("No message input found");
			}
			this.queueTransferEvenetData.push(textMessage);
			response.setCode("WSK-00000");
			response.setDes("SUCCESSED");
		} catch (Exception exp) {
			exp.printStackTrace();
			response.setCode("WSK-99999");
			response.setDes(exp.getMessage());
		}
		return response;
	}
}
