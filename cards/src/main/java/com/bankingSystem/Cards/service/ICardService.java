package com.bankingSystem.Cards.service;

import com.bankingSystem.Cards.dto.CardDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
