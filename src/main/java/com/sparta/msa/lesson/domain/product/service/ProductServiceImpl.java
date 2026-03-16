package com.sparta.msa.lesson.domain.product.service;

import com.sparta.msa.lesson.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

  private final ProductRepository productRepository;

  // 생성자 주입
//  public ProductServiceImpl(ProductRepository productRepository) {
//    this.productRepository = productRepository;
//  }

  // 필드 주입
//  @Autowired
//  private ProductRepository productRepository;


  // Setter 주입
//  @Autowired // setter 메서드에 @Autowired를 붙인다.
//  public void setProductRepository(ProductRepository productRepository) {
//    this.productRepository = productRepository;
// }

}
