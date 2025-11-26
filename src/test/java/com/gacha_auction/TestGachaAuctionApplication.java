package com.gacha_auction;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestGachaAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.from(GachaAuctionApplication::main).with(AbstractIntegrationTest.class).run(args);
    }

}
