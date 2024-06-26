package com.api.pickle.domain.membertag.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import com.api.pickle.domain.tag.domain.Tag;
import com.api.pickle.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;


    @Builder
    private MemberTag(Member member , Tag tag) {
        this.member = member;
        this.tag = tag;
    }
    public static MemberTag createMemberTag(Member member, Tag tag) {
        return MemberTag.builder()
                .member(member)
                .tag(tag)
                .build();
    }
}
