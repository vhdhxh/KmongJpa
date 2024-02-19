package com.talentmarket.KmongJpa.service;

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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

   private final LikeRepository likeRepository;
   private final BoardRepository boardRepository;
   private final UserRepository userRepository;

   //찜 카운터 갱신
    public void LikeCount(Long boardId , PrincipalDetails principalDetails) {
        // 중복카운트 불가, 카운트가 없다는것을 검증해야됨
       if(likeRepository.findLikesByBoardId(boardId).isPresent()){
          throw new CustomException(ErrorCode.COUNTED_LIKE);
       };

        Optional<Board> board = boardRepository.findById(boardId);
        Optional<Users> users = userRepository.findById(principalDetails.getDto().getId());
        Like like = Like.builder().board(board.get()).users(users.get()).build();
       likeRepository.save(like);
    }

    //찜 카운터 내림
}
