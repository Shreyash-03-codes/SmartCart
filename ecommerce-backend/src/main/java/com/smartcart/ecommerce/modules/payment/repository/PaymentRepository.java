package com.smartcart.ecommerce.modules.payment.repository;

import com.smartcart.ecommerce.modules.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByTransactionId(String transactionId);
}
