package com.api.pickle.domain.tag.dao;

import com.api.pickle.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag,Long> {
    void deleteById(long tagId);
}
