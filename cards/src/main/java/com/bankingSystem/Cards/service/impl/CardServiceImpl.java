package com.bankingSystem.Cards.service.impl;

import com.bankingSystem.Cards.constants.CardConstant;
import com.bankingSystem.Cards.dto.CardDto;
import com.bankingSystem.Cards.entity.Card;
import com.bankingSystem.Cards.exception.ResourceNotFoundException;
import com.bankingSystem.Cards.mapper.CardMapper;
import com.bankingSystem.Cards.repository.CardRepository;
import com.bankingSystem.Cards.service.ICardService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardServiceImpl implements ICardService {

    private CardRepository cardRepository;
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            throw new RuntimeException("Card already exists with this mobile number");
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {

        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstant.CREDIT_CARD);
        newCard.setTotalLimit(CardConstant.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstant.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","mobileNumber",mobileNumber));
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardsDto) {
        Card card = cardRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card","mobileNumber", cardsDto.getMobileNumber()));
        CardMapper.mapToCard(cardsDto, card);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardRepository.deleteById(cards.getCardId());
        return true;
    }
}
