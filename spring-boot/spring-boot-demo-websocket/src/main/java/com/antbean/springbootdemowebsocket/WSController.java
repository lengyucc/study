package com.antbean.springbootdemowebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {

	@MessageMapping("/welcome") // 接收浏览器发来的消息,类似RequestMapping
	@SendTo("/topic/getResponse") // 当服务端有消息时,会对订阅了@SendTo中的路径的浏览器发送消息
	public SMessage say(BMessage bMessage) throws InterruptedException {
		Thread.sleep(3000);
		return new SMessage("Hello, " + bMessage.getName());
	}
}
