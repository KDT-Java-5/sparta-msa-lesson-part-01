package com.sparta.msa.lesson.domain.user.mapper;

import com.sparta.msa.lesson.domain.user.dto.request.UserRequest;
import com.sparta.msa.lesson.domain.user.dto.response.UserResponse;
import com.sparta.msa.lesson.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface UserMapper {

  // User Entity -> UserResponse DTO 변환
  UserResponse toResponse(User user);

  // UserRequest DTO -> User Entity 변환
  @Mapping(target = "password", source = "encodedPassword")
  User toEntity(UserRequest request, String encodedPassword);
}
