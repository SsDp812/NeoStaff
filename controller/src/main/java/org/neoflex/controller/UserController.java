package org.neoflex.controller;


import org.neoflex.business.UserService;
import org.neoflex.common.exceptions.user.UserNotFoundException;
import org.neoflex.dto.response.user.UserCardDto;
import org.neoflex.dto.response.user.UserPreviewCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserPreviewCardDto> getAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserCardDto getUser(@RequestParam Long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }
}
