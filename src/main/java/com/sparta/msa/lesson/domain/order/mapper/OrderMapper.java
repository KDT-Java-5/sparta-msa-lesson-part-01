package com.sparta.msa.lesson.domain.order.mapper;

import com.sparta.msa.lesson.domain.order.dto.response.OrderResponse;
import com.sparta.msa.lesson.domain.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  // 여러 개의 @Mapping을 동시에 사용할 수 있음
  @Mapping(target = "orderId", source = "id")// 이름이 다른 필드 매핑
  @Mapping(target = "username", source = "user.name")
  // 중첩 객체의 필드를 평탄화하여 매핑
  OrderResponse toResponse(Order order);

}