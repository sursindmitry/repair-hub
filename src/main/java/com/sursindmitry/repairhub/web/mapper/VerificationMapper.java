package com.sursindmitry.repairhub.web.mapper;

import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.web.dto.VerificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VerificationMapper {

  @Mapping(source = "entity.email", target = "email")
  @Mapping(source = "message", target = "message")
  VerificationResponse toDto(User entity, String message);
}
