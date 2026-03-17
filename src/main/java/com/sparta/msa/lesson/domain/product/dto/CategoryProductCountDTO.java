package com.sparta.msa.lesson.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryProductCountDTO {

  String categoryName;
  
  Long productCount;

  @QueryProjection
  public CategoryProductCountDTO(String categoryName, Long productCount) {
    this.categoryName = categoryName;
    this.productCount = productCount;
  }
}
