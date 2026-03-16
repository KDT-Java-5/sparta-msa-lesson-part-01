package com.sparta.msa.lesson.domain.order.service;

import com.sparta.msa.lesson.domain.order.repository.OrderRepository;
import com.sparta.msa.lesson.domain.product.repository.ProductRepository;
import com.sparta.msa.lesson.domain.user.entity.User;
import com.sparta.msa.lesson.domain.user.repository.UserRepository;
import com.sparta.msa.lesson.global.exception.DomainException;
import com.sparta.msa.lesson.global.exception.DomainExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Transactional
  public void create(Long userId, Long productId, int quantity) {
    // 1. 다른 도메인(User)의 데이터를 가져오기 위해 주입받은 서비스를 사용
    User user = getUserById(userId); // 주문하는 유저는 반드시 존재해야 하므로 'get' 사용

    // 2. User 권한에 따른 로직 처리
    if (user.getEmail().equals("admin")) {
      // 어드민 계정 로직...
    }

    // 3. 재고 차감 및 주문 생성 로직...
    // ...
  }

  protected User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));
  }

}