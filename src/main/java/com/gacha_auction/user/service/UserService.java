package com.gacha_auction.user.service;

import com.gacha_auction.exception.NotFoundException;
import com.gacha_auction.user.domain.User;
import com.gacha_auction.user.repository.UserRepository;
import com.gacha_auction.user.service.dto.input.FindUserInput;
import com.gacha_auction.user.service.dto.input.UserInput;
import com.gacha_auction.user.service.dto.output.FindUserOutput;
import com.gacha_auction.user.service.dto.output.UserOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserOutput save(final UserInput input) {
        final User user = input.toEntity();
        final User saved = userRepository.save(user);
        return UserOutput.from(saved);
    }

    @Transactional(readOnly = true)
    public FindUserOutput findById(final FindUserInput input) {
        final User user = userRepository.findById(input.id())
                .orElseThrow(() -> new NotFoundException("user not found"));
        return FindUserOutput.from(user);
    }
}
