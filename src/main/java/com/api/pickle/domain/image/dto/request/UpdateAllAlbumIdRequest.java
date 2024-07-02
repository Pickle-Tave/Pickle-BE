package com.api.pickle.domain.image.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAllAlbumIdRequest {
    private List<UpdateAlbumIdRequest> updateAlbumIdRequestList;
}
