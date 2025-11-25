package com.gacha_auction.user.repository;

import com.gacha_auction.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
