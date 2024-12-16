package com.example.likesystem.conf;

import com.example.likesystem.service.LikeService;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
public class CaffeineConfig {
    @Autowired
    private LikeService likeService;

    @Bean
    public LoadingCache<Integer, AtomicInteger> cacheManager() {
        LoadingCache<Integer, AtomicInteger> caffeine = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(10, TimeUnit.MILLISECONDS)
                .removalListener((Integer key, AtomicInteger value, RemovalCause removalCause)->{
                    likeService.updateLikeCount(key,value.get());
                })
                .build(k->new AtomicInteger(0));

        return caffeine;
    }



}
