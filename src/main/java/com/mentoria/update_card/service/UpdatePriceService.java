package com.mentoria.update_card.service;

import com.mentoria.update_card.dto.CreateCardDto;
import com.mentoria.update_card.exceptions.BadRequesException;
import com.mentoria.update_card.exceptions.NotFoundException;
import com.mentoria.update_card.mapper.CardMapper;
import com.mentoria.update_card.model.Card;
import com.mentoria.update_card.model.Price;
import com.mentoria.update_card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UpdatePriceService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;


    public Card execute(String cardId, Double newPrice) throws NotFoundException {
        String partitionKey = cardId.split("-")[0];
        String sortKey = cardId.split("-")[1];

        Card card = cardRepository.getCard(partitionKey, sortKey);
        if (card == null) {
            throw new NotFoundException();
        }

        Price price = Price.builder().price(newPrice).date(new Date().getTime()).build();

        List<Price> priceList = card.getPriceHistorial();
        priceList.add(price);
        card.setPrice(newPrice);
        return cardRepository.updatePrice(card);

    }
}
