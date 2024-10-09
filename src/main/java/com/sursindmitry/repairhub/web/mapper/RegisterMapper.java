package com.sursindmitry.repairhub.web.mapper;

import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.web.dto.RegisterRequestDto;
import com.sursindmitry.repairhub.web.dto.RegisterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
  @Mapping(source = "email", target = "email")
  @Mapping(source = "firstName", target = "firstName")
  @Mapping(source = "lastName", target = "lastName")
  @Mapping(source = "password", target = "password")

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "refreshToken", ignore = true)
  User toEntity(RegisterRequestDto dto);

  @Mapping(source = "entity.email", target = "email")
  @Mapping(source = "entity.firstName", target = "firstName")
  @Mapping(source = "entity.lastName", target = "lastName")
  @Mapping(source = "message", target = "message")
  RegisterResponseDto toDto(User entity, String message);
}
