package com.talentmarket.KmongJpa.Item.application;

import com.talentmarket.KmongJpa.Item.application.dto.ItemPagingResponse;
import com.talentmarket.KmongJpa.Item.application.dto.DetailResponse;
import com.talentmarket.KmongJpa.Item.application.dto.ItemPaginationDto;
import com.talentmarket.KmongJpa.Item.application.dto.WriteRequest;
import com.talentmarket.KmongJpa.Item.domain.Item;
import com.talentmarket.KmongJpa.Item.domain.ItemImage;
import com.talentmarket.KmongJpa.Item.domain.Itemcount;
import com.talentmarket.KmongJpa.Item.repository.ItemCountRepository;
import com.talentmarket.KmongJpa.Item.repository.ItemImageRepository;
import com.talentmarket.KmongJpa.Item.repository.ItemRepository;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCountRepository itemCountRepository;
    private final ItemImageRepository itemImageRepository;

    //글쓰기
    @Transactional
    public Long WriteBoard(WriteRequest request, Users principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = Item.createBoard(request , principalDetails);
        Long Id = itemRepository.save(item).getId();
        List<ItemImage> itemImages = request.getImages().stream()
                .map(imageName -> ItemImage.builder()
                        .item(item)
                        .imageName(imageName)
                        .build())
                .collect(Collectors.toList());
        itemImageRepository.saveAll(itemImages);
        itemCountRepository.plusCount();
        return Id;
    }
    //게시글 수정
    @Transactional
    public Long UpdateBoard(WriteRequest writeRequest, Long boardId, Users principalDetails) {
        Users.checkUserSession(principalDetails);
        Item item = itemRepository.findById(boardId)
               .orElseThrow(()->new CustomException(ErrorCode.ITEM_NOT_FOUND));
         return item.updateBoard(writeRequest);
    }
    //게시글 삭제
    @Transactional
    public void DeleteBoard(Long boardId , Users principalDetails) {
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
       List<ItemImage> itemImages= itemImageRepository.findByItemId(boardId);
       return DetailResponse.ToDto(item, itemImages);
    }

//    게시글 페이징 반환
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "items")
    public Page<ItemPagingResponse> DetailBoard(Pageable pageable) {
        System.out.println("DetailBoard");
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
