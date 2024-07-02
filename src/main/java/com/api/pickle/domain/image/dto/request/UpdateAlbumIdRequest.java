package com.api.pickle.domain.image.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UpdateAlbumIdRequest {
    private Long albumId;
    private List<Long> imageIds;
}
