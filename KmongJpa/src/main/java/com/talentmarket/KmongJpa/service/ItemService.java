package com.talentmarket.KmongJpa.service;

import com.talentmarket.KmongJpa.Dto.BoardPagingResponse;
import com.talentmarket.KmongJpa.Dto.DetailResponse;
import com.talentmarket.KmongJpa.Dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Dto.WriteRequest;
import com.talentmarket.KmongJpa.config.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.entity.Item;
import com.talentmarket.KmongJpa.entity.itemcount;
import com.talentmarket.KmongJpa.entity.Users;
import com.talentmarket.KmongJpa.exception.CustomException;
import com.talentmarket.KmongJpa.exception.ErrorCode;
import com.talentmarket.KmongJpa.repository.ItemCountRepository;
import com.talentmarket.KmongJpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCountRepository itemCountRepository;

    //글쓰기
    @Transactional
    public Long WriteBoard(WriteRequest request, PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = Item.createBoard(request , principalDetails);
        return  itemRepository.save(item).getId();

    }
    //게시글 수정
    @Transactional
    public Long UpdateBoard(WriteRequest writeRequest,  Long boardId,PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = itemRepository.findById(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
         return item.updateBoard(writeRequest);
    }
    //게시글 삭제
    @Transactional
    public void DeleteBoard(Long boardId , PrincipalDetails principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = itemRepository.findById(boardId)
                .orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
        itemRepository.delete(item);
    }

    //게시글 상세보기
    @Transactional(readOnly = true)
    public DetailResponse DetailBoard(Long boardId) {

       Item item = itemRepository.findItemAndComment(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));

       return DetailResponse.ToDto(item);
    }

//    게시글 페이징 반환
    @Transactional(readOnly = true)
    public Page<BoardPagingResponse> DetailBoard(Pageable pageable) {
     Page<Item> boards = itemRepository.findItem(pageable);
        Page<BoardPagingResponse> map = boards.map(b->BoardPagingResponse.builder()
                .writer(b.getUsers().getName()).price(b.getPrice()).title(b.getTitle()).thumbnail(b.getThumbnail()).itemId(b.getId()).build());

        return map;
    }

    @Transactional(readOnly = true)
    public Page<ItemPaginationDto> DetailBoard2(Pageable pageable) {
         itemcount itemCount = itemCountRepository.findById(1L).get();
         Long total = itemCount.getItemcount();
       Page<ItemPaginationDto> items = itemRepository.paginationCoveringIndex(pageable , total);

        return items;

    }
    
}
