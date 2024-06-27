package com.api.pickle.infra.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void putToBookmarkHash(String hashName, String key, Enum<?> value) {
        redisTemplate.opsForHash().put(hashName, key, value.name());
    }
}
