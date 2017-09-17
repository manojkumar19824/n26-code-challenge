package com.mkm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class N26Orchestrator {

    public static void main(String[] args) {
        SpringApplication.run(N26Orchestrator.class, args);
    }
}
