package com.talentmarket.KmongJpa.sms.application;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListCodeRepository {
   private List<Code> codes = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private AtomicLong Id = new AtomicLong(1L);

   public void addCode(Code code) {
       codes.add(code);
       code.setId(Id.getAndAdd(1L));
       scheduler.schedule(() -> codes.remove(code), 3, TimeUnit.MINUTES);

   }

   public Code findById(Long id) {
   return codes.stream()
           .filter(code ->code.sameId(id))
           .findFirst()
           .orElseThrow(()->new RuntimeException("코드가 만료되었습니다"));
   }
}
