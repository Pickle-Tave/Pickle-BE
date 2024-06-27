package com.api.pickle.domain.bookmark.domain;

import com.api.pickle.domain.participant.domain.Participant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Enumerated(EnumType.STRING)
    private MarkStatus markStatus;

    @Builder
    public Bookmark (Participant participant){
        this.participant = participant;
        this.markStatus = MarkStatus.UNMARKED;
    }

    public void updateMarkStatus(MarkStatus markStatus){
        this.markStatus = markStatus;
    }
}
