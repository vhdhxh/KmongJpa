package com.talentmarket.KmongJpa.service;


import com.talentmarket.KmongJpa.Dto.LikeResponse;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Board;
import com.talentmarket.KmongJpa.entity.Like;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.BoardRepository;
import com.talentmarket.KmongJpa.repository.LikeRepository;
import com.talentmarket.KmongJpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

   private final LikeRepository likeRepository;
   private final BoardRepository boardRepository;
   private final UserRepository userRepository;

   //찜 카운터 갱신
    public void likeCount(Long boardId , PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        // 중복카운트 불가, 카운트가 없다는것을 검증해야됨

        Long userId = principalDetails.getDto().getId();

       if(likeRepository.findLikesByBoardIdAndUsersId(boardId,userId).isPresent()){
          throw new CustomException(ErrorCode.COUNTED_LIKE);
       }

        Board board = boardRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));

       Users user = (principalDetails.getDto());
        Like like = Like.builder().board(board).users(user).build();
       likeRepository.save(like);
    }

    //찜 카운터 내림
    public void disLike(Long boardId , PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);

        if(likeRepository.findLikesByBoardIdAndUsersId(boardId,principalDetails.getDto().getId()).isEmpty()){
            throw new CustomException(ErrorCode.NOT_COUNTED_LIKE);
        }
        Long userId = principalDetails.getDto().getId();
        likeRepository.deleteByUsersIdAndBoardId(userId,boardId);
    }

    //찜 카운트 조회
    @Transactional(readOnly = true)
    public LikeResponse getLike (Long boardId) {
      List<Like> likes = likeRepository.findLikesByBoardId(boardId);
      Long likeCount = (long) likes.size();
      LikeResponse likeResponse = new LikeResponse(likeCount);
        return likeResponse ;
    }
}
