package com.sparta.msa.lesson.domain.order.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

  Long orderId; // Entity의 'id'와 이름이 다름

  String username;   // Entity의 'user.username'에서 가져와야 함

  BigDecimal totalPrice;

}
