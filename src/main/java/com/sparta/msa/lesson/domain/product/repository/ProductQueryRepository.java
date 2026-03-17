package com.sparta.msa.lesson.domain.product.repository;

import static com.sparta.msa.lesson.domain.category.entity.QCategory.category;
import static com.sparta.msa.lesson.domain.product.entity.QProduct.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa.lesson.domain.product.dto.CategoryProductCountDTO;
import com.sparta.msa.lesson.domain.product.dto.CategoryProductDTO;
import com.sparta.msa.lesson.domain.product.dto.ProductDTO;
import com.sparta.msa.lesson.domain.product.dto.QCategoryProductCountDTO;
import com.sparta.msa.lesson.domain.product.dto.QCategoryProductDTO;
import com.sparta.msa.lesson.domain.product.dto.QProductDTO;
import com.sparta.msa.lesson.domain.product.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

  private final JPAQueryFactory queryFactory;

  public List<Product> findProducts(String name, Double minPrice, Double maxPrice) {
    return queryFactory
        .selectFrom(product)
        .where(
            nameContains(name), // 메서드 호출
            priceGoe(minPrice),
            priceLoe(maxPrice)
        )
        .fetch();
  }

  // 조건들을 메서드로 분리
  private BooleanExpression nameContains(String name) {
    return name != null ? product.name.contains(name) : null;
  }

  private BooleanExpression priceGoe(Double minPrice) {
    return minPrice != null ? product.price.goe(minPrice) : null;
  }

  private BooleanExpression priceLoe(Double maxPrice) {
    return maxPrice != null ? product.price.loe(maxPrice) : null;
  }

  public List<Product> findProductsByParentCategory(Long parentCategoryId) {
    return queryFactory
        .selectFrom(product)
        .join(product.category, category)
        .where(category.parent.id.eq(parentCategoryId))
        .fetch();
  }

  public Page<Product> findPagedProducts(String name, Pageable pageable) {
    // 1. 콘텐츠 조회 쿼리 (페이징 적용)
    List<Product> content = queryFactory
        .selectFrom(product)
        .where(
            name != null ? product.name.contains(name) : null
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(product.createdAt.desc())
        .fetch();

    // 2. 전체 개수 조회 쿼리 (조건은 동일하게, 페이징은 제외)
    Long total = queryFactory
        .select(product.count())
        .from(product)
        .where(
            name != null ? product.name.contains(name) : null
        )
        .fetchOne();

    // 3. PageImpl 객체로 조립하여 반환
    return new PageImpl<>(content, pageable, total);
  }

  public List<ProductDTO> findProductDTOs(Double minPrice) {
    return queryFactory
        .select(new QProductDTO( // Q-DTO의 생성자를 호출
            product.name,
            product.price,
            product.stock
        ))
        .from(product)
        .where(product.price.goe(minPrice))
        .fetch();
  }

  public List<CategoryProductDTO> findCategoryProducts(String categoryName) {
    return queryFactory
        .select(new QCategoryProductDTO( // Q-DTO 생성자로 결과 매핑
            category.name,
            product.name,
            product.price,
            product.stock
        ))
        .from(product)
        .join(product.category, category) // product와 category를 조인
        .where(category.name.eq(categoryName))
        .fetch();
  }

  public List<CategoryProductCountDTO> findCategoryProductCounts() {
    return queryFactory
        .select(new QCategoryProductCountDTO(
            product.category.name,
            product.id.count()  // 카테고리별 상품 수를 센다
        ))
        .from(product)
        .join(product.category, category)
        .groupBy(product.category.name)  // 카테고리 이름으로 그룹화
        .orderBy(product.id.countDistinct().desc())
        .fetch();
  }

}
