package com.sparta.msa.lesson.global.config;

import com.sparta.msa.lesson.domain.user.service.UserService;
import com.sparta.msa.lesson.domain.user.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public UserService userService() {
    return new UserServiceImpl();
  }
}
