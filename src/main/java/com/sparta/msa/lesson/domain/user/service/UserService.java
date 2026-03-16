package com.sparta.msa.lesson.domain.user.service;

import com.sparta.msa.lesson.domain.user.dto.request.UserRequest;
import com.sparta.msa.lesson.domain.user.dto.response.UserResponse;
import com.sparta.msa.lesson.domain.user.entity.User;
import com.sparta.msa.lesson.domain.user.mapper.UserMapper;
import com.sparta.msa.lesson.domain.user.repository.UserRepository;
import com.sparta.msa.lesson.global.exception.DomainException;
import com.sparta.msa.lesson.global.exception.DomainExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 의존성 주입

  @Transactional
  public UserResponse save(UserRequest request) {
    // 1. 이메일 중복 확인 (비즈니스 규칙)
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new DomainException(DomainExceptionCode.DUPLICATE_EMAIL);
    }

    // 2. 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 3. DTO를 Entity로 변환하여 DB에 저장
    User user = userMapper.toEntity(request, encodedPassword);
    User savedUser = userRepository.save(user);

    // 4. Entity를 Response DTO로 변환하여 Controller에 반환
    return userMapper.toResponse(savedUser);
  }
}
