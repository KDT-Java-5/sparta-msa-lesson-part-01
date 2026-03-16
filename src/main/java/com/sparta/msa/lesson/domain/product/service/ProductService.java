package com.sparta.msa.lesson.domain.product.service;

import com.sparta.msa.lesson.domain.product.dto.request.ProductRequest;
import com.sparta.msa.lesson.domain.product.dto.response.ProductResponse;
import com.sparta.msa.lesson.domain.product.entity.Product;
import com.sparta.msa.lesson.domain.product.repository.ProductRepository;
import com.sparta.msa.lesson.global.exception.DomainException;
import com.sparta.msa.lesson.global.exception.DomainExceptionCode;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final EntityManager entityManager;

  @Transactional
  public void decreaseStock(Long productId, int quantity) {
    // 1. ID=1 상품 조회. (이때 엔티티와 스냅샷이 영속성 컨텍스트에 로드됨)
    Product product = productRepository.findById(productId).get();

    // 2. 객체의 상태 변경. (메모리 상의 엔티티 객체 필드만 변경됨)
    //    - 현재 엔티티: Product(id=1, name="노트북", stock=90)
    //    - 스냅샷      : {name="노트북", stock=100}
    product.decreaseStock(quantity);
  }
  // 3. 메서드 종료 -> @Transactional에 의해 트랜잭션 커밋 시도 -> Flush 자동 호출

  @Transactional
  public void createAndFlush(Product product) {
    // 1. 엔티티를 영속성 컨텍스트에 저장. (아직 INSERT 쿼리는 DB에 안 감)
    productRepository.save(product);

    // 2. flush()를 호출하여 INSERT 쿼리를 DB에 즉시 전송.
    entityManager.flush();

    // 3. 이후 로직에서 이 product를 ID로 조회하는 다른 쿼리를 실행해도,
    // 이미 DB에 데이터가 있으므로 정상적으로 조회된다.
  }

  public List<ProductResponse> getAllProducts() {
    return List.of();
  }

  public ProductResponse getProductById(Long id) {
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

  public ProductResponse create(ProductRequest request) {
    return ProductResponse.builder().build();
  }

  public ProductResponse update(Long id, ProductRequest request) {
    return ProductResponse.builder().build();
  }

  public void deleteById(Long id) {

  }

}
