package com.api.pickle.infra.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void putToBookmarkHash(String hashName, String key, Enum<?> value) {
        redisTemplate.opsForHash().put(hashName, key, value.name());
    }

    public Map<Object, Object> getHash(String hashName) {
        return redisTemplate.opsForHash().entries(hashName);
    }

    public Set<String> getKeysByPattern(String pattern){
        return redisTemplate.keys(pattern);
    }

    public void deleteKeysByPattern(String pattern){
        Set<String> keys = getKeysByPattern(pattern);
        if (!keys.isEmpty()){
            redisTemplate.delete(keys);
        }
    }
}
