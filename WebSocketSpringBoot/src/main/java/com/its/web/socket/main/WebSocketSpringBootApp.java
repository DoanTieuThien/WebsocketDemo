package com.its.web.socket.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tuannx2
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.its.web.*")
public class WebSocketSpringBootApp {
	public static void main(String[] args) {
		SpringApplication.run(WebSocketSpringBootApp.class, "");
	}
}
