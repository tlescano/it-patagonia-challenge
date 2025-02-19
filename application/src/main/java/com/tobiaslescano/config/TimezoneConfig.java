package com.tobiaslescano.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.TimeZone;

@Configuration
@Slf4j
public class TimezoneConfig {

    @Bean
    public TimeZone timeZone(){
        TimeZone defaultTimeZone = TimeZone.getTimeZone("GMT-3");
        TimeZone.setDefault(defaultTimeZone);
        log.info("Timezone set to {}. Example date: {}", defaultTimeZone.toZoneId(), new Date());
        return defaultTimeZone;
    }

}