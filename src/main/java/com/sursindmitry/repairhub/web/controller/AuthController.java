package com.sursindmitry.repairhub.web.controller;

import com.sursindmitry.repairhub.database.entity.RefreshToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.LoginService;
import com.sursindmitry.repairhub.service.RefreshTokenService;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.service.VerificationService;
import com.sursindmitry.repairhub.web.dto.JwtRequest;
import com.sursindmitry.repairhub.web.dto.JwtResponse;
import com.sursindmitry.repairhub.web.dto.RefreshTokenRequest;
import com.sursindmitry.repairhub.web.dto.RegisterRequestDto;
import com.sursindmitry.repairhub.web.dto.RegisterResponseDto;
import com.sursindmitry.repairhub.web.dto.VerificationResponse;
import com.sursindmitry.repairhub.web.mapper.RegisterMapper;
import com.sursindmitry.repairhub.web.mapper.VerificationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Registration Controller", description = "Registration and Verification methods")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
  private final RegisterFacadeService registerFacadeService;
  private final VerificationService verificationService;
  private final LoginService loginService;
  private final RefreshTokenService refreshTokenService;

  private final RegisterMapper registerMapper;
  private final VerificationMapper verificationMapper;

  @PostMapping("/register")
  @Operation(
      description = "Register user",
      responses = {
          @ApiResponse(responseCode = "201", description = "Register User")
      }
  )
  public ResponseEntity<RegisterResponseDto> register(
      @Valid @RequestBody RegisterRequestDto requestDto) {
    User user = registerMapper.toEntity(requestDto);

    RegisterResponseDto response = registerMapper.toDto(registerFacadeService.register(user),
        "Вы зарегистрировались. Подтвердите почту.");

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/verification")
  @Operation(
      description = "Verification user",
      responses = {
          @ApiResponse(responseCode = "200", description = "Verification User")
      }
  )
  public ResponseEntity<VerificationResponse> verification(@RequestParam String token) {

    VerificationResponse response =
        verificationMapper.toDto(verificationService.verification(token),
            "Вы подтвердили почту. Войдите в аккаунт");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {
    JwtResponse response = loginService.login(request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtResponse> refreshJwtToken(
      @Valid @RequestBody RefreshTokenRequest request) {
    JwtResponse refreshToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

    return new ResponseEntity<>(refreshToken, HttpStatus.OK);
  }
}
