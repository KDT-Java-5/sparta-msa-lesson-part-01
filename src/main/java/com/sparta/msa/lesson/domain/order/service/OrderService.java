package com.sparta.msa.lesson.domain.order.service;

import com.sparta.msa.lesson.domain.order.dto.request.OrderRequest;
import com.sparta.msa.lesson.domain.order.dto.response.OrderResponse;
import com.sparta.msa.lesson.domain.order.entity.Order;
import com.sparta.msa.lesson.domain.order.entity.OrderProduct;
import com.sparta.msa.lesson.domain.order.repository.OrderProductRepository;
import com.sparta.msa.lesson.domain.order.repository.OrderRepository;
import com.sparta.msa.lesson.domain.product.entity.Product;
import com.sparta.msa.lesson.domain.product.repository.ProductRepository;
import com.sparta.msa.lesson.domain.user.entity.User;
import com.sparta.msa.lesson.domain.user.repository.UserRepository;
import com.sparta.msa.lesson.global.constants.enums.OrderStatus;
import com.sparta.msa.lesson.global.exception.DomainException;
import com.sparta.msa.lesson.global.exception.DomainExceptionCode;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderProductRepository orderProductRepository;

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

  private User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));
  }

  @Transactional
  public OrderResponse order(OrderRequest request) {

    // 1. 사용자 조회
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

    // 2. 상품 일괄 조회
    List<Long> productIds = request.getProducts().stream()
        .map(OrderRequest.OrderProductRequest::getId)
        .toList();

    List<Product> products = productRepository.findAllById(productIds);

    if (products.size() != productIds.size()) {
      throw new DomainException(DomainExceptionCode.NOT_FOUND_PRODUCT);
    }

    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));

    // 3. 재고 확인 및 감소 + 총 가격 계산
    BigDecimal totalPrice = request.getProducts().stream()
        .map(requestProduct -> {
          Product product = productMap.get(requestProduct.getId());

          if (product.getStock() < requestProduct.getQuantity()) {
            throw new DomainException(DomainExceptionCode.INSUFFICIENT_STOCK);
          }

          product.decreaseStock(requestProduct.getQuantity());

          return product.getPrice().multiply(new BigDecimal(requestProduct.getQuantity()));
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    // 4. Order 저장
    Order savedOrder = orderRepository.save(
        Order.builder()
            .user(user)
            .totalPrice(totalPrice)
            .status(OrderStatus.PENDING)
            .build()
    );

    // 5. OrderProduct 생성 및 일괄 저장
    List<OrderProduct> orderProducts = request.getProducts().stream()
        .map(requestProduct -> {
          Product product = productMap.get(requestProduct.getId());
          return OrderProduct.builder()
              .order(savedOrder)
              .product(product)
              .quantity(requestProduct.getQuantity())
              .price(product.getPrice())
              .build();
        })
        .toList();

    orderProductRepository.saveAll(orderProducts);

    // 6. 응답 반환
    return OrderResponse.builder()
        .id(savedOrder.getId())
        .totalPrice(savedOrder.getTotalPrice())
        .status(savedOrder.getStatus())
        .createdAt(savedOrder.getCreatedAt())
        .user(OrderResponse.OrderUserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .build())
        .products(orderProducts.stream()
            .map(op -> OrderResponse.OrderProductResponse.builder()
                .id(op.getProduct().getId())
                .name(op.getProduct().getName())
                .quantity(op.getQuantity())
                .price(op.getPrice())
                .build())
            .toList())
        .build();
  }

}