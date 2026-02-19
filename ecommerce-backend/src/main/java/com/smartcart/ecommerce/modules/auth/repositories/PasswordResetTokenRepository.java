package com.smartcart.ecommerce.modules.auth.repositories;

import com.smartcart.ecommerce.modules.auth.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByEmail(String email);
}
