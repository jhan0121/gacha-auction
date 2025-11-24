package com.gacha_auction;

import org.springframework.boot.SpringApplication;

public class TestGachaAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.from(GachaAuctionApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
