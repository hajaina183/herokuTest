package com.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class Config {
	private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void keepAwake() {
        String pingUrl = "https://hajaina-11.herokuapp.com/ping";
        String result = restTemplate.getForObject(pingUrl, String.class);
        System.out.println(result);
    }
}
