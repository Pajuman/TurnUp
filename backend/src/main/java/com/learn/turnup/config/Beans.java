package com.learn.turnup.config;

import com.deepl.api.DeepLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public DeepLClient deeplClient(@Value("${deepl.api.key}") String apiKey) {
        return new DeepLClient(apiKey);
    }
}
