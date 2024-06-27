package com.talentmarket.KmongJpa;

import java.util.concurrent.CompletableFuture;

public class main {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture cf = CompletableFuture.runAsync(()-> {
            System.out.println("cf");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        CompletableFuture cf2 = CompletableFuture.runAsync(()-> {
            System.out.println("cf2");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture cf3 = CompletableFuture.runAsync(()-> {
            System.out.println("cf3");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        cf.join();
        cf3.join();
        cf2.join();


    }
}
