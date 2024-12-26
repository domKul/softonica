package com.softonicabackend;

import com.softonicabackend.infrastructure.security.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SoftonicaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftonicaBackendApplication.class, args);
	}

}
