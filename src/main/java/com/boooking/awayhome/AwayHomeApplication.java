package com.boooking.awayhome;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwayHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwayHomeApplication.class, args);
		System.out.println("Server started");

	}

}
