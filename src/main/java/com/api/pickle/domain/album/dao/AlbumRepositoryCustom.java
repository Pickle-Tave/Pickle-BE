package com.api.pickle.domain.album.dao;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;

import java.util.List;

public interface AlbumRepositoryCustom {

    List<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(String keyword);

    List<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(String albumStatus);

    List<AlbumSearchResponse> findAllAlbumOfMemberByCreatedDateDesc(Long memberId);
}
