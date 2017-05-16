package com.antbean.springbootdemowebsocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {
	
	// 通过SimpMessagingTemplate向浏览器发消息
	@Autowired
	private SimpMessagingTemplate messagingTemplate;	

	@MessageMapping("/chat")
	public void handleChat(Principal principal, String msg) throws InterruptedException {
		if (principal.getName().equals("lmh")) {
			messagingTemplate.convertAndSendToUser("admin", "/queue/notifications", principal.getName() + "-send:" + msg);
		}else{
			messagingTemplate.convertAndSendToUser("lmh", "/queue/notifications", principal.getName() + "-send:" + msg);
		}
	}

	@MessageMapping("/welcome") // 接收浏览器发来的消息,类似RequestMapping
	@SendTo("/topic/getResponse") // 当服务端有消息时,会对订阅了@SendTo中的路径的浏览器发送消息
	public SMessage say(BMessage bMessage) throws InterruptedException {
		Thread.sleep(3000);
		return new SMessage("Hello, " + bMessage.getName());
	}
}
