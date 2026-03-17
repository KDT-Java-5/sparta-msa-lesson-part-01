package com.sparta.msa.lesson.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

  String name;

  BigDecimal price;

  Integer stock;

  @QueryProjection // QueryDSL은 이 생성자를 보고 QProductDTO를 생성합니다.
  public ProductDTO(String name, BigDecimal price, Integer stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }
}
