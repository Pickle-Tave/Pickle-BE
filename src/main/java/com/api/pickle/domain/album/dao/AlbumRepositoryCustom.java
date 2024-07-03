package com.api.pickle.domain.album.dao;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface AlbumRepositoryCustom {

    Slice<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(Long memberId, String keyword, int pageSize, Long lastAlbumId);

    Slice<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(Long memberId, String albumStatus, int pageSize, Long lastAlbumId);

    Slice<AlbumSearchResponse> findAllAlbumOfMemberByCreatedDateDesc(Long memberId, int pageSize, Long lastAlbumId);
}
