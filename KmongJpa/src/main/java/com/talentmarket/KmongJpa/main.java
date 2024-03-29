package com.talentmarket.KmongJpa;

public class main {
    public static void main(String[] args) {
        int sum = 0;
        for(int i = 1; i <= 1000 ; i++) {
            if (i%2==0)
                sum += i;
        }
        System.out.println(sum);
    }
}
