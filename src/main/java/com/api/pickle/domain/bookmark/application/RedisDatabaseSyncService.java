package com.api.pickle.domain.bookmark.application;

import com.api.pickle.domain.bookmark.dao.BookmarkRepository;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.domain.bookmark.domain.MarkStatus;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.infra.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

import static com.api.pickle.global.common.constants.RedisConstants.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisDatabaseSyncService {
    private final RedisService redisService;
    private final BookmarkRepository bookmarkRepository;


    @Scheduled(fixedRate = DATABASE_SYNC_CYCLE)
    @Transactional
    public void syncRedsToDatabase(){
        Set<String> memberIds = redisService.getKeysByPattern(generateBookmarkWildcard());

        for (String stringedMemberId : memberIds){
            Map<Object, Object> hash = redisService.getHash(stringedMemberId);
            for (Map.Entry<Object, Object> entry : hash.entrySet()){
                Long bookmarkId = Long.parseLong(entry.getKey().toString());
                Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                        .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));
                bookmark.updateMarkStatus(MarkStatus.valueOf(entry.getValue().toString()));
                bookmarkRepository.save(bookmark);
            }
        }

        redisService.deleteKeysByPattern(generateBookmarkWildcard());
    }

    private String generateBookmarkWildcard(){
        return BOOKMARK_MEMBER_ID_KEY.concat(WILD_CARD);
    }
}
