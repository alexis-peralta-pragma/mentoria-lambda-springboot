package com.mentoria.update_card.repository;

import com.mentoria.update_card.model.Card;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface CardRepository {
    Card getCard(String partitionKey, String sortKey);
    default Card updatePrice(Card card){
        return this.saveCard(card);
    }

    Card saveCard(Card card);
}
