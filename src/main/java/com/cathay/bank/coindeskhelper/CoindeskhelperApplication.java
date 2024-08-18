package com.cathay.bank.coindeskhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:coindeskhelper.properties", "application.properties" })
public class CoindeskhelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoindeskhelperApplication.class, args);
	}

}
