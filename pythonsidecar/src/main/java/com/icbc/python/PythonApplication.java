package com.icbc.python;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
@EnableEurekaClient
@EnableDiscoveryClient
public class PythonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PythonApplication.class, args);
    }

}
