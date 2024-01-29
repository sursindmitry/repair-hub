package com.sursindmitry.repairhub.database.repository;

import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationTokenRepository extends
    CrudRepository<EmailVerificationToken, Long> {
  Optional<EmailVerificationToken> findByToken(String token);
}
