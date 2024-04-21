package com.talentmarket.KmongJpa.like.application;


import com.talentmarket.KmongJpa.like.application.dto.LikeResponse;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.like.domain.Like;
import com.talentmarket.KmongJpa.like.repository.LikeRepository;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
   private final LikeRepository likeRepository;
   private final ItemRepository itemRepository;
   //사용자가 찜 을 누르는 행위를 해서 조회후 값이 없으면 likeCount, 있으면 disLike 메서드를 호출
    public void like(Long itemId , Users user) {
        Users.checkUserSession(user);
        Long userId = user.getId();
        if(likeRepository.findLikesByItemIdAndUsersId(itemId,userId).isPresent()){
            disLike(itemId,user);
            return;
        }
        likeCount(itemId,user);
    }

   //찜 카운터 갱신
    public void likeCount(Long itemId , Users user) {
        // 중복카운트 불가, 카운트가 없다는것을 검증해야됨
        Long userId = user.getId();
       if(likeRepository.findLikesByItemIdAndUsersId(itemId,userId).isPresent()){
          throw new CustomException(ErrorCode.COUNTED_LIKE);
       }
        Item item = itemRepository.findById(itemId).orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
        Like like = Like.builder().item(item).users(user).build();
       likeRepository.save(like);
    }

    //찜 카운터 내림
    public void disLike(Long itemId , Users user) {
       if(likeRepository.findLikesByItemIdAndUsersId(itemId,user.getId()).isEmpty()){
            throw new CustomException(ErrorCode.NOT_COUNTED_LIKE);
        }
        Long userId = user.getId();
        likeRepository.deleteByUsersIdAndItemId(userId,itemId);
    }

    //찜 카운트 조회
    @Transactional(readOnly = true)
    public LikeResponse getLike (Long itemId) {
      List<Like> likes = likeRepository.findLikesByItemId(itemId);
      Long likeCount = (long) likes.size();
      LikeResponse likeResponse = new LikeResponse(likeCount);
        return likeResponse ;
    }
}
