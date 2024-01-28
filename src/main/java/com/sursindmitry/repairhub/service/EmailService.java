package com.sursindmitry.repairhub.service;

public interface EmailService {
  void sendConfirmationEmail(String email, String firstName, String lastName, String token);
}
