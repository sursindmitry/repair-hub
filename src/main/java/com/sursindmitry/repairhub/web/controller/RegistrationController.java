package com.sursindmitry.repairhub.web.controller;

import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.web.dto.RegisterRequestDto;
import com.sursindmitry.repairhub.web.dto.RegisterResponseDto;
import com.sursindmitry.repairhub.web.mapper.RegisterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class RegistrationController {
  private final RegisterFacadeService registerFacadeService;

  private final RegisterMapper registerMapper;

  @PostMapping("/register")
  public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto requestDto) {
    User user = registerMapper.toEntity(requestDto);

    return registerMapper.toDto(registerFacadeService.register(user),
        "Вы зарегистрировались. Подтвердите почту.");
  }
}
