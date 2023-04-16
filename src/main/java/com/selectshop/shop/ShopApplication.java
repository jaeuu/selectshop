package com.selectshop.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // ** 스프링 부트에서 스케줄러가 작동하게 합니다.
@EnableJpaAuditing // **JPA를 이용해서 Timestamped를 사용할때는 시간 자동 변경이 가능하도록 @EnableJpaAuditing를 적어준다.
@SpringBootApplication // 스프링 부트임을 선언합니다.
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
