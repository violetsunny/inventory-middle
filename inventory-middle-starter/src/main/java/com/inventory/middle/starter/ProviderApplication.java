package com.inventory.middle.starter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 提供者服务启动类
 * @author yueweifeng
 */
@SpringBootApplication(scanBasePackages = {"top.kdla.framework" , "com.inventory.middle" })
@EnableFeignClients(basePackages = "com.inventory.middle")
@EnableScheduling
public class ProviderApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("inventory-middle Provider is running...");
    }
}
