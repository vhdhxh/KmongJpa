package com.talentmarket.KmongJpa.global.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.talentmarket.KmongJpa.global.cache.CacheType;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;


@Configuration
@EnableCaching
public class CacheConfig {

  @Autowired
  private MeterRegistry meterRegistry;
    @Bean
    public CacheManager cacheManager() {
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cache ->
                {
                    CaffeineCache caffeine = new CaffeineCache(cache.getCacheName(),
                                Caffeine.newBuilder().recordStats()
                                        .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                                        .maximumSize(cache.getMaximumSize())
                                        .build());
                    CaffeineCacheMetrics.monitor(meterRegistry,caffeine.getNativeCache(),cache.getCacheName());

                    return caffeine;
                }
                        )

                .collect(toList());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);





        return cacheManager;
    }
}
