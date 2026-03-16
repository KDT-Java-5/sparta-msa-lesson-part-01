package com.sparta.msa.lesson.domain.product.controller;

import com.sparta.msa.lesson.domain.product.dto.request.ProductRequest;
import com.sparta.msa.lesson.domain.product.dto.response.ProductResponse;
import com.sparta.msa.lesson.domain.product.service.ProductService;
import com.sparta.msa.lesson.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ApiResponse<List<ProductResponse>> getAllProducts() {
    return ApiResponse.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
    return ApiResponse.ok(productService.getProductById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<ProductResponse> create(@RequestBody ProductRequest request) {
    return ApiResponse.ok(productService.create(request));
  }

  @PutMapping("/{id}")
  public ApiResponse<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
    return ApiResponse.ok(productService.update(id,request));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    productService.deleteById(id);
  }

}
