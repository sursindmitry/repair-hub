package com.sursindmitry.repairhub.web.controller;

import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.service.VerificationService;
import com.sursindmitry.repairhub.web.dto.RegisterRequestDto;
import com.sursindmitry.repairhub.web.dto.RegisterResponseDto;
import com.sursindmitry.repairhub.web.dto.VerificationResponse;
import com.sursindmitry.repairhub.web.mapper.RegisterMapper;
import com.sursindmitry.repairhub.web.mapper.VerificationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class RegistrationController {
  private final RegisterFacadeService registerFacadeService;
  private final VerificationService verificationService;

  private final RegisterMapper registerMapper;
  private final VerificationMapper verificationMapper;

  @PostMapping("/register")
  public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto requestDto) {
    User user = registerMapper.toEntity(requestDto);

    return registerMapper.toDto(registerFacadeService.register(user),
        "Вы зарегистрировались. Подтвердите почту.");
  }

  @GetMapping("/verification")
  public VerificationResponse verification(@RequestParam String token) {
    return verificationMapper.toDto(verificationService.verification(token),
        "Вы подтвердили почту. Войдите в аккаунт");
  }
}
