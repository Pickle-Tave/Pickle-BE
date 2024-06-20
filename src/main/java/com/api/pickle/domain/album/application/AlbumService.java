package com.api.pickle.domain.album.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ParticipantRepository participantRepository;
    private final MemberUtil memberUtil;

    public void createAlbum(String albumName){
        Member currentMember = memberUtil.getCurrentMember();
        Album newAlbum = Album.createPrivateAlbum(albumName);

        albumRepository.save(newAlbum);
        participantRepository.save(Participant.createHostParticipant(newAlbum, currentMember));
    }
}
