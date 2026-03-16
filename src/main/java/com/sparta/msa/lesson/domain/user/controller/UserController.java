package com.sparta.msa.lesson.domain.user.controller;

import com.sparta.msa.lesson.domain.user.service.UserService;
import com.sparta.msa.lesson.domain.user.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  // private final UserService userService = new UserServiceImpl();

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public void save() {
    userService.save();
  }

}
