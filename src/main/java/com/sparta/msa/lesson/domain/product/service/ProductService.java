package com.sparta.msa.lesson.domain.product.service;

import com.sparta.msa.lesson.domain.product.dto.request.ProductRequest;
import com.sparta.msa.lesson.domain.product.dto.response.ProductResponse;
import com.sparta.msa.lesson.domain.product.entity.Product;
import com.sparta.msa.lesson.domain.product.repository.ProductRepository;
import com.sparta.msa.lesson.global.exception.DomainException;
import com.sparta.msa.lesson.global.exception.DomainExceptionCode;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductResponse> getAllProducts(){
    return List.of();
  }

  public ProductResponse getProductById(Long id){
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_PRODUCT));

    return ProductResponse.builder()
        .id(product.getId())
        .categoryId(product.getCategory().getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .stock(product.getStock())
        .createdAt(product.getCreatedAt())
        .build();
  }

  public ProductResponse create( ProductRequest request) {
    return ProductResponse.builder().build();
  }

  public ProductResponse update( Long id, ProductRequest request) {
    return ProductResponse.builder().build();
  }

  public void deleteById(Long id){

  }

}
