package com.sparta.msa.lesson.domain.order.dto.response;

import com.sparta.msa.lesson.global.constants.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

  Long id;

  BigDecimal totalPrice;

  OrderStatus status;

  LocalDateTime createdAt;

  OrderUserResponse user;

  List<OrderProductResponse> products;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class OrderUserResponse {

    Long id;

    String name;

  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class OrderProductResponse {

    Long id;

    String name;

    Integer quantity;

    BigDecimal price;

  }
}