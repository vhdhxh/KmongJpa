package com.talentmarket.KmongJpa.Item.application;

import com.talentmarket.KmongJpa.Item.application.dto.ItemPagingResponse;
import com.talentmarket.KmongJpa.Item.application.dto.DetailResponse;
import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.domain.Itemcount;
import com.talentmarket.KmongJpa.Item.repository.ItemCountRepository;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.global.auth.PrincipalDetails;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
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
        Long Id = itemRepository.save(item).getId();
        System.out.println(itemCountRepository.findById(1L).get().getItemcount());
        itemCountRepository.plusCount();
        System.out.println(itemCountRepository.findById(1L).get().getItemcount());


        return Id;

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
        itemCountRepository.minusCount();
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
    public Page<ItemPagingResponse> DetailBoard(Pageable pageable) {
     Page<Item> boards = itemRepository.findItem(pageable);
        Page<ItemPagingResponse> map = boards.map(b-> ItemPagingResponse.builder()
                .writer(b.getUsers().getName()).price(b.getPrice()).title(b.getTitle()).thumbnail(b.getThumbnail()).itemId(b.getId()).build());

        return map;
    }

    @Transactional(readOnly = true)
    public Page<ItemPaginationDto> DetailBoard2(Pageable pageable) {
         Itemcount itemCount = itemCountRepository.findById(1L).get();
         Long total = itemCount.getItemcount();
       Page<ItemPaginationDto> items = itemRepository.paginationCoveringIndex(pageable , total);
        System.out.println(itemRepository.getClass());

        return items;

    }
    
}
