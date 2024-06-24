package com.api.pickle.domain.auth.domain;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken{
    @Id
    private Long memberId;

    private String token;

    @TimeToLive
    private long ttl;

    @Builder
    public RefreshToken(Long memberId, String token, long ttl) {
        this.memberId = memberId;
        this.token = token;
        this.ttl = ttl;
    }

    public void updateRefreshToken(String newToken, long newTtl){
        this.token = newToken;
        this.ttl = newTtl;
    }
}
