package com.teample.matching.domain.tag.service;

import com.teample.matching.domain.tag.domain.Tag;
import com.teample.matching.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> getOrCreateTags(List<String> tagNames) {
        // 1. 사용자가 "Java", "Java" 처럼 중복 입력했을 경우를 대비해 중복 제거
        List<String> distinctNames = tagNames.stream()
                .distinct()
                .collect(Collectors.toList());

        // 2. 이미 존재하는 태그들을 한 번의 쿼리(IN 절)로 가져옵니다.
        List<Tag> existingTags = tagRepository.findByTagNameIn(distinctNames);

        // 3. 빠른 비교를 위해 기존 태그 이름들을 Set으로 추출
        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getTagName)
                .collect(Collectors.toSet());

        // 4. 기존 DB에 없는 '새로운 태그'만 필터링하여 새 Tag 엔티티로 만듭니다.
        List<Tag> newTags = distinctNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(Tag::new)
                .collect(Collectors.toList());

        // 5. 새로운 태그가 있다면 한 번의 쿼리로 통째로 저장(Batch Insert)합니다.
        if (!newTags.isEmpty()) {
            tagRepository.saveAll(newTags);
            existingTags.addAll(newTags); // 최종 반환을 위해 기존 리스트에 추가
        }

        return existingTags;
    }
}