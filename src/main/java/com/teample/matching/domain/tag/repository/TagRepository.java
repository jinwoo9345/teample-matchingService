package com.teample.matching.domain.tag.repository;

import com.teample.matching.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // 태그 이름으로 기존 태그가 있는지 조회하기 위한 메서드
    Optional<Tag> findByTagName(String tagName);

    List<Tag> findByTagNameIn(List<String> distinctNames);
}
