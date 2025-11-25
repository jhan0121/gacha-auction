package com.gacha_auction.user.controller;

import com.gacha_auction.user.controller.dto.request.UserRequest;
import com.gacha_auction.user.controller.dto.response.FindUserResponse;
import com.gacha_auction.user.controller.dto.response.UserResponse;
import com.gacha_auction.user.service.UserService;
import com.gacha_auction.user.service.dto.input.FindUserInput;
import com.gacha_auction.user.service.dto.input.UserInput;
import com.gacha_auction.user.service.dto.output.FindUserOutput;
import com.gacha_auction.user.service.dto.output.UserOutput;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<FindUserResponse> getUserInfo(@PathVariable("id") Long id) {
        final FindUserInput input = FindUserInput.from(id);
        final FindUserOutput output = userService.findById(input);
        FindUserResponse response = FindUserResponse.from(output);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody final UserRequest request) {
        final UserInput input = request.toInput();
        final UserOutput output = userService.save(input);
        final UserResponse response = UserResponse.from(output);
        return ResponseEntity.created(URI.create("/users/" + output.id())).body(response);
    }
}
