package com.api.pickle.domain.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClassifiedImageResponse {

    private List<List<String>> groupedImages;
}
