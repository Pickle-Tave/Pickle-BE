package com.api.pickle.domain.auth.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseTimeEntity {
    @Id
    private Long memberId;

    private String token;

    @Builder
    public RefreshToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public void updateRefreshToken(String newToken){
        this.token = newToken;
    }
}
