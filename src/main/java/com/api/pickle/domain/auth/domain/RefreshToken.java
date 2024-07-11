package com.api.pickle.domain.auth.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
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
