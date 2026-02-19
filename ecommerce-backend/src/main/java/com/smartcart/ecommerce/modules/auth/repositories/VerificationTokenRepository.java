package com.smartcart.ecommerce.modules.auth.repositories;

import com.smartcart.ecommerce.modules.auth.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByEmail(String email);
}
