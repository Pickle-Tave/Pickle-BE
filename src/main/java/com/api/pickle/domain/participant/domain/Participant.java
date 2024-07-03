package com.api.pickle.domain.participant.domain;

import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Enumerated(EnumType.STRING)
    private HostStatus hostStatus;

    @Builder
    public Participant(Album album, Member member, HostStatus hostStatus){
        this.album = album;
        this.member = member;
        this.hostStatus = hostStatus;
    }

    public static Participant createHostParticipant(Album album, Member member){
        return Participant.builder()
                .album(album)
                .member(member)
                .hostStatus(HostStatus.HOST)
                .build();
    }

    public static Participant createGuestParticipant(Album album, Member member){
        return Participant.builder()
                .album(album)
                .member(member)
                .hostStatus(HostStatus.GUEST)
                .build();
    }
}
