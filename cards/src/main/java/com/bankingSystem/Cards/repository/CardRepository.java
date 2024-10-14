package com.bankingSystem.Cards.repository;

import com.bankingSystem.Cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findByMobileNumber(String mobileNumber);

    Optional<Card> findByCardNumber(String cardNumber);
}

